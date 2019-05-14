package com.romanidze.metrikano.influxservice.receivers;

import com.romanidze.metrikano.influxservice.dto.RecordDTO;
import com.romanidze.metrikano.influxservice.services.interfaces.RecordService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * 28.02.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Component
public class RecordListener {

    private static final Logger logger = LogManager.getLogger(RecordListener.class);

    private final RecordService recordService;

    @Autowired
    public RecordListener(RecordService recordService) {
        this.recordService = recordService;
    }

    @KafkaListener(topics="${spring.kafka.consumer.topics[0]}")
    public void processInput(@Payload RecordDTO data){

        logger.info("Получено сообщение: {}", data);
        this.recordService.saveRecord(data);
        logger.info("Запись {} сохранена", data);

    }

    @KafkaListener(topics="${spring.kafka.consumer.topics[1]}")
    public void processClassification(@Payload RecordDTO data){

        logger.info("Получен результат классификации: {}", data);
        this.recordService.saveRecord(data);
        logger.info("Результат {} сохранён", data);

    }

}
