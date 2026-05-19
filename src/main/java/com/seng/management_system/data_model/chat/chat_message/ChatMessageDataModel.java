package com.seng.management_system.data_model.chat.chat_message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDataModel {
    private Long id;
    private Long userId;
    private Long chatId;
}
