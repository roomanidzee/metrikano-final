package com.romanidze.metrikano.influxservice.repositories.implementations;

import com.romanidze.metrikano.influxservice.domain.Record;
import com.romanidze.metrikano.influxservice.repositories.interfaces.RecordRepository;

import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 28.02.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Repository
public class RecordRepositoryImpl implements RecordRepository {

    private final InfluxDBTemplate<Record> influxDBTemplate;

    private static final String SELECT_QUERY = "SELECT * FROM group_metrics";

    @Autowired
    public RecordRepositoryImpl(InfluxDBTemplate<Record> influxDBTemplate) {
        this.influxDBTemplate = influxDBTemplate;
    }

    @Override
    public void save(Record record) {
        this.influxDBTemplate.write(record);
    }

    @Override
    public List<Record> findAll() {

        QueryResult queryResult =
                this.influxDBTemplate.query(new Query(SELECT_QUERY, this.influxDBTemplate.getDatabase()));

        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();

        return resultMapper.toPOJO(queryResult, Record.class);
    }

    @Override
    public List<Record> findRecordsByIDs(List<String> ids) {

        String dynamicString = IntStream.range(1, ids.size() - 2)
                                        .mapToObj(i -> ids.get(i) + "|")
                                        .collect(Collectors.joining());

        String formattedString = MessageFormat.format("/{}|{}|{}/",
                                                                   ids.get(0), dynamicString, ids.get(ids.size() - 1));

        String sqlQuery = MessageFormat.format("SELECT * FROM group_metrics WHERE id =~ {}", formattedString);

        QueryResult queryResult = this.influxDBTemplate.query(new Query(sqlQuery, this.influxDBTemplate.getDatabase()));

        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();

        return resultMapper.toPOJO(queryResult, Record.class);

    }
}
