package com.keshrixlabs.neoanthrop.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MessageRequest {
    private String senderId;
    private String recipientId;
    private String message;
    private MessageType type;
    private String chatId;
}
