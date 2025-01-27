package com.keshrixlabs.whatsappclone.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.requestMatchers(
                                        "/v3/api-docs/",
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "configuration/ui",
                                        "configuration/security",
                                        "/webjars/**",
                                        "/ws/**") //for web socket
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .oauth2ResourceServer(auth -> auth.jwt(token ->
                        token.jwtAuthenticationConverter(new KeyCloakJwtAuthenticationConverter())));
        //oauth resource server is the one that validates the jwt token that i get from authorization server
        //we did not go with oauth2ResourceServer(Customizer.withDefaults()) because we want to customize the jwtAuthenticationConverter, want to limit access on endpoints based on roles from keycloak
        // we also did not use the default JwtAuthentication converter, we make our own new KeyCloakJwtAuthenticationConverter()
        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        config.setAllowedHeaders(Arrays.asList(
                HttpHeaders.ORIGIN,
                HttpHeaders.ACCEPT,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.AUTHORIZATION
        ));

        config.setAllowedMethods(
                Arrays.asList("GET", "POST", "PUT","DELETE", "OPTIONS", "PATCH")
        );

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
