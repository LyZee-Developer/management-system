package com.seng.management_system.service.chat;

import com.seng.management_system.data_model.chat.ChatDataModel;
import com.seng.management_system.data_model.chat.chat_message.ChatMessageDataModel;
import com.seng.management_system.model.chat.ChatMessage;

import java.util.List;

public interface ChatMessageService {
    List<ChatMessage> conversation(Long chatId);

    boolean clearMessage(ChatMessageDataModel model);

    boolean delete(Long chatId);
}
