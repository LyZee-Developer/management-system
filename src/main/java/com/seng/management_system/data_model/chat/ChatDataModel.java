package com.seng.management_system.data_model.chat;

import com.seng.management_system.data_model.BaseDataModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class ChatDataModel extends BaseDataModel {
    private Long id;
    private Long sendBy;
    private Long receiveBy;


    // ********* Chat member *********
    private Set<Long> userIds;

    // ********* ChatMessage *********
    //seen message
    private Long messageId;
    private Long userId;

    private String content;
    private String type; // SOUND, Audio, Video, Image, Other...\
    private Long parentId; // parent of chat message

    public static class Room{
        private String roomName;
    }
}
