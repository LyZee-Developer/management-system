package com.seng.management_system.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChatConversationRepository extends JpaRepository<ChatConversation, Long>, JpaSpecificationExecutor<ChatConversation> {

}
