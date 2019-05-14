package com.romanidze.metrikano.authservice.services.implementations;

import com.romanidze.metrikano.authservice.domain.Profile;
import com.romanidze.metrikano.authservice.domain.User;
import com.romanidze.metrikano.authservice.dto.response.ProfileDTO;
import com.romanidze.metrikano.authservice.mappers.mapstruct.ProfileMapper;
import com.romanidze.metrikano.authservice.mappers.mybatis.ProfileDBMapper;
import com.romanidze.metrikano.authservice.services.interfaces.AuthenticationService;
import com.romanidze.metrikano.authservice.services.interfaces.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * 10.05.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class ProfileServiceImpl implements ProfileService {

    private final AuthenticationService authenticationService;
    private final ProfileMapper profileMapper;
    private final ProfileDBMapper profileDBMapper;

    @Autowired
    public ProfileServiceImpl(AuthenticationService authenticationService,
                              ProfileMapper profileMapper, ProfileDBMapper profileDBMapper) {
        this.authenticationService = authenticationService;
        this.profileMapper = profileMapper;
        this.profileDBMapper = profileDBMapper;
    }

    @Override
    public void createProfile(ProfileDTO profileDTO) {

        Profile profile = this.profileMapper.dtoToDomain(profileDTO);
        this.profileDBMapper.save(profile);

    }

    @Override
    public ProfileDTO getProfileByUser(Authentication authentication) {

        User user = this.authenticationService.getUserByAuthentication(authentication);
        Profile profile = this.profileDBMapper.findByUser(user.getId());

        return this.profileMapper.domainToDTO(profile);
    }
}
