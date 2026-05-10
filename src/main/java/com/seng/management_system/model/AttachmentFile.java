package com.seng.management_system.model;

import com.seng.management_system.model.chat.ChatMessage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class AttachmentFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String filename;

    private Long refId;
    @Column(nullable = false)
    private String featureType; // STUDENT, PROFILE, ACCOUNT, ...
    private Double size;
    private String pathImage;
    @Column(nullable = false)
    private String extension;
}
