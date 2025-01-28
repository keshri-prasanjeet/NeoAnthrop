package com.keshrixlabs.whatsappclone.chat;

import com.keshrixlabs.whatsappclone.message.Message;
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


    private User sender;
    private User recipient;

    private List<Message> messages;

}
