package com.keshrixlabs.neoanthrop.chat;

import com.keshrixlabs.neoanthrop.common.StringResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
@Tag(name = "Chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<StringResponse> createChat(
            @RequestParam("sender-id") String senderId,
            @RequestParam("recipient-id") String recipientId) {
        final String chatId = chatService.createChat(senderId, recipientId);
        StringResponse createdChat = StringResponse.builder()
                .response(chatId).build();
        return ResponseEntity.ok(createdChat);
    }

    @GetMapping
    public ResponseEntity<List<ChatResponse>> getChatsByUser(Authentication authentication) {
        return ResponseEntity.ok(chatService.getAllChatsFromUser(authentication));
    }
}
