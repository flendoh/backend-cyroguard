package com.example.cryoguard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CryoGuardApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryoGuardApplication.class, args);
    }

}
