package com.romanidze.metrikano.postgresservice.application;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 14.04.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@SpringCloudApplication
@ComponentScan({"com.romanidze.metrikano.postgresservice.config",
                "com.romanidze.metrikano.postgresservice.controllers",
                "com.romanidze.metrikano.postgresservice.domain",
                "com.romanidze.metrikano.postgresservice.mappers",
                "com.romanidze.metrikano.postgresservice.services",
                "com.romanidze.metrikano.postgresservice.filters",
                "com.romanidze.metrikano.postgresservice.receivers"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
