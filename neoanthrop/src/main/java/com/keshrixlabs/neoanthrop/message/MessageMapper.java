package com.keshrixlabs.neoanthrop.message;

import com.keshrixlabs.neoanthrop.file.FileUtils;
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
                .mediaFile(FileUtils.readFileFromLocation(message.getMediaFilepath()))
                .build();
    }
}