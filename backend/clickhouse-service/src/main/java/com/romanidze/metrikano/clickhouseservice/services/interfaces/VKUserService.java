package com.romanidze.metrikano.clickhouseservice.services.interfaces;

import com.romanidze.metrikano.clickhouseservice.dto.VKUserDTO;

import java.util.List;

/**
 * 08.03.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface VKUserService {

    void saveVKUserData(VKUserDTO vkUserDTO);
    List<VKUserDTO> getAllData();
    VKUserDTO getRecordByID(String recordID);
    List<VKUserDTO> getRecordsInfo(List<String> recordsIDs);

}
