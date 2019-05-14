package com.romanidze.metrikano.authservice.services.interfaces;

import com.romanidze.metrikano.authservice.dto.response.UserDTO;
import org.springframework.security.core.Authentication;

/**
 * 28.04.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface UserService {

    UserDTO getUserInfo(Authentication authentication);

}
