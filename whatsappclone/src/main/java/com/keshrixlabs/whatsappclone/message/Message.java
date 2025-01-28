package com.keshrixlabs.whatsappclone.message;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.keshrixlabs.whatsappclone.common.BaseAuditingEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
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
        private String chatId;
        @Enumerated(EnumType.STRING)
        private MesssageState state;
        @Enumerated(EnumType.STRING)
        private MessageType type;
}
