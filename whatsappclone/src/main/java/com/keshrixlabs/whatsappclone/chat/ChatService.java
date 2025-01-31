package com.keshrixlabs.whatsappclone.chat;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;

    @Transactional(readOnly = true)
    public List<ChatResponse> getChatsByRecipientId(Authentication currentUser) {
        final String currentUserId = currentUser.getName();//current authenticated user
        //
        return chatRepository.findChatsBySenderId(currentUserId)
                .stream()
                .map(chat -> chatMapper.toChatResponse(chat, currentUserId))
                .toList();
    }
}
