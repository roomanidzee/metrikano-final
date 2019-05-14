package com.romanidze.metrikano.influxservice.controllers;

import com.romanidze.metrikano.influxservice.dto.RecordDTO;
import com.romanidze.metrikano.influxservice.dto.RecordInformationDTO;
import com.romanidze.metrikano.influxservice.services.interfaces.RecordService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import rx.Single;

/**
 * 28.02.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@RestController
@RequestMapping({"/admin", "/user"})
public class RecordController {

    private final RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
    public Single<ResponseEntity<List<RecordDTO>>> getData(){
        return Single.just(ResponseEntity.ok(this.recordService.getAllRecords()));
    }

    @GetMapping("/data/for_ids")
    public Single<ResponseEntity<List<RecordDTO>>> getDataForUser(@RequestBody RecordInformationDTO recordInformationDTO){
        return Single.just(ResponseEntity.ok(this.recordService.getRecordsInformation(recordInformationDTO.getRecordIdentificators())));
    }

}
