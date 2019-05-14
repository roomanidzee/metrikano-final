package com.romanidze.metrikano.authservice.services.implementations;

import com.romanidze.metrikano.authservice.config.security.enums.Role;
import com.romanidze.metrikano.authservice.config.security.enums.UserState;
import com.romanidze.metrikano.authservice.domain.User;
import com.romanidze.metrikano.authservice.dto.request.RegistrationDTO;
import com.romanidze.metrikano.authservice.dto.response.RegistrationResponseDTO;
import com.romanidze.metrikano.authservice.mappers.mybatis.UserDBMapper;
import com.romanidze.metrikano.authservice.services.interfaces.RegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 28.04.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserDBMapper userDBMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationServiceImpl(UserDBMapper userDBMapper,
                                   PasswordEncoder passwordEncoder) {
        this.userDBMapper = userDBMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegistrationResponseDTO registerUser(RegistrationDTO registrationDTO) {

        User user = User.builder()
                        .username(registrationDTO.getUsername())
                        .password(this.passwordEncoder.encode(registrationDTO.getPassword()))
                        .role(Role.USER.toString())
                        .state(UserState.CONFIRMED.toString())
                        .build();

        this.userDBMapper.save(user);

        return RegistrationResponseDTO.builder()
                                      .username(user.getUsername())
                                      .state(user.getState())
                                      .build();


    }
}
