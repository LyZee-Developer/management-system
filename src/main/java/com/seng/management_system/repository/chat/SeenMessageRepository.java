package com.seng.management_system.repository.chat;

import com.seng.management_system.model.chat.SeenMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SeenMessageRepository extends JpaRepository<SeenMessage,Long>, JpaSpecificationExecutor<SeenMessage>{

}