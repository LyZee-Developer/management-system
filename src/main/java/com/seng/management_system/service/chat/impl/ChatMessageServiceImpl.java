package com.seng.management_system.service.chat.impl;

import com.seng.management_system.data_model.chat.chat_message.ChatMessageDataModel;
import com.seng.management_system.exception.ApiException;
import com.seng.management_system.model.DataRef;
import com.seng.management_system.model.UserInfo;
import com.seng.management_system.model.chat.Chat;
import com.seng.management_system.model.chat.ChatMessage;
import com.seng.management_system.repository.DataRefRepository;
import com.seng.management_system.repository.UserInfoRepository;
import com.seng.management_system.repository.chat.ChatMemberRepository;
import com.seng.management_system.repository.chat.ChatMessageRepository;
import com.seng.management_system.repository.chat.ChatRepository;
import com.seng.management_system.service.chat.ChatService;
import com.seng.management_system.service.chat.ChatMessageService;
import com.seng.management_system.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private DataRefRepository dataRefRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatService chatService;

    @Override
    public List<ChatMessage> conversation(Long chatId) {
        return chatMessageRepository.findByChatIdAndIsActivateOrderById(chatId, Boolean.TRUE);
    }

    @Override
    public boolean clearMessage(ChatMessageDataModel model) {
        String username = AuthenticationUtil.getCurrentUser();
        Date now = new Date();
        List<ChatMessage> allConversation = chatMessageRepository.findByChatIdAndIsActivateOrderById(model.getChatId(), Boolean.TRUE);
        List<ChatMessage> disabledAllConversation = new ArrayList<>();
        for (ChatMessage msg : allConversation) {
            msg.setIsActivate(Boolean.FALSE);
            msg.setUpdateBy(username);
            msg.setUpdateDate(now);
            disabledAllConversation.add(msg);
        }
        chatMessageRepository.saveAll(disabledAllConversation);

        //************** Mark message to after remove message *****************
        RemoveAllMessage(model);

        return true;
    }

    private void RemoveAllMessage(ChatMessageDataModel model) {
        Date now = new Date();

        DataRef type = dataRefRepository.findByCode("CLEAR").orElseThrow(() -> new ApiException("data ref not found!"));
        UserInfo me = userInfoRepository.findById(model.getUserId()).orElseThrow(() -> new ApiException("User reaction not found!"));
        Chat chat = chatRepository.findByIdAndIsActivate(model.getChatId(), Boolean.TRUE).orElseThrow(() -> new ApiException("chat not found!"));

        ChatMessage removeAll = new ChatMessage();
        removeAll.setSendBy(me);
        removeAll.setSendDate(now);
        removeAll.setContent("All message have been clear by your member!🥹");
        removeAll.setType(type);
        removeAll.setParent(null);
        removeAll.setChat(chat);
        removeAll.setIsActivate(Boolean.TRUE);
        removeAll.setCreateBy(AuthenticationUtil.getCurrentUser());
        removeAll.setCreateDate(now);

        chatMessageRepository.save(removeAll);
    }

    @Override
    public boolean delete(Long messageId) {
        String me = AuthenticationUtil.getCurrentUser();
        Date now = new Date();
        ChatMessage message = chatMessageRepository.findByIdAndIsActivate(messageId, Boolean.TRUE).orElseThrow(() -> new ApiException("chat message not found!"));
        message.setDelete(Boolean.TRUE);
        message.setUpdateDate(now);
        message.setUpdateBy(me);
        chatMessageRepository.save(message);
        return true;
    }

}
