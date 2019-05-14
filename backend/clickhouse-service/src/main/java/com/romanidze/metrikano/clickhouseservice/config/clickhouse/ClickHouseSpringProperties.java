package com.romanidze.metrikano.clickhouseservice.config.clickhouse;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * 08.03.2019
 *
 * Параметры подключения к ClickHouse
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
@Component
@ConfigurationProperties(prefix = "spring.clickhouse")
public class ClickHouseSpringProperties {

    private String host;
    private Integer port;
    private String database;
    private String user;
    private String password;

    /**
     * Метод для создания строки подключения к базе данных
     * @return сформированная строка подключения
     */
    public String getDatabaseURL(){

        if(this.host == null || this.host.isEmpty()){
            throw new IllegalArgumentException("Значение host должно быть передано");
        }

        if(this.port == null || this.port == 0){
            throw new IllegalArgumentException("Значение port должно быть передано");
        }

        if(this.database == null || this.database.isEmpty()){
            throw new IllegalArgumentException("Значение database должно быть передано");
        }

        StringBuilder sb = new StringBuilder();

        sb.append("jdbc:clickhouse://")
          .append(this.host).append(":")
          .append(this.port).append("/")
          .append(this.database); 

        return sb.toString();

    }

}
