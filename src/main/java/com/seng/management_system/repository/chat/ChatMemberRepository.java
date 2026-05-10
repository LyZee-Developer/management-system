package com.seng.management_system.repository.chat;

import com.seng.management_system.model.chat.ChatMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChatMemberRepository extends JpaRepository<ChatMember,Long>, JpaSpecificationExecutor<ChatMember> {
}
