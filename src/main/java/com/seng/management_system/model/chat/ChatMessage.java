package com.seng.management_system.model.chat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.seng.management_system.model.BaseActivateEntity;
import com.seng.management_system.model.DataRef;
import com.seng.management_system.model.UserInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class ChatMessage extends BaseActivateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    private List<ChatMessage> reply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Chat chat;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chatMessage", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SeenMessage> seenMessages;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chatMessage", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ReactChatMessage> reactMessages;
}
