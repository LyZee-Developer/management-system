package com.seng.management_system.service;

import java.util.List;

import com.seng.management_system.data_model.chat.ChatDataModel;
import com.seng.management_system.model.chat.Chat;

public interface ChatService {

    String create(ChatDataModel model);

    String addUserToChat(List<Long> userIds, Long chatId);

    String seenChat(Long messageId, Long userId);

    List<Chat> list();

    boolean delete(Long id);

    boolean block();

    boolean changeRoomName(ChatDataModel model);

    boolean pin();

}
