package com.romanidze.metrikano.clickhouseservice.controllers;

import com.romanidze.metrikano.clickhouseservice.dto.RecordsInfoDTO;
import com.romanidze.metrikano.clickhouseservice.dto.VKUserDTO;
import com.romanidze.metrikano.clickhouseservice.services.interfaces.VKUserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import rx.Single;

/**
 * 08.03.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@RestController
@RequestMapping({"/admin", "/user"})
public class VKUserController {

    private final VKUserService vkUserService;

    @Autowired
    public VKUserController(VKUserService vkUserService) {
        this.vkUserService = vkUserService;
    }

    @GetMapping(value = "/data", produces = MediaType.APPLICATION_JSON_VALUE)
    public Single<ResponseEntity<List<VKUserDTO>>> getData(){
        return Single.just(ResponseEntity.ok(this.vkUserService.getAllData()));
    }

    @GetMapping("/data/{id}")
    public Single<ResponseEntity<VKUserDTO>> getSingleRecord(@PathVariable("id") String id){
        return Single.just(ResponseEntity.ok(this.vkUserService.getRecordByID(id)));
    }

    @GetMapping("/data/for_ids")
    public Single<ResponseEntity<List<VKUserDTO>>> getDataForUser(@RequestBody RecordsInfoDTO recordsInfoDTO){
        return Single.just(ResponseEntity.ok(this.vkUserService.getRecordsInfo(recordsInfoDTO.getRecordIdentificators())));
    }

}
