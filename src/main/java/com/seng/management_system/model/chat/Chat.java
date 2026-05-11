package com.seng.management_system.model.chat;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.seng.management_system.model.BaseActivateEntity;
import com.seng.management_system.model.UserInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@JsonIncludeProperties({"roomName","id","members","messages"})
public class Chat extends BaseActivateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chat", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ChatMember> members;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chat", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ChatMessage> messages;
}
