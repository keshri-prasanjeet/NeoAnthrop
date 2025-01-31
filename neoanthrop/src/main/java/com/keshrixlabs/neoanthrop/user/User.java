package com.keshrixlabs.neoanthrop.user;

import com.keshrixlabs.neoanthrop.chat.Chat;
import com.keshrixlabs.neoanthrop.common.BaseAuditingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import static com.keshrixlabs.neoanthrop.user.UserConstants.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")

@NamedQuery(
        name = FIND_USER_BY_EMAIL,
        query = "select u from User u where u.email = :email")
@NamedQuery(
        name = FIND_ALL_USERS_EXCEPT_SELF,
        query = "select u from User u where u.id != :publicId")
@NamedQuery(
        name = FIND_USER_BY_PUBLIC_ID,
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
