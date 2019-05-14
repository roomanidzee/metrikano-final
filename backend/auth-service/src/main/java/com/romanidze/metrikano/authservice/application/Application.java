package com.romanidze.metrikano.authservice.application;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 28.04.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@SpringCloudApplication
@ComponentScan({"com.romanidze.metrikano.authservice.config", "com.romanidze.metrikano.authservice.controllers",
                "com.romanidze.metrikano.authservice.mappers", "com.romanidze.metrikano.authservice.services",
                "com.romanidze.metrikano.authservice.filters"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
