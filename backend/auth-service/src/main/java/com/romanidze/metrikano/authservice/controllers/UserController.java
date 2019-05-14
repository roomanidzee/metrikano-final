package com.romanidze.metrikano.authservice.controllers;

import com.romanidze.metrikano.authservice.dto.response.UserDTO;
import com.romanidze.metrikano.authservice.services.interfaces.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rx.Single;

/**
 * 28.04.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public Single<ResponseEntity<UserDTO>> getUserInformation(Authentication authentication){
        return Single.just(ResponseEntity.ok(this.userService.getUserInfo(authentication)));
    }

}
