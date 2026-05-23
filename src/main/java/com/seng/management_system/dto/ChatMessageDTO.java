package com.seng.management_system.dto;

import java.util.Date;
import java.util.List;

import com.seng.management_system.model.DataRef;
import com.seng.management_system.model.UserInfo;
import com.seng.management_system.model.chat.ReactChatMessage;
import com.seng.management_system.model.chat.SeenMessage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDTO {

    private Long id;
    private String content;
    private DataRef type;
    private boolean isActivate;
    private boolean isPin;
    private boolean delete;
    private Date sendDate;
    private UserInfo sendBy;
    private List<SeenMessage> seenMessages;
    private List<ReactChatMessage> reactMessages;
}
