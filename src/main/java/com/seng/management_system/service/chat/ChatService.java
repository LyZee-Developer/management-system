package com.seng.management_system.service.chat;

import java.util.List;

import com.seng.management_system.data_model.chat.ChatDataModel;
import com.seng.management_system.data_model.chat.ChatFilterDataModel;
import com.seng.management_system.model.chat.Chat;

public interface ChatService {

    Long create(ChatDataModel model);

    String addUserToChat(List<Long> userIds, Long addByUserId, Long chatId);

    String seenChat(Long messageId, Long userId);

    String sendMessage(ChatDataModel model);

    List<Chat> list(ChatFilterDataModel filter);

    boolean delete(Long id);

    Long unTyping(Long userId, Long chatId);

    boolean block(ChatDataModel.BlockMessage block);

    boolean changeRoomName(ChatDataModel model);

    boolean pin();

    boolean reactMessage(ChatDataModel.ReactMessage reactMessage);

}
