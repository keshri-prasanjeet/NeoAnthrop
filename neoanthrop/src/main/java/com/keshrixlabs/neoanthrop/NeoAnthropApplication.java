package com.keshrixlabs.neoanthrop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NeoAnthropApplication {

    public static void main(String[] args) {
        SpringApplication.run(NeoAnthropApplication.class, args);
    }

}
