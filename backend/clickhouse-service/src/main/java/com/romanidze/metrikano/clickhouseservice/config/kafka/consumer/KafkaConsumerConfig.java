package com.romanidze.metrikano.clickhouseservice.config.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.romanidze.metrikano.clickhouseservice.dto.VKUserDTO;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * 04.03.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Configuration
public class KafkaConsumerConfig {

    private final KafkaConsumerProperties kafkaConsumerProperties;
    private final ObjectMapper objectMapper;

    @Autowired
    public KafkaConsumerConfig(KafkaConsumerProperties kafkaConsumerProperties,
                               @Qualifier("kafkaObjectMapper") ObjectMapper objectMapper) {
        this.kafkaConsumerProperties = kafkaConsumerProperties;
        this.objectMapper = objectMapper;
    }

    @Bean
    public Map<String, Object> consumerProps(){

        Map<String, Object> configProps = new HashMap<>();

        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaConsumerProperties.getBootstrapServers());
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, this.kafkaConsumerProperties.getOffsetType());
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, this.kafkaConsumerProperties.getGroupID());
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return configProps;

    }

    @Bean
    public ConsumerFactory<String, VKUserDTO> consumerFactory(){

        return new DefaultKafkaConsumerFactory<>(consumerProps(),
                                                 new StringDeserializer(),
                                                 new JsonDeserializer<>(VKUserDTO.class, this.objectMapper));

    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, VKUserDTO> kafkaListenerContainerFactory(){

        ConcurrentKafkaListenerContainerFactory<String, VKUserDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;

    }
}