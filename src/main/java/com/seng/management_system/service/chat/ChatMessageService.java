package com.seng.management_system.service.chat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.seng.management_system.data_model.chat.chat_message.ChatMessageDataModel;
import com.seng.management_system.data_model.chat.chat_message.ChatMessageFilterDataModel;
import com.seng.management_system.dto.ChatMessageDTO;

public interface ChatMessageService {

    Page<ChatMessageDTO> conversation(ChatMessageFilterDataModel filter, Pageable pageable);

    boolean clearMessage(ChatMessageDataModel model);

    boolean delete(Long chatId);

    Long getParentChatId(Long id);
}
