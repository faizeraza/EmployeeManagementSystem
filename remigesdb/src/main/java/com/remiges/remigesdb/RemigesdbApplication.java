package com.remiges.remigesdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RemigesdbApplication {

    public static void main(String[] args) {
        SpringApplication.run(RemigesdbApplication.class, args);
    }

}
