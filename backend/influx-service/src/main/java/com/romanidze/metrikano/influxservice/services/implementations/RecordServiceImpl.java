package com.romanidze.metrikano.influxservice.services.implementations;

import com.romanidze.metrikano.influxservice.config.kafka.producer.KafkaProducerProperties;
import com.romanidze.metrikano.influxservice.domain.Record;
import com.romanidze.metrikano.influxservice.dto.RecordDTO;
import com.romanidze.metrikano.influxservice.dto.UserRecordDTO;
import com.romanidze.metrikano.influxservice.mappers.RecordMapper;
import com.romanidze.metrikano.influxservice.repositories.interfaces.RecordRepository;
import com.romanidze.metrikano.influxservice.services.interfaces.RecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 28.02.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class RecordServiceImpl implements RecordService {

    private final RecordRepository recordRepository;
    private final RecordMapper recordMapper;
    private final KafkaProducerProperties kafkaProducerProperties;
    private final KafkaTemplate<String, UserRecordDTO> kafkaTemplate;

    @Autowired
    public RecordServiceImpl(RecordRepository recordRepository, RecordMapper recordMapper,
                             KafkaProducerProperties kafkaProducerProperties,
                             KafkaTemplate<String, UserRecordDTO> kafkaTemplate) {
        this.recordRepository = recordRepository;
        this.recordMapper = recordMapper;
        this.kafkaProducerProperties = kafkaProducerProperties;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void saveRecord(RecordDTO recordDTO) {

        String newGroupID = recordDTO.getGroupID()
                                     .replace("b", "")
                                     .replace("'", "")
                                     .replace("-", "");

        recordDTO.setGroupID(newGroupID);
        recordDTO.setId(UUID.randomUUID().toString());

        if(recordDTO.getUsername() != null){

            UserRecordDTO userRecordDTO = UserRecordDTO.builder()
                                                       .username(recordDTO.getUsername())
                                                       .recordID(recordDTO.getId())
                                                       .recordType("VK_GROUP")
                                                       .build();
            this.kafkaTemplate.send(this.kafkaProducerProperties.getTopic(), userRecordDTO);

        }else{
            recordDTO.setUsername("");
        }

        Record record = this.recordMapper.dtoToDomain(recordDTO);

        this.recordRepository.save(record);
    }

    @Override
    public List<RecordDTO> getAllRecords() {

        List<Record> records = this.recordRepository.findAll();

        return records.stream()
                      .map(this.recordMapper::domainToDTO)
                      .collect(Collectors.toList());
    }

    @Override
    public List<RecordDTO> getRecordsInformation(List<String> ids) {

        List<Record> records = this.recordRepository.findRecordsByIDs(ids);

        return records.stream()
                      .map(this.recordMapper::domainToDTO)
                      .collect(Collectors.toList());
    }
}
