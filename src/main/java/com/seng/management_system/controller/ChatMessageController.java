package com.seng.management_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import com.seng.management_system.apiResponse.ApiResponse;
import com.seng.management_system.constant.RouteApi;
import com.seng.management_system.data_model.chat.chat_message.ChatMessageDataModel;
import com.seng.management_system.service.chat.ChatMessageService;

@RestController
@RequestMapping(RouteApi.CHAT_MESSAGE)
public class ChatMessageController {

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/clear")
    public ResponseEntity<Object> ClearMessage(@RequestBody ChatMessageDataModel model) {
        return ResponseEntity.ok(ApiResponse.success(chatMessageService.clearMessage(model)));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        var result = chatMessageService.delete(id);
        Long parentId = chatMessageService.getParentChatId(id);
        messagingTemplate.convertAndSend(
                "/topic/conversation",
                parentId
        );
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/remove/{id}")
    public ResponseEntity<Object> removeMessage(@PathVariable Long id) {
        Long parentId = chatMessageService.removeMessage(id);
        messagingTemplate.convertAndSend(
                "/topic/conversation",
                parentId
        );
        return ResponseEntity.ok(ApiResponse.success(parentId));
    }


}
