package com.keshrixlabs.whatsappclone.chat;

import com.keshrixlabs.whatsappclone.message.Message;
import com.keshrixlabs.whatsappclone.message.MessageState;
import com.keshrixlabs.whatsappclone.message.MessageType;
import com.keshrixlabs.whatsappclone.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.GenerationType.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = UUID)
    private String id;

    @ManyToOne//Many chats can be sent by one user
    @JoinColumn(name = "sender_id")
    private User sender;
    @ManyToOne//Many chats can be received by one user
    @JoinColumn(name = "recipient_id")
    private User recipient;
    @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER)//One chat can have many messages
    //the mappedBy attribute is used to indicate that the Message entity owns the relationship through its chat field.
    @OrderBy("createdDate DESC")
    private List<Message> messages;

    @Transient
    public String getChatName(final String senderId) {
        if(senderId.equals(recipient.getId())){
            return sender.getFirstName() + " " + sender.getLastName(); //Chatting with myself
        }
        else return recipient.getFirstName() + " " + recipient.getLastName(); //Regular chat with another person
    }

    @Transient
    public long getUnreadMessageCount(final String recipientId) {
        return messages
                .stream()
                .filter(message -> message.getRecipientId().equals(recipientId))
                .filter(message -> MessageState.SENT == message.getState())
                .count();
    }

    @Transient
    public String getLatestMessage() {
        if(messages != null && !messages.isEmpty()){
            if(messages.get(0).getType() != MessageType.TEXT){
                return messages.get(0).getType().name().toLowerCase() + " received";
            }
            return messages.get(0).getContent();
        }
        return null;
    }

    @Transient
    public String getLatestMessageTime() {
        if(messages != null && !messages.isEmpty()){
            return messages.get(0).getCreatedDate().toString();
        }
        return null;
    }

}
