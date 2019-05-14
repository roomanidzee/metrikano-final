package com.romanidze.metrikano.influxservice.config.influx;

import com.romanidze.metrikano.influxservice.domain.Record;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.influxdb.InfluxDBConnectionFactory;
import org.springframework.data.influxdb.InfluxDBProperties;
import org.springframework.data.influxdb.InfluxDBTemplate;

/**
 * 28.02.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Configuration
@EnableConfigurationProperties(InfluxDBProperties.class)
public class InfluxDBConfiguration {

    @Bean
    public InfluxDBConnectionFactory connectionFactory(final InfluxDBProperties properties){
        return new InfluxDBConnectionFactory(properties);
    }

    @Bean
    public InfluxDBTemplate<Record> influxDBTemplate(final InfluxDBConnectionFactory connectionFactory){
        return new InfluxDBTemplate<>(connectionFactory, new RecordConverter());
    }

}
