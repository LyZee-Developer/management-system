package com.seng.management_system.repository.chat;

import com.seng.management_system.model.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long>, JpaSpecificationExecutor<ChatMessage> {
}
