package com.romanidze.metrikano.postgresservice.config.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.romanidze.metrikano.postgresservice.dto.UserWithLinkDTO;
import com.romanidze.metrikano.postgresservice.dto.UserWithRecordDTO;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * 09.05.2019
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

    @Bean(name="consumerFactoryUserLink")
    @ConditionalOnMissingBean(name="userLinkListenerFactory")
    public ConsumerFactory<String, UserWithLinkDTO> consumerFactoryUserLink(){

        return new DefaultKafkaConsumerFactory<>(consumerProps(),
                                                 new StringDeserializer(),
                                                 new JsonDeserializer<>(UserWithLinkDTO.class, this.objectMapper));

    }

    @Bean(name="userLinkListenerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, UserWithLinkDTO> userLinkListenerFactory(){

        ConcurrentKafkaListenerContainerFactory<String, UserWithLinkDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryUserLink());
        return factory;

    }

    @Bean(name="consumerFactoryUserRecord")
    @ConditionalOnMissingBean(name="userRecordListenerFactory")
    public ConsumerFactory<String, UserWithRecordDTO> consumerFactoryUserRecord(){

        return new DefaultKafkaConsumerFactory<>(consumerProps(),
                                                 new StringDeserializer(),
                                                 new JsonDeserializer<>(UserWithRecordDTO.class ,this.objectMapper));

    }

    @Bean(name="userRecordListenerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, UserWithRecordDTO> userRecordListenerFactory(){

        ConcurrentKafkaListenerContainerFactory<String, UserWithRecordDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryUserRecord());
        return factory;

    }

}
