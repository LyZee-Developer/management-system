package com.seng.management_system.model.chat;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.seng.management_system.model.BaseActivateEntity;
import com.seng.management_system.model.DataRef;
import com.seng.management_system.model.UserInfo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@JsonIncludeProperties({"id", "sendBy", "sendDate", "isActivate", "content", "type", "isPin", "delete", "reply", "seenMessages", "reactMessages"})
public class ChatMessage extends BaseActivateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIncludeProperties({"id", "email", "name", "phone", "gender"})
    private UserInfo sendBy;
    private Date sendDate;

    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "code")
    private DataRef type;

    private boolean isDelete;
    private boolean isPin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private ChatMessage parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @SQLRestriction("is_activate = true")
    private List<ChatMessage> reply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Chat chat;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chatMessage", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SeenMessage> seenMessages;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chatMessage", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @SQLRestriction("is_activate = true")
    private List<ReactChatMessage> reactMessages;
}
