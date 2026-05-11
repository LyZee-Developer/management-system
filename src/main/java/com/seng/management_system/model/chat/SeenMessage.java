package com.seng.management_system.model.chat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.seng.management_system.model.UserInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
public class SeenMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private ChatMessage chatMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIncludeProperties({"id", "email", "name", "phone", "gender"})
    private UserInfo seenBy;

    private Date seenDate;
}
