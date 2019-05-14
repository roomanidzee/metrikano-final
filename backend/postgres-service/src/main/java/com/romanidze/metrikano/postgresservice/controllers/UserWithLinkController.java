package com.romanidze.metrikano.postgresservice.controllers;

import com.romanidze.metrikano.postgresservice.dto.UserWithLinkDTO;
import com.romanidze.metrikano.postgresservice.services.interfaces.UserWithLinkService;

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
@RequestMapping("/user/links")
public class UserWithLinkController {

    private final UserWithLinkService userWithLinkService;

    @Autowired
    public UserWithLinkController(UserWithLinkService userWithLinkService) {
        this.userWithLinkService = userWithLinkService;
    }

    @GetMapping("/data")
    public Single<ResponseEntity<List<UserWithLinkDTO>>> getData(){
        return Single.just(ResponseEntity.ok(this.userWithLinkService.showAllRecords()));
    }

    @GetMapping("/{username}")
    public Single<ResponseEntity<List<UserWithLinkDTO>>> getDataByUsername(@PathVariable("username") String username){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String requestUsername = ((UserDetails) authentication.getDetails()).getUsername();

        if(!requestUsername.equals(username)){
            throw new IllegalArgumentException("Ссылки по пользователю можно получить только их хозяину");
        }

        return Single.just(ResponseEntity.ok(this.userWithLinkService.showAllRecordsByUsername(username)));

    }

}
