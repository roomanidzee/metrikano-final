package com.romanidze.metrikano.postgresservice.services.implementations;

import com.romanidze.metrikano.postgresservice.domain.UserWithRecord;
import com.romanidze.metrikano.postgresservice.dto.UserWithRecordDTO;
import com.romanidze.metrikano.postgresservice.mappers.mapstruct.UserWithRecordMapper;
import com.romanidze.metrikano.postgresservice.mappers.mybatis.UserWithRecordDBMapper;
import com.romanidze.metrikano.postgresservice.services.interfaces.UserWithRecordService;

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
public class UserWithRecordServiceImpl implements UserWithRecordService {

    private final UserWithRecordMapper userWithRecordMapper;
    private final UserWithRecordDBMapper userWithRecordDBMapper;

    @Autowired
    public UserWithRecordServiceImpl(UserWithRecordMapper userWithRecordMapper,
                                     UserWithRecordDBMapper userWithRecordDBMapper) {
        this.userWithRecordMapper = userWithRecordMapper;
        this.userWithRecordDBMapper = userWithRecordDBMapper;
    }

    @Override
    public void saveUserWithRecord(UserWithRecordDTO userWithRecordDTO) {

        UserWithRecord userWithRecord = this.userWithRecordMapper.dtoToDomain(userWithRecordDTO);
        this.userWithRecordDBMapper.save(userWithRecord);

    }

    @Override
    public List<UserWithRecordDTO> showAllRecords() {

        List<UserWithRecord> data = this.userWithRecordDBMapper.findAll();

        return data.stream()
                   .map(this.userWithRecordMapper::domainToDTO)
                   .collect(Collectors.toList());

    }

    @Override
    public List<UserWithRecordDTO> showAllRecordsByUsername(String id) {

        List<UserWithRecord> data = this.userWithRecordDBMapper.findAllByUsername(id);

        return data.stream()
                   .map(this.userWithRecordMapper::domainToDTO)
                   .collect(Collectors.toList());

    }
}
