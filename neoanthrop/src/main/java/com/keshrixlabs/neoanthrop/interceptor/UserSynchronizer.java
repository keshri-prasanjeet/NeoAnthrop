package com.keshrixlabs.neoanthrop.interceptor;

import com.keshrixlabs.neoanthrop.user.User;
import com.keshrixlabs.neoanthrop.user.UserMapper;
import com.keshrixlabs.neoanthrop.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSynchronizer {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void synchronizeWithIdp(Jwt token) {
        log.info("Synchronizing user with IDP");
        getUserEmail(token).ifPresent(email -> {
            log.info("Syncing user with email: {}", email);
            Optional<User> optionalUser = userRepository.findByEmail(email);
            //check if user exists in the database already
            User user = userMapper.fromTokenAttributes(token.getClaims());
            optionalUser.ifPresent(existingUser -> {
                user.setId(existingUser.getId());
                //if the user we just created from the token claims already exists in db then just update the user id
            });
            userRepository.save(user);
            //save the user to the database
        });
    }

    private Optional<String> getUserEmail(Jwt token) {
        //extract email from token
        Map<String , Object> claims = token.getClaims();
        return Optional.ofNullable(claims.get("email").toString());
    }
}
