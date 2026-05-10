package com.seng.management_system.model.chat;

import com.seng.management_system.model.DataRef;
import com.seng.management_system.model.UserInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
public class ReactChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserInfo reactBy;

    private Date reactDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "code")
    private DataRef reactCode;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatMessage chatMessage;
}
