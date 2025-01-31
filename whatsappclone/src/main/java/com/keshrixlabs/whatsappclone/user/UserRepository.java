package com.keshrixlabs.whatsappclone.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

import static com.keshrixlabs.whatsappclone.user.UserConstants.*;

public interface UserRepository extends JpaRepository<User, String> {
    @Query(name = FIND_USER_BY_EMAIL)
    Optional<User> findByEmail(String email);
    @Query(name = FIND_ALL_USERS_EXCEPT_SELF)
    List<User> findAllUsersExceptSelf(String publicId);
    @Query(name = FIND_USER_BY_PUBLIC_ID)
    Optional<User> findUserByPublicId(String publicId);
}
