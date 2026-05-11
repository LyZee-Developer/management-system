package com.seng.management_system.controller;

import com.seng.management_system.data_model.chat.ChatDataModel;
import com.seng.management_system.data_model.chat.ChatFilterDataModel;
import com.seng.management_system.model.chat.ChatMessage;
import com.seng.management_system.service.chat.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seng.management_system.apiResponse.ApiResponse;
import com.seng.management_system.constant.ApiPath;
import com.seng.management_system.service.ChatService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.util.List;

@RestController
@RequestMapping(ApiPath.CHAT)
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatMessageService chatMessageService;

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
        Long messageId = model.getMessageId();
        Long userId = model.getUserId();
        return ResponseEntity.ok(ApiResponse.success(chatService.seenChat(messageId, userId)));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(ApiResponse.success(chatService.delete(id)));
    }

    @PostMapping("/block")
    public ResponseEntity<Object> block() {
        return ResponseEntity.ok(ApiResponse.success(chatService.block()));
    }

    @GetMapping("/changeRoom")
    public ResponseEntity<Object> changeRoomName(@ModelAttribute ChatDataModel model) {
        return ResponseEntity.ok(ApiResponse.success(chatService.changeRoomName(model)));
    }

    @GetMapping("/conversation/{id}")
    public ResponseEntity<Object> conversation(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(ApiResponse.success(chatMessageService.conversation(id)));
    }

    @PostMapping("/conversation/send")
    public ResponseEntity<Object> sendMessage(@RequestBody ChatDataModel model) {
        return ResponseEntity.ok(ApiResponse.success(chatService.sendMessage(model)));
    }

    @PostMapping("/pin")
    public ResponseEntity<Object> pin() {
        return ResponseEntity.ok(ApiResponse.success(chatService.pin()));
    }
}
