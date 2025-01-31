package com.keshrixlabs.neoanthrop.message;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class MessageResponse {
    private long id;
    private String content;
    private MessageState state;
    private MessageType type;
    private String senderId;
    private String recipientId;
    private LocalDateTime createdAt;
    private byte[] mediaFile;
}
