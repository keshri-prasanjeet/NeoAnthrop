package com.keshrixlabs.whatsappclone.user;

import com.keshrixlabs.whatsappclone.chat.Chat;
import com.keshrixlabs.whatsappclone.common.BaseAuditingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")

@NamedQuery(
        name = "UserConstants.FIND_USER_BY_EMAIL",
        query = "select u from User u where u.email = :email")//:email syntax in the query is a named parameter in JPQL (Java Persistence Query Language).
@NamedQuery(
        name = "UserConstants.FIND_USERS_EXCEPT_SELF",
        query = "select u from User u where u.id != :publicId")
@NamedQuery(
        name = "UserConstants.FIND_USER_BY_PUBLIC_ID",
        query = "select u from User u where u.id = :publicId")
public class User extends BaseAuditingEntity {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime lastSeen;

    @OneToMany(mappedBy = "sender")
    private List<Chat> chatsAsSender;
    @OneToMany(mappedBy = "recipient")
    private List<Chat> chatsAsRecipient;

    @Transient
    //The @Transient annotation is necessary to indicate that this method should not be persisted in the database
    //However, even if we don't use @Transient, it will still work but it is a good practice to use it
    public boolean isUserOnline() {
        return lastSeen != null && lastSeen.isAfter(LocalDateTime.now().minusMinutes(1));
    }

}
