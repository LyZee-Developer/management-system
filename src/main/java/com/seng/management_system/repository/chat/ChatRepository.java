package com.seng.management_system.repository.chat;

import java.util.Optional;

import com.seng.management_system.model.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChatRepository extends JpaRepository<Chat, Long>, JpaSpecificationExecutor<Chat> {

    Optional<Chat> findByIdAndIsActivate(Long id, boolean isActive);
}
