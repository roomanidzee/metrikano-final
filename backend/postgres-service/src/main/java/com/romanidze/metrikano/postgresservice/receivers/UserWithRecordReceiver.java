package com.romanidze.metrikano.postgresservice.receivers;

import com.romanidze.metrikano.postgresservice.dto.UserWithRecordDTO;
import com.romanidze.metrikano.postgresservice.services.interfaces.UserWithRecordService;
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
public class UserWithRecordReceiver {

    private static final Logger logger = LogManager.getLogger(UserWithRecordReceiver.class);

    private final UserWithRecordService userWithRecordService;

    @Autowired
    public UserWithRecordReceiver(UserWithRecordService userWithRecordService) {
        this.userWithRecordService = userWithRecordService;
    }

    @KafkaListener(topics="${spring.kafka.consumer.topics[0]}",
                   containerFactory = "userRecordListenerFactory")
    public void processUserWithRecord(@Payload UserWithRecordDTO data){

        logger.info("Получено сообщение: {}", data);
        this.userWithRecordService.saveUserWithRecord(data);
        logger.info("Запись {} сохранена", data);

    }

}
