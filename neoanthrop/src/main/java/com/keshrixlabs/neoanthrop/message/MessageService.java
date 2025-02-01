package com.keshrixlabs.neoanthrop.message;

import com.keshrixlabs.neoanthrop.chat.Chat;
import com.keshrixlabs.neoanthrop.chat.ChatRepository;
import com.keshrixlabs.neoanthrop.file.FileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class MessageService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final MessageMapper messageMapper;
    private final FileService fileService;

    public void saveMessage(MessageRequest messageRequest) {
        //find the chat this message belongs to
        Chat chat = chatRepository.findById(messageRequest.getChatId())
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));

        Message message = new Message();
        message.setSenderId(messageRequest.getSenderId());
        message.setRecipientId(messageRequest.getReceiverId());
        message.setContent(messageRequest.getMessage());
        message.setType(messageRequest.getType());
        message.setChat(chat);
        message.setState(MessageState.SENT);
        messageRepository.save(message);
    }

    public List<MessageResponse> findMessagesByChatId(String chatId) {
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

        messageRepository.changeMessageStateByChatId(chatId, MessageState.READ);

        //todo notificationService.notifyMessagesSeen(chatId, recipientId);
    }

    public void uploadMediaMessage(String chatId, MultipartFile mediaMessage, Authentication authentication) {
        //Authentication object is the authenticated user, the one who is sending the message
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));

        final String senderId = getSenderId(chat, authentication);
        final String recipientId = getRecipientId(chat, authentication);

        final String filePath = fileService.saveFile(mediaMessage, senderId);

        Message message = new Message();
        message.setSenderId(senderId);
        message.setRecipientId(recipientId);
        message.setType(MessageType.IMAGE);
        message.setChat(chat);
        message.setState(MessageState.SENT);
        message.setMediaFilepath(filePath);
        messageRepository.save(message);

        //todo notification media
    }

    private String getSenderId(Chat chat, Authentication authentication) {
        if(chat.getSender().getId().equals(authentication.getName())){
            return chat.getSender().getId();
        } else if(chat.getRecipient().getId().equals(authentication.getName())){
            return chat.getRecipient().getId();
        } else {
            throw new EntityNotFoundException("Sender not found");
        }
    }

    private String getRecipientId(Chat chat, Authentication authentication) {
        //we have the chat, we have the authentication object
        //we dont know if the authenticated user is the sender or the recipient for this chat
        /*
        * The chat object has a sender and a recipient but for a chat either can be the sender or the recipient
        * The chat object can store the sender and the recipient in any order, if a user is stored as a sender in one chat
        * it doesn't make it "the sender" in for that chat between user 1 and user 2. It could be the recipient if other
        * user sends a message. The authenticated user is the sender, if that matches with sender/recipient that is the
        * sender for that instance.
         */
        if (chat.getSender().getId().equals(authentication.getName())) {
            return chat.getRecipient().getId();
        } else if (chat.getRecipient().getId().equals(authentication.getName())) {
            return chat.getSender().getId();
        }
        throw new EntityNotFoundException("Recipient not found");
    }
}
