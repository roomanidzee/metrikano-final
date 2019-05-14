package com.romanidze.metrikano.postgresservice.services.implementations;

import com.romanidze.metrikano.postgresservice.domain.UserWithLink;
import com.romanidze.metrikano.postgresservice.dto.UserWithLinkDTO;
import com.romanidze.metrikano.postgresservice.mappers.mapstruct.UserWithLinkMapper;
import com.romanidze.metrikano.postgresservice.mappers.mybatis.UserWithLinkDBMapper;
import com.romanidze.metrikano.postgresservice.services.interfaces.UserWithLinkService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 16.04.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Service
public class UserWithLinkServiceImpl implements UserWithLinkService {

    private final UserWithLinkDBMapper userWithLinkDBMapper;
    private final UserWithLinkMapper userWithLinkMapper;

    @Autowired
    public UserWithLinkServiceImpl(UserWithLinkDBMapper userWithLinkDBMapper, UserWithLinkMapper userWithLinkMapper) {
        this.userWithLinkDBMapper = userWithLinkDBMapper;
        this.userWithLinkMapper = userWithLinkMapper;
    }

    @Override
    public void saveUserWithLink(UserWithLinkDTO userWithLinkDTO) {

        UserWithLink userWithLink = this.userWithLinkMapper.dtoToDomain(userWithLinkDTO);

        this.userWithLinkDBMapper.save(userWithLink);

    }

    @Override
    public List<UserWithLinkDTO> showAllRecords() {

        List<UserWithLink> data = this.userWithLinkDBMapper.findAll();

        return data.stream()
                   .map(this.userWithLinkMapper::domainToDTO)
                   .collect(Collectors.toList());

    }

    @Override
    public List<UserWithLinkDTO> showAllRecordsByUsername(String username) {

        List<UserWithLink> data = this.userWithLinkDBMapper.findByUsername(username);

        return data.stream()
                   .map(this.userWithLinkMapper::domainToDTO)
                   .collect(Collectors.toList());

    }
}
