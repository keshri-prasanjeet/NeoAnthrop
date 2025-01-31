package com.keshrixlabs.neoanthrop.user;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserMapper {
    public User fromTokenAttributes(Map<String, Object> claims) {
        User user = new User();user.setEmail(claims.get("email").toString());
        if(claims.containsKey("sub")){
            user.setId(claims.get("sub").toString());
            //"sub" claim identifies the subject of the token. The subject is the user that the token represents.
        }

        if(claims.containsKey("email")){
            user.setEmail(claims.get("email").toString());
        }

        if(claims.containsKey("given_name")){
            user.setFirstName(claims.get("given_name").toString());
        } else if(claims.containsKey("nickname")){
            user.setFirstName(claims.get("nickname").toString());
        }

        if(claims.containsKey("family_name")) {
            user.setLastName(claims.get("family_name").toString());
        }
        user.setLastSeen(LocalDateTime.now());
        return user;
    }
}
