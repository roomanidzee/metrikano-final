package com.romanidze.metrikano.postgresservice.controllers;

import com.romanidze.metrikano.postgresservice.dto.UserWithRecordDTO;
import com.romanidze.metrikano.postgresservice.services.interfaces.UserWithRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import rx.Single;

import java.util.List;

/**
 * 16.04.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@RestController
@RequestMapping("/user/records")
public class UserWithRecordController {

    private final UserWithRecordService userWithRecordService;

    @Autowired
    public UserWithRecordController(UserWithRecordService userWithRecordService) {
        this.userWithRecordService = userWithRecordService;
    }

    @GetMapping("/data")
    public Single<ResponseEntity<List<UserWithRecordDTO>>> getData(){
        return Single.just(ResponseEntity.ok(this.userWithRecordService.showAllRecords()));
    }

    @GetMapping("/{username}")
    public Single<ResponseEntity<List<UserWithRecordDTO>>> getDataByUsername(@PathVariable("username") String username){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String requestUsername = ((UserDetails) authentication.getDetails()).getUsername();

        if(!requestUsername.equals(username)){
            throw new IllegalArgumentException("Записи по пользователю можно получить только их хозяину");
        }

        return Single.just(ResponseEntity.ok(this.userWithRecordService.showAllRecordsByUsername(username)));

    }


}
