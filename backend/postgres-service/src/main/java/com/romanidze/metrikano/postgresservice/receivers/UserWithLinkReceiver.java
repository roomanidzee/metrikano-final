package com.romanidze.metrikano.postgresservice.receivers;

import com.romanidze.metrikano.postgresservice.dto.UserWithLinkDTO;
import com.romanidze.metrikano.postgresservice.services.interfaces.UserWithLinkService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 09.05.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Component
public class UserWithLinkReceiver {

    private static final Logger logger = LogManager.getLogger(UserWithLinkReceiver.class);

    private final UserWithLinkService userWithLinkService;

    @Autowired
    public UserWithLinkReceiver(UserWithLinkService userWithLinkService) {
        this.userWithLinkService = userWithLinkService;
    }

    @KafkaListener(topics="${spring.kafka.consumer.topics[1]}",
                   containerFactory = "userLinkListenerFactory")
    public void processUserWithLink(@Payload UserWithLinkDTO data){

        logger.info("Получено сообщение: {}", data);
        this.userWithLinkService.saveUserWithLink(data);
        logger.info("Запись {} сохранена", data);

    }

}
