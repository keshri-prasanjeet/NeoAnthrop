package com.keshrixlabs.neoanthrop.message;

import com.keshrixlabs.neoanthrop.chat.Chat;
import com.keshrixlabs.neoanthrop.chat.ChatRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final MessageMapper messageMapper;

    public void saveMessage(MessageRequest messageRequest) {
        //find the chat this message belongs to
        Chat chat = chatRepository.findById(messageRequest.getChatId())
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));

        messageRepository.save(Message.builder()
                .senderId(messageRequest.getSenderId())
                .recipientId(messageRequest.getReceiverId())
                .content(messageRequest.getMessage())
                .type(messageRequest.getType())
                .chat(chat)
                .state(MessageState.SENT)
                .build());
    }

    public List<MessageResponse> findChatMessages(String chatId) {
//        return messageRepository.findMessagesByChatId(chatId).stream()
//                .map(message -> MessageResponse.builder()
//                        .id(message.getId())
//                        .content(message.getContent())
//                        .createdAt(message.getCreatedAt())
//                        .state(message.getState())
//                        .senderId(message.getSenderId())
//                        .recipientId(message.getRecipientId())
//                        .type(message.getType())
//                        .build()).collect(Collectors.toList());
        if (chatRepository.findById(chatId).isEmpty()) {
            throw new EntityNotFoundException("Chat not found");
        }
        return messageRepository.findMessagesByChatId(chatId).stream()
                .map(messageMapper::toMessageResponse)
                .collect(Collectors.toList());
    }
    @Transactional
    public void setMessagesToSeen(String chatId, Authentication authentication) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));

        final String recipientId = getRecipientId(chat, authentication);

        messageRepository.setMessagesToSeenByChatId(chatId, MessageState.READ);

        //todo notificationService.notifyMessagesSeen(chatId, recipientId);
    }

    private String getRecipientId(Chat chat, Authentication authentication) {
        //we have the chat, we have the authentication object
        //we dont know if the authenticated user is the sender or the recipient for this chat
        if (chat.getSender().getId().equals(authentication.getName())) {
            return chat.getRecipient().getId();
        } else if (chat.getRecipient().getId().equals(authentication.getName())) {
            return chat.getSender().getId();
        }
        throw new EntityNotFoundException("Recipient not found");
    }
}
