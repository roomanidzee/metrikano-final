package com.romanidze.metrikano.clickhouseservice.application;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 04.03.2019
 *
 * Класс для запуска микросервиса clickhouse-service
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@SpringCloudApplication
@ComponentScan({"com.romanidze.metrikano.clickhouseservice.config", "com.romanidze.metrikano.clickhouseservice.controllers",
                "com.romanidze.metrikano.clickhouseservice.mappers", "com.romanidze.metrikano.clickhouseservice.receivers",
                "com.romanidze.metrikano.clickhouseservice.repositories", "com.romanidze.metrikano.clickhouseservice.services",
                "com.romanidze.metrikano.clickhouseservice.filters"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
