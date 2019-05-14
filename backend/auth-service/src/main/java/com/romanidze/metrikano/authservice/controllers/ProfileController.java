package com.romanidze.metrikano.authservice.controllers;

import com.romanidze.metrikano.authservice.dto.response.ProfileDTO;
import com.romanidze.metrikano.authservice.services.interfaces.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import rx.Single;

/**
 * 10.05.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/create")
    public Single<ResponseEntity<String>> createProfile(@RequestBody ProfileDTO profileDTO){

        this.profileService.createProfile(profileDTO);
        return Single.just(ResponseEntity.ok("{\"message\": \"Профиль сохранён\"}"));

    }

    @GetMapping("/get")
    public Single<ResponseEntity<ProfileDTO>> getProfileInfo(Authentication authentication){

        return Single.just(ResponseEntity.ok(this.profileService.getProfileByUser(authentication)));

    }

}
