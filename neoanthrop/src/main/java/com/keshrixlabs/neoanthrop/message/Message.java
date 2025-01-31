package com.keshrixlabs.neoanthrop.message;

import com.keshrixlabs.neoanthrop.chat.Chat;
import jakarta.persistence.*;
import lombok.*;
import com.keshrixlabs.neoanthrop.common.BaseAuditingEntity;

import static com.keshrixlabs.neoanthrop.message.MessageConstants.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
@Builder
@NamedQuery(name = FIND_MESSAGES_BY_CHAT_ID,
        query = "select m from Message m where m.chat.id = :chatId ORDER BY m.createdAt DESC")
@NamedQuery(name = SET_MESSAGES_TO_SEEN_BY_CHAT,
        query = "UPDATE Message SET state = :newState WHERE chat.id = :chatId")
public class Message extends BaseAuditingEntity {
    @Id
    @SequenceGenerator(name = "message_sequence", sequenceName = "message_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_sequence")
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column(name = "sender_id", nullable = false)
    private String senderId;
    @Column(name = "recipient_id", nullable = false)
    private String recipientId;
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;
    @Enumerated(EnumType.STRING)
    private MessageState state;
    @Enumerated(EnumType.STRING)
    private MessageType type;
}
