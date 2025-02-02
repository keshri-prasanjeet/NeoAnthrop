package com.keshrixlabs.neoanthrop.notification;

import com.keshrixlabs.neoanthrop.message.MessageType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Notification {
    private String chatId;
    private String chatName;
    private String senderId;
    private String recipientId;
    private String content;
    private MessageType messageType;
    private NotificationType type;
    private byte[] media;
}
