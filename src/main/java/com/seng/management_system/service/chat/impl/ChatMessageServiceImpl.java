package com.seng.management_system.service.chat.impl;

import com.seng.management_system.exception.ApiException;
import com.seng.management_system.model.chat.ChatMessage;
import com.seng.management_system.repository.chat.ChatMessageRepository;
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
    private ChatService chatService;

    @Override
    public List<ChatMessage> conversation(Long chatId) {
        return chatMessageRepository.findByChatIdAndIsActivateOrderById(chatId, Boolean.TRUE);
    }

    @Override
    public boolean removeAll(Long chatId) {
        String me = AuthenticationUtil.getCurrentUser();
        Date now = new Date();
        List<ChatMessage> allConversation = chatMessageRepository.findByChatIdAndIsActivateOrderById(chatId, Boolean.TRUE);
        List<ChatMessage> disabledAllConversation = new ArrayList<>();
        for (ChatMessage msg : allConversation) {
            msg.setIsActivate(Boolean.FALSE);
            msg.setUpdateBy(me);
            msg.setUpdateDate(now);
            disabledAllConversation.add(msg);
        }
        chatMessageRepository.saveAll(disabledAllConversation);
        return true;
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
