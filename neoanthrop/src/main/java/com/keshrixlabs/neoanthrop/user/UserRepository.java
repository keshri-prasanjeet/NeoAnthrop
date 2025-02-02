package com.keshrixlabs.neoanthrop.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import static com.keshrixlabs.neoanthrop.user.UserConstants.*;

public interface UserRepository extends JpaRepository<User, String> {
    @Query(name = FIND_USER_BY_EMAIL)
    Optional<User> findByEmail(@Param("email") String email);

    @Query(name = FIND_ALL_USERS_EXCEPT_SELF)
    List<User> findAllUsersExceptSelf(String publicId);

    @Query(name = FIND_USER_BY_PUBLIC_ID)
    Optional<User> findUserByPublicId(@Param("publicId") String publicId);
}
