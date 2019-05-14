package com.romanidze.metrikano.clickhouseservice.config.clickhouse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import org.springframework.jdbc.core.JdbcTemplate;

import ru.yandex.clickhouse.ClickHouseDataSource;
import ru.yandex.clickhouse.settings.ClickHouseProperties;

import javax.sql.DataSource;

/**
 * 08.03.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Configuration
public class ClickHouseConfig {

    private final ClickHouseSpringProperties clickHouseSpringProperties;

    /**
     * Конструктор для инициализации параметров для ClickHouse
     * @param clickHouseSpringProperties параметры подключения к ClickHouse
     */
    @Autowired
    public ClickHouseConfig(ClickHouseSpringProperties clickHouseSpringProperties) {
        this.clickHouseSpringProperties = clickHouseSpringProperties;
    }

    /**
     * Компонент для инициализации ClickHouseProperties
     * @return готовый компонент
     */
    @Bean
    public ClickHouseProperties clickHouseProperties(){

        ClickHouseProperties clickHouseProperties = new ClickHouseProperties();

        clickHouseProperties.setHost(this.clickHouseSpringProperties.getHost());
        clickHouseProperties.setPort(this.clickHouseSpringProperties.getPort());
        clickHouseProperties.setDatabase(this.clickHouseSpringProperties.getDatabase());
        clickHouseProperties.setUser(this.clickHouseSpringProperties.getUser());
        clickHouseProperties.setPassword(this.clickHouseSpringProperties.getPassword());

        return clickHouseProperties;
    }

    /**
     * Объект для соединений с базой данных
     * @return компонент, готовый для подключения к БД
     */
    @Bean
    @Primary
    public DataSource dataSource(){
        return new ClickHouseDataSource(this.clickHouseSpringProperties.getDatabaseURL(), clickHouseProperties());
    }

    /**
     * Объект JdbcTemplate, готовый для работы с ClickHouse
     * @return объект JdbcTemplate
     */
    @Bean
    @Primary
    public JdbcTemplate clickHouseJdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }

}
