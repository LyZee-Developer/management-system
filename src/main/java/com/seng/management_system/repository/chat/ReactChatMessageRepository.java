package com.seng.management_system.repository.chat;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.seng.management_system.model.chat.ReactChatMessage;

public interface ReactChatMessageRepository extends JpaRepository<ReactChatMessage, Long>, JpaSpecificationExecutor<ReactChatMessage> {

    Optional<ReactChatMessage> findByReactCodeCodeAndReactByIdAndChatMessageId(String emojiCode, Long reactById, Long chatMessageId);

}
