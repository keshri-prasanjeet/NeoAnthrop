package com.keshrixlabs.whatsappclone.interceptor;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor

public class UserSynchronizerFilter extends OncePerRequestFilter {

    private final UserSynchronizer userSynchronizer;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        if(shouldSynchronize(requestURI) && !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            //if the user is authenticated, synchronize the user with the IDP
            JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            //cast the authentication token to JwtAuthenticationToken and get the token
            userSynchronizer.synchronizeWithIdp(token.getToken());
            //pass the token to the userSynchronizer to synchronize the user with the IDP
        }

    }

    private boolean shouldSynchronize(String requestURI) {
        return requestURI.startsWith("/user") || requestURI.startsWith("/chat");
    }
    /*only synchronize the user with the IDP if the request is for the user or chat endpoints
     The UserSynchronizerFilter will intercept all HTTP requests that pass through the Spring
     Security filter chain, regardless of the endpoint. This means it will be executed for
     every request made to our application, whether the request is related to user operations or not.*/
}
