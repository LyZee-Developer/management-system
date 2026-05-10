package com.seng.management_system.repository.chat;

import com.seng.management_system.model.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long>, JpaSpecificationExecutor<Chat> {
    Optional<Chat> findByIdAndIsActivate(Long id, Boolean isActivate);
}
