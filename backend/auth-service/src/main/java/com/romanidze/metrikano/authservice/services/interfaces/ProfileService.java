package com.romanidze.metrikano.authservice.services.interfaces;

import com.romanidze.metrikano.authservice.dto.response.ProfileDTO;
import org.springframework.security.core.Authentication;

/**
 * 10.05.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface ProfileService {

    void createProfile(ProfileDTO profileDTO);
    ProfileDTO getProfileByUser(Authentication authentication);

}
