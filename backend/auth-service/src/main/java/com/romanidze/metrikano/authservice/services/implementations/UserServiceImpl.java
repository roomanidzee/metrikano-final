package com.romanidze.metrikano.authservice.services.implementations;

import com.romanidze.metrikano.authservice.domain.User;
import com.romanidze.metrikano.authservice.dto.response.UserDTO;
import com.romanidze.metrikano.authservice.mappers.mapstruct.UserMapper;
import com.romanidze.metrikano.authservice.services.interfaces.AuthenticationService;
import com.romanidze.metrikano.authservice.services.interfaces.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * 28.04.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(AuthenticationService authenticationService, UserMapper userMapper) {
        this.authenticationService = authenticationService;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO getUserInfo(Authentication authentication) {

        User user = this.authenticationService.getUserByAuthentication(authentication);

        return this.userMapper.domainToDTO(user);

    }
}
