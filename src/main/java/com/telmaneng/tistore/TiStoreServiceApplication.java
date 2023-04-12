package com.telmaneng.tistore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TiStoreServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TiStoreServiceApplication.class, args);
    }

}
