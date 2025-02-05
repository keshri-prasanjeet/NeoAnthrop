package com.keshrixlabs.neoanthrop.chat;

import com.keshrixlabs.neoanthrop.user.User;
import com.keshrixlabs.neoanthrop.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ChatResponse> getAllChatsFromUser(Authentication currentUser) {
        final String currentUserId = currentUser.getName();//current authenticated user
        return chatRepository.getCurrentUserChats(currentUserId)
                .stream()
                .map(chat -> chatMapper.toChatResponse(chat, currentUserId))
                .toList();
    }

    public String createChat(String senderId, String recipientId) {
        Optional<Chat> optionalChat = chatRepository.findChatByReceiverAndSender(senderId, recipientId);
        if (optionalChat.isPresent()) {
            return optionalChat.get().getId();
        }
        User sender = userRepository.findUserByPublicId(senderId)
                .orElseThrow(() -> new EntityNotFoundException("Sender not found with id: " + senderId)); //maybe default to SMS that join me on NeoAnthrop
        //The orElseThrow method expects a Supplier which is a functional interface. The lambda function
        // you are using calls the constructor of the EntityNotFoundException class with a parameterized
        // message.
        User recipient = userRepository.findUserByPublicId(recipientId)
                .orElseThrow(() -> new EntityNotFoundException("Recipient not found with id: " + recipientId));
        Chat chat = new Chat();
        chat.setSender(sender);
        chat.setRecipient(recipient);
        chatRepository.save(chat);
        return chat.getId();
    }
}
