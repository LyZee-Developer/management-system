package com.seng.management_system.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReactConversationRepository extends JpaRepository<ReactConversation, Long>, JpaSpecificationExecutor<ReactConversation> {

}
