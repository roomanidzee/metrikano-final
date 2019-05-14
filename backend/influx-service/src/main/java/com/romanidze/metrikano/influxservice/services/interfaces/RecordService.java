package com.romanidze.metrikano.influxservice.services.interfaces;

import com.romanidze.metrikano.influxservice.dto.RecordDTO;

import java.util.List;

/**
 * 28.02.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface RecordService {

    void saveRecord(RecordDTO recordDTO);
    List<RecordDTO> getAllRecords();
    List<RecordDTO> getRecordsInformation(List<String> ids);

}
