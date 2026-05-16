package com.seng.management_system.repository.chat;

import com.seng.management_system.model.chat.ChatMessage;
import com.seng.management_system.model.chat.ReactChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ReactChatMessageRepository extends JpaRepository<ReactChatMessage,Long>, JpaSpecificationExecutor<ReactChatMessage> {
    Optional<ReactChatMessage> findByReactCodeCodeAndReactByIdAndChatMessageId(String emojiCode, Long reactById,Long chatMessageId);

}
