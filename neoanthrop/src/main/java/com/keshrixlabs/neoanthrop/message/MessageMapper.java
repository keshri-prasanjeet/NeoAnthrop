package com.keshrixlabs.neoanthrop.message;

import org.springframework.stereotype.Service;

@Service
public class MessageMapper {
    public MessageResponse toMessageResponse(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .senderId(message.getSenderId())
                .recipientId(message.getRecipientId())
                .type(message.getType())
                .state(message.getState())
                .createdAt(message.getCreatedAt())
                //todo read the media file
                .build();
    }
}