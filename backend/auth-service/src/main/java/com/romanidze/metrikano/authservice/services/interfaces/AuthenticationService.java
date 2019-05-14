package com.romanidze.metrikano.authservice.services.interfaces;

import com.romanidze.metrikano.authservice.domain.User;
import com.romanidze.metrikano.authservice.dto.request.LoginDTO;
import com.romanidze.metrikano.authservice.dto.response.LoginResponseDTO;

import com.romanidze.metrikano.authservice.dto.response.TokenInfoDTO;
import org.springframework.security.core.Authentication;

/**
 * 28.04.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface AuthenticationService {

    User getUserByAuthentication(Authentication authentication);
    TokenInfoDTO checkToken(Authentication authentication);
    LoginResponseDTO loginUser(LoginDTO loginDTO);

}
