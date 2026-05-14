package com.seng.management_system.service.chat.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.seng.management_system.constant.ChatConstant;
import com.seng.management_system.data_model.chat.ChatDataModel;
import com.seng.management_system.data_model.chat.ChatFilterDataModel;
import com.seng.management_system.model.DataRef;
import com.seng.management_system.model.chat.Chat;
import com.seng.management_system.model.chat.ChatMember;
import com.seng.management_system.model.chat.ChatMessage;
import com.seng.management_system.model.chat.SeenMessage;
import com.seng.management_system.repository.DataRefRepository;
import com.seng.management_system.repository.chat.ChatMemberRepository;
import com.seng.management_system.repository.chat.ChatMessageRepository;
import com.seng.management_system.repository.chat.ChatRepository;
import com.seng.management_system.repository.chat.SeenMessageRepository;
import com.seng.management_system.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seng.management_system.exception.ApiException;
import com.seng.management_system.model.UserInfo;
import com.seng.management_system.repository.UserInfoRepository;
import com.seng.management_system.service.chat.ChatService;

import jakarta.transaction.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private DataRefRepository dataRefRepository;

    @Autowired
    private SeenMessageRepository seenMessageRepository;

    @Autowired
    private ChatMemberRepository chatMemberRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    private final Date dateNow = new Date();

    @Override
    public List<Chat> list(ChatFilterDataModel filter) {
        return chatMemberRepository.findByUserIdAndIsActivate(filter.getUserId(), Boolean.TRUE).stream().map(ChatMember::getChat).toList();
    }

    @Override
    @Transactional
    public String create(ChatDataModel model) {
        String currentUser = AuthenticationUtil.getCurrentUser();
        Chat chat = new Chat();

        //************** verify current user are using or active ************
        UserInfo sender = userInfoRepository.findById(model.getSendBy()).orElseThrow(() -> new ApiException("Sender not found!"));
        UserInfo receiver = null;

        if (!ObjectUtils.isEmpty(model.getReceiveBy())) {
            receiver = userInfoRepository.findById(model.getReceiveBy()).orElseThrow(() -> new ApiException("Receiver not found!"));
        }

        chat.setIsActivate(Boolean.TRUE);
        chat.setCreateBy(currentUser);
        chat.setCreateDate(dateNow);
        chatRepository.save(chat);

        // ************* user who are including in chat *************
        setUserWhoChatIn(sender, receiver, chat);

        // ************* User that starting message to user *************
        startMessageByUser(sender, model, chat);

        return "create chat successfully!";
    }

    @Override
    @Transactional
    public String seenChat(Long lastMessageId, Long seenById) {
        ChatMessage chatMessage = chatMessageRepository.findById(lastMessageId).orElseThrow(() -> new ApiException("Chat message not found!"));
        UserInfo user = userInfoRepository.findByIdAndIsActivate(seenById, Boolean.TRUE).orElseThrow(() -> new ApiException("user info not found!"));

        Long totalMessage = chatMessageRepository.countByChatIdAndIsActivate(chatMessage.getChat().getId(), Boolean.TRUE);

        SeenMessage seen = new SeenMessage();
        seen.setChatMessage(chatMessage);
        seen.setSeenBy(user);
        seen.setSeenDate(dateNow);

        //************ user have read message ***********
        ChatMember me = chatMemberRepository.findByChatIdAndUserIdAndIsActivate(chatMessage.getChat().getId(), seenById, Boolean.TRUE);
        me.setLastSeenMessageId(lastMessageId);
        chatMemberRepository.save(me);

        seenMessageRepository.save(seen);
        return "seen success!";
    }

    @Override
    public String sendMessage(ChatDataModel model) {
        UserInfo sender = userInfoRepository.findById(model.getSendBy()).orElseThrow(() -> new ApiException("Sender not found!"));
        Chat chat = chatRepository.findByIdAndIsActivate(model.getId(), Boolean.TRUE).orElseThrow(() -> new ApiException("chat not found!"));
        startMessageByUser(sender, model, chat);
        return "sent";
    }

    @Override
    public String addUserToChat(List<Long> userIds, Long addByUserId, Long chatId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new ApiException("chat not found!"));
        UserInfo addByUser = userInfoRepository.findByIdAndIsActivate(addByUserId, Boolean.TRUE).orElseThrow(() -> new ApiException("chat not found!"));
        List<UserInfo> users = new ArrayList<>();
        List<ChatMember> members = new ArrayList<>();

        if (userIds.isEmpty()) {
            throw new ApiException("userId must have value!");
        }

        for (Long userId : userIds) {
            UserInfo user = userInfoRepository.findByIdAndIsActivate(userId, Boolean.TRUE).orElseThrow(() -> new ApiException("user not found!"));
            users.add(user);
        }

        // ********** set up user to chat *************
        for (UserInfo userInfo : users) {
            members.add(setChatMember(userInfo, chat, Boolean.FALSE));

            //********** show up the message user that have been added user **********
            ChatDataModel model = new ChatDataModel();

            model.setType(ChatConstant.ADD);
            String message = String.format("%s have been added new %s", addByUser.getName(), userInfo.getName());
            model.setContent(message);

            startMessageByUser(addByUser, model, chat);
        }

        chatMemberRepository.saveAll(members);


        return "Add user to chat successfully!";
    }

    @Transactional
    private void setUserWhoChatIn(UserInfo sender, UserInfo receiver, Chat chat) {
        List<ChatMember> saveData = new ArrayList<>();

        ChatMember sendMessage = setChatMember(sender, chat, Boolean.TRUE);
        saveData.add(sendMessage);

        // ************* user chat with self or other user *************
        if (receiver != null) {
            ChatMember receiveMessage = setChatMember(receiver, chat, Boolean.FALSE);
            saveData.add(receiveMessage);
        }
        chatMemberRepository.saveAll(saveData);
    }

    @Transactional
    private ChatMember setChatMember(UserInfo user, Chat chat, boolean IsAdmin) {
        ChatMember message = new ChatMember();

        message.setUser(user);
        message.setChat(chat);
        message.setPinChat(Boolean.FALSE);
        message.setAdmin(IsAdmin);
        message.setLastSeenMessageId(0L);
        message.setDateJoin(dateNow);
        message.setIsActivate(Boolean.TRUE);
        message.setCreateBy(AuthenticationUtil.getCurrentUser());
        message.setCreateDate(dateNow);

        return message;
    }

    @Transactional
    private void startMessageByUser(UserInfo sender, ChatDataModel model, Chat chat) {
        ChatMessage message = new ChatMessage();
        DataRef type = null;
        ChatMessage parent = null;
        if (StringUtils.hasLength(model.getType())) {
            type = dataRefRepository.findByCode(model.getType()).orElseThrow(() -> new ApiException("data ref not found!"));
        }

        if (!ObjectUtils.isEmpty(model.getParentId())) {
            parent = chatMessageRepository.findById(model.getParentId()).orElseThrow(() -> new ApiException("parent of chat message not found!"));
        }

        message.setSendBy(sender);
        message.setSendDate(dateNow);
        message.setContent(model.getContent());
        message.setType(type);
        message.setParent(parent);
        message.setChat(chat);
        message.setIsActivate(Boolean.TRUE);
        message.setCreateBy(AuthenticationUtil.getCurrentUser());
        message.setCreateDate(dateNow);

        chatMessageRepository.save(message);
    }

    @Override
    public boolean delete(Long id) {
        Chat chat = chatRepository.findByIdAndIsActivate(id, Boolean.TRUE).orElseThrow(() -> new ApiException("chat not found!"));
        chat.setIsActivate(Boolean.FALSE);
        chatRepository.save(chat);
        return true;
    }

    @Override
    public boolean block() {
        return true;
    }

    @Override
    public boolean changeRoomName(ChatDataModel model) {
        return true;
    }

    @Override
    public boolean pin() {
        return true;
    }
}
