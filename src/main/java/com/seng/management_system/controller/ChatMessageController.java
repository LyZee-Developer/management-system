package com.seng.management_system.controller;

import com.seng.management_system.apiResponse.ApiResponse;
import com.seng.management_system.constant.RouteApi;
import com.seng.management_system.data_model.chat.ChatFilterDataModel;
import com.seng.management_system.service.chat.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RouteApi.CHAT_MESSAGE)
public class ChatMessageController {

    @Autowired
    private ChatMessageService chatMessageService;

    @GetMapping("/remove/{id}")
    public ResponseEntity<Object> removeAll(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(chatMessageService.removeAll(id)));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(chatMessageService.delete(id)));
    }
}
