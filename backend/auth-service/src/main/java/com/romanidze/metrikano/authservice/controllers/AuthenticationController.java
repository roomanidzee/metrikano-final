package com.romanidze.metrikano.authservice.controllers;

import com.romanidze.metrikano.authservice.dto.request.LoginDTO;
import com.romanidze.metrikano.authservice.dto.response.LoginResponseDTO;
import com.romanidze.metrikano.authservice.dto.response.TokenInfoDTO;
import com.romanidze.metrikano.authservice.services.interfaces.AuthenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public Single<ResponseEntity<LoginResponseDTO>> login(@RequestBody LoginDTO loginDTO){
        return Single.just(ResponseEntity.ok(this.authenticationService.loginUser(loginDTO)));
    }

    @PostMapping("/check_auth")
    public Single<ResponseEntity<TokenInfoDTO>> checkAuth(Authentication authentication){
        return Single.just(ResponseEntity.ok(this.authenticationService.checkToken(authentication)));
    }

}
