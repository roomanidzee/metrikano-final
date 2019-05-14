package com.romanidze.metrikano.authservice.controllers;

import com.romanidze.metrikano.authservice.dto.request.RegistrationDTO;
import com.romanidze.metrikano.authservice.dto.response.RegistrationResponseDTO;
import com.romanidze.metrikano.authservice.services.interfaces.RegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public Single<ResponseEntity<RegistrationResponseDTO>> register(@RequestBody RegistrationDTO registrationDTO){
        return Single.just(ResponseEntity.ok(this.registrationService.registerUser(registrationDTO)));
    }

}
