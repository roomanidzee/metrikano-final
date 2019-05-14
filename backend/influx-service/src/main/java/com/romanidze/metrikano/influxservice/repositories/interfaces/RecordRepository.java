package com.romanidze.metrikano.influxservice.repositories.interfaces;

import com.romanidze.metrikano.influxservice.domain.Record;

import java.util.List;

/**
 * 28.02.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface RecordRepository {

    void save(Record record);
    List<Record> findAll();
    List<Record> findRecordsByIDs(List<String> ids);

}
