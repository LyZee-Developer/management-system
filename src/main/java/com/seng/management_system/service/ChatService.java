package com.seng.management_system.service;

import java.util.List;

import com.seng.management_system.data_model.chat.ChatDataModel;
import com.seng.management_system.model.chat.Chat;

public interface ChatService {

    Chat create(ChatDataModel model);

    List<Chat> list();

    boolean delete(Long id);

    boolean block();

    boolean changeRoomName(ChatDataModel.ChatUpdateModel model);

    boolean pin();

}
