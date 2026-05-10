package com.seng.management_system.controller;

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
import com.seng.management_system.data_model.chat.ChatDataModel;
import com.seng.management_system.service.ChatService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping(ApiPath.CHAT)
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/list")
    public ResponseEntity<Object> list() {
        return ResponseEntity.ok(ApiResponse.success(chatService.list()));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody ChatDataModel model) {
        return ResponseEntity.ok(ApiResponse.success(chatService.create(model)));
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
    public ResponseEntity<Object> changeRoomName(@ModelAttribute ChatDataModel.ChatUpdateModel model) {
        return ResponseEntity.ok(ApiResponse.success(chatService.changeRoomName(model)));
    }

    @PostMapping("/pin")
    public ResponseEntity<Object> pin() {
        return ResponseEntity.ok(ApiResponse.success(chatService.pin()));
    }
}
