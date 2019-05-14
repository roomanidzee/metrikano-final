package com.romanidze.metrikano.postgresservice.services.interfaces;

import com.romanidze.metrikano.postgresservice.dto.UserWithLinkDTO;

import java.util.List;

/**
 * 16.04.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface UserWithLinkService {

    void saveUserWithLink(UserWithLinkDTO userWithLinkDTO);
    List<UserWithLinkDTO> showAllRecords();
    List<UserWithLinkDTO> showAllRecordsByUsername(String username);

}
