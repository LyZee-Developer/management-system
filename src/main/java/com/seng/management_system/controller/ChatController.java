package com.seng.management_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import com.seng.management_system.apiResponse.ApiResponse;
import com.seng.management_system.constant.RouteApi;
import com.seng.management_system.data_model.chat.ChatDataModel;
import com.seng.management_system.data_model.chat.ChatFilterDataModel;
import com.seng.management_system.data_model.chat.chat_message.ChatMessageFilterDataModel;
import com.seng.management_system.service.chat.ChatMessageService;
import com.seng.management_system.service.chat.ChatService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping(RouteApi.CHAT)
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/list")
    public ResponseEntity<Object> list(@RequestBody ChatFilterDataModel filter) {
        return ResponseEntity.ok(ApiResponse.success(chatService.list(filter)));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody ChatDataModel model) {
        return ResponseEntity.ok(ApiResponse.success(chatService.create(model)));
    }

    @PostMapping("/add-user")
    public ResponseEntity<Object> addUserToChat(@Valid @RequestBody ChatDataModel model) {
        Long chatId = model.getId();
        Long addByUserId = model.getAddByUserId();
        List<Long> userIds = model.getUserIds().stream().toList();
        return ResponseEntity.ok(ApiResponse.success(chatService.addUserToChat(userIds, addByUserId, chatId)));
    }

    @PostMapping("/seen")
    public ResponseEntity<Object> seenMessage(@Valid @RequestBody ChatDataModel model) {
        Long lastMessageId = model.getMessageId();
        Long seenBy = model.getUserId();
        var result = chatService.seenChat(lastMessageId, seenBy);
        messagingTemplate.convertAndSend(
                "/topic/conversation",
                model.getId()
        );
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable @Positive Long id) {
        var result = chatService.delete(id);
        messagingTemplate.convertAndSend(
                "/topic/conversation",
                id
        );
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/un_typing")
    public ResponseEntity<Object> removeMessage(@RequestParam Long userId, @RequestParam Long chatId) {
        chatService.unTyping(userId, chatId);
        messagingTemplate.convertAndSend(
                "/topic/conversation",
                chatId
        );
        return ResponseEntity.ok(ApiResponse.success(chatId));
    }

    @PostMapping("/block")
    public ResponseEntity<Object> block(@RequestBody ChatDataModel.BlockMessage block) {
        return ResponseEntity.ok(ApiResponse.success(chatService.block(block)));
    }

    @GetMapping("/changeRoom")
    public ResponseEntity<Object> changeRoomName(@ModelAttribute ChatDataModel model) {
        return ResponseEntity.ok(ApiResponse.success(chatService.changeRoomName(model)));
    }

    @PostMapping("/conversation")
    public ResponseEntity<Object> conversation(@RequestBody ChatMessageFilterDataModel filter, @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(chatMessageService.conversation(filter, pageable)));
    }

    @PostMapping("/conversation/send")
    public ResponseEntity<Object> sendMessage(@RequestBody ChatDataModel model) {
        var result = chatService.sendMessage(model);
        messagingTemplate.convertAndSend(
                "/topic/conversation",
                model.getId()
        );
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/pin")
    public ResponseEntity<Object> pin() {
        return ResponseEntity.ok(ApiResponse.success(chatService.pin()));
    }

    @PostMapping("/react")
    public ResponseEntity<Object> ReactMessage(@RequestBody ChatDataModel.ReactMessage reactMessage) {
        var result = chatService.reactMessage(reactMessage);
        messagingTemplate.convertAndSend(
                "/topic/conversation",
                reactMessage.getChatId()
        );
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
