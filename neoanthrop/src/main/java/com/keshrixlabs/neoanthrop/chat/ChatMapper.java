package com.keshrixlabs.neoanthrop.chat;

import org.springframework.stereotype.Service;

@Service
public class ChatMapper {

    public ChatResponse toChatResponse(Chat chat, String currentUserId) {
        return ChatResponse.builder()
                .id(chat.getId())
                .name(chat.getChatName(currentUserId))
                .unreadCount(chat.getUnreadMessageCount(currentUserId))
                .lastMessage(chat.getLatestMessage())
                .isRecipientOnline(chat.getRecipient().isUserOnline())
                .senderId(chat.getSender().getId())
                .recipientId(chat.getRecipient().getId())
                .build();
    }
}
