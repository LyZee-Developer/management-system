package com.seng.management_system.data_model.chat;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatDataModel {

    private Long id;
    private Long senderId;
    private Long receiverId;
    @NotBlank(message = "content is required!")
    private String content;

    @Setter
    @Getter
    public static class ChatUpdateModel {

        private Long id;
        private String roomName;

    }
}
