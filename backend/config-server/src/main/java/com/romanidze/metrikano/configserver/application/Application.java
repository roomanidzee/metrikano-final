package com.romanidze.metrikano.configserver.application;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 16.02.2019
 *
 * Сервис хранения конфигурациии для основных микросервисов системы
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@SpringCloudApplication
@EnableConfigServer
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

}
