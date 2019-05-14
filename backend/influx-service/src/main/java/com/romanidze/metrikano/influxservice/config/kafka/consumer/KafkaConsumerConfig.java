package com.romanidze.metrikano.influxservice.config.kafka.consumer;

import com.romanidze.metrikano.influxservice.dto.RecordDTO;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * 28.02.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Configuration
public class KafkaConsumerConfig {

    private final KafkaConsumerProperties kafkaConsumerProperties;

    @Autowired
    public KafkaConsumerConfig(KafkaConsumerProperties kafkaConsumerProperties) {
        this.kafkaConsumerProperties = kafkaConsumerProperties;
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
    public ConsumerFactory<String, RecordDTO> consumerFactory(){

        return new DefaultKafkaConsumerFactory<>(consumerProps(),
                                                 new StringDeserializer(),
                                                 new JsonDeserializer<>(RecordDTO.class));

    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, RecordDTO> kafkaListenerContainerFactory(){

        ConcurrentKafkaListenerContainerFactory<String, RecordDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;

    }
}
