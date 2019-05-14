package com.romanidze.metrikano.clickhouseservice.receivers;

import com.romanidze.metrikano.clickhouseservice.dto.VKUserDTO;
import com.romanidze.metrikano.clickhouseservice.services.interfaces.VKUserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * 08.03.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Component
public class VKUserReceiver {

    private static final Logger logger = LogManager.getLogger(VKUserReceiver.class);

    private final VKUserService vkUserService;

    @Autowired
    public VKUserReceiver(VKUserService vkUserService) {
        this.vkUserService = vkUserService;
    }

    @KafkaListener(topics="${spring.kafka.consumer.topics[0]}")
    public void processInput(@Payload VKUserDTO data){

        logger.info("Получено сообщение: {}", data);
        this.vkUserService.saveVKUserData(data);
        logger.info("Запись {} сохранена", data);

    }

    @KafkaListener(topics="${spring.kafka.consumer.topics[1]}")
    public void processClassification(@Payload VKUserDTO data){

        logger.info("Получен результат классификации: {}", data);
        this.vkUserService.saveVKUserData(data);
        logger.info("Результат {} сохранён", data);

    }

}
