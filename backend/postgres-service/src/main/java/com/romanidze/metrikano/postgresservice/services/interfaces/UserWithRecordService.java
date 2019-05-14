package com.romanidze.metrikano.postgresservice.services.interfaces;

import com.romanidze.metrikano.postgresservice.dto.UserWithRecordDTO;

import java.util.List;

/**
 * 16.04.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface UserWithRecordService {

    void saveUserWithRecord(UserWithRecordDTO userWithRecordDTO);
    List<UserWithRecordDTO> showAllRecords();
    List<UserWithRecordDTO> showAllRecordsByUsername(String username);

}
