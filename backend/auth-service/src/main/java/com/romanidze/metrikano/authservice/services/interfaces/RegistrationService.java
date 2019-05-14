package com.romanidze.metrikano.authservice.services.interfaces;

import com.romanidze.metrikano.authservice.dto.request.RegistrationDTO;
import com.romanidze.metrikano.authservice.dto.response.RegistrationResponseDTO;

/**
 * 28.04.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface RegistrationService {

    RegistrationResponseDTO registerUser(RegistrationDTO registrationDTO);

}
