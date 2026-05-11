package com.seng.management_system.model.chat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.seng.management_system.model.BaseActivateEntity;
import com.seng.management_system.model.UserInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@JsonIncludeProperties({"id", "user", "dateJoin", "isAdmin", "isPinChat", "readCount"})
public class ChatMember extends BaseActivateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIncludeProperties({"id", "email", "name", "phone", "gender","hex","userLogin"})
    private UserInfo user;

    private Date dateJoin;

    private boolean isAdmin;
    private boolean isPinChat;
    private Integer readCount;
}
