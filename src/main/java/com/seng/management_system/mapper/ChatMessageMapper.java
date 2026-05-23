package com.seng.management_system.mapper;

import com.seng.management_system.dto.ChatMessageDTO;
import com.seng.management_system.model.chat.ChatMessage;

public class ChatMessageMapper {

    public static ChatMessageDTO mapToDto(ChatMessage entity) {

        ChatMessageDTO dto = new ChatMessageDTO();

        dto.setId(entity.getId());
        dto.setSendDate(entity.getSendDate());
        dto.setContent(entity.getContent());

        dto.setDelete(entity.isDelete());
        dto.setPin(entity.isPin());

        if (entity.getSendBy() != null) {
            dto.setSendBy(entity.getSendBy());
        }

        if (entity.getType() != null) {
            dto.setType(entity.getType());
        }

        dto.setSeenMessages(entity.getSeenMessages());
        dto.setReactMessages(entity.getReactMessages());

        return dto;
    }
}
