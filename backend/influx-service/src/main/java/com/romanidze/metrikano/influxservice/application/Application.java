package com.romanidze.metrikano.influxservice.application;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 28.02.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@SpringCloudApplication
@ComponentScan({"com.romanidze.metrikano.influxservice.config", "com.romanidze.metrikano.influxservice.controllers",
                "com.romanidze.metrikano.influxservice.mappers", "com.romanidze.metrikano.influxservice.repositories",
                "com.romanidze.metrikano.influxservice.receivers", "com.romanidze.metrikano.influxservice.services",
                "com.romanidze.metrikano.influxservice.filters"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
