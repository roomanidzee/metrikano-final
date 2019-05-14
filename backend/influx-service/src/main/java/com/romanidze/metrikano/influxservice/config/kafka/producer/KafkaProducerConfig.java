package com.romanidze.metrikano.influxservice.config.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.romanidze.metrikano.influxservice.dto.UserRecordDTO;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * 09.05.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Configuration
public class KafkaProducerConfig {

    private final KafkaProducerProperties kafkaProducerProperties;
    private final ObjectMapper objectMapper;

    @Autowired
    public KafkaProducerConfig(KafkaProducerProperties kafkaProducerProperties,
                               ObjectMapper objectMapper) {
        this.kafkaProducerProperties = kafkaProducerProperties;
        this.objectMapper = objectMapper;
    }

    @Bean
    public Map<String, Object> producerProps(){

        Map<String, Object> producerProps = new HashMap<>();

        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaProducerProperties.getBootstrapServers());
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return producerProps;

    }

    @Bean
    public ProducerFactory<String, UserRecordDTO> producerFactory(){

        return new DefaultKafkaProducerFactory<>(producerProps(),
                                                 new StringSerializer(),
                                                 new JsonSerializer<>(this.objectMapper));

    }

    @Bean
    public KafkaTemplate<String, UserRecordDTO> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

}
