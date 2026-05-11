package com.seng.management_system.service.impl.chat;

import com.seng.management_system.data_model.chat.ChatDataModel;
import com.seng.management_system.model.chat.ChatMessage;
import com.seng.management_system.repository.chat.ChatMessageRepository;
import com.seng.management_system.service.ChatService;
import com.seng.management_system.service.chat.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatService chatService;

    @Override
    public List<ChatMessage> conversation(Long chatId) {
        return chatMessageRepository.findByChatIdAndIsActivate(chatId, Boolean.TRUE);
    }

}
