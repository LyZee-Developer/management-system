package com.seng.management_system.model.chat;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.seng.management_system.model.BaseActivateEntity;
import com.seng.management_system.model.UserInfo;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@JsonIncludeProperties({"id", "user", "dateJoin", "isActivate", "isAdmin", "isPinChat", "lastSeenMessageId", "unread"})
public class ChatMember extends BaseActivateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIncludeProperties({"id", "email", "name", "phone", "gender", "hex", "userLogin"})
    private UserInfo user;

    private Date dateJoin;

    private boolean isAdmin;
    private boolean isPinChat;
    private Long lastSeenMessageId;
    private Long unread;

}
