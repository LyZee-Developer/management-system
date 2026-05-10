package com.seng.management_system.service.impl.chat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.seng.management_system.model.chat.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seng.management_system.data_model.chat.ChatDataModel;
import com.seng.management_system.exception.ApiException;
import com.seng.management_system.model.UserInfo;
import com.seng.management_system.repository.UserInfoRepository;
import com.seng.management_system.repository.chat.ChatConversationRepository;
import com.seng.management_system.repository.chat.ChatRepository;
import com.seng.management_system.service.ChatService;

import jakarta.transaction.Transactional;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private ChatConversationRepository chatConversationRepository;

    @Override
    @Transactional
    public Chat create(ChatDataModel model) {
        Chat senderData = new Chat();
        Chat receiverData = new Chat();
        LocalDate now = LocalDate.now();
        Date dateNow = new Date();
        UUID uid = UUID.randomUUID();

        UserInfo sender = userInfoRepository.findById(model.getSenderId()).orElseThrow(() -> new ApiException("Sender not found!"));
        UserInfo receiver = userInfoRepository.findById(model.getReceiverId()).orElseThrow(() -> new ApiException("Receiver not found!"));

        // *********** user send message to other user *************** 
        senderData.setIsActivate(Boolean.TRUE);

        // *********** user who get message from other user *************** 
        receiverData.setCreateDate(dateNow);
        receiverData.setIsActivate(Boolean.TRUE);

        List<Chat> senderAndReceiver = List.of(senderData, receiverData);

        // *********** store message while user send message ***********
        // setChatMessage(model.getContent(), uid);
        chatRepository.saveAll(senderAndReceiver);

        return new Chat();
    }

    private void setChatMessage() {
        ChatConversation message = new ChatConversation();
        // message.
    }

    @Override
    public List<Chat> list() {
        return new ArrayList<>();
    }

    @Override
    public boolean delete(Long id) {
        Chat chat = chatRepository.findByIdAndIsActivate(id, Boolean.TRUE).orElseThrow(() -> new ApiException("chat nout found!"));
        chat.setIsActivate(Boolean.FALSE);
        chatRepository.save(chat);
        return true;
    }

    @Override
    public boolean block() {
        return true;
    }

    @Override
    public boolean changeRoomName(ChatDataModel.ChatUpdateModel model) {
        return true;
    }

    @Override
    public boolean pin() {
        return true;
    }
}
