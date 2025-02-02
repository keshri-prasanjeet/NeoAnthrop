package com.keshrixlabs.neoanthrop;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@SecurityScheme(
        name = "keycloack",
        type = SecuritySchemeType.OAUTH2,
        bearerFormat = "JWT",
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER,
        flows = @OAuthFlows(
                password = @OAuthFlow(
                        authorizationUrl = "https://2eae-2401-4900-1f25-626b-c5e2-60e0-c89d-a87d.ngrok-free.app/realms/neo-anthrop/protocol/openid-connect/auth",
                        tokenUrl = "https://2eae-2401-4900-1f25-626b-c5e2-60e0-c89d-a87d.ngrok-free.app/realms/neo-anthrop/protocol/openid-connect/token"
                )
        )
)
public class NeoAnthropApplication {

    public static void main(String[] args) {
        SpringApplication.run(NeoAnthropApplication.class, args);
    }

}
