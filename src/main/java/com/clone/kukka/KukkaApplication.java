package com.clone.kukka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KukkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KukkaApplication.class, args);
    }

}
