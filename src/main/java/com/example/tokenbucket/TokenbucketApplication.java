package com.example.tokenbucket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TokenbucketApplication {

    public static void main(String[] args) {
        SpringApplication.run(TokenbucketApplication.class, args);
    }
}