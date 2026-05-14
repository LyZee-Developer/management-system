package com.seng.management_system.repository.chat;

import com.seng.management_system.model.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long>, JpaSpecificationExecutor<ChatMessage> {
    List<ChatMessage> findByChatIdAndIsActivateOrderById(Long chatId, boolean isActivate);
    Optional<ChatMessage> findByIdAndIsActivate(Long chatId, boolean isActivate);
    Long countByChatIdAndIsActivate(Long chatId, boolean isActivate);
}
