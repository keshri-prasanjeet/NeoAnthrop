package com.keshrixlabs.neoanthrop.chat;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class ChatResponse {
    private String id;
    private String name;
    private String senderId;
    private String recipientId;
    private String lastMessage;
    private LocalDateTime lastMessageTime;
    private long unreadCount;
    private boolean isRecipientOnline;
}
