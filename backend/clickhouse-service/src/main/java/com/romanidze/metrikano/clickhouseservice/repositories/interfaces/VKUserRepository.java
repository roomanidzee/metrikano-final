package com.romanidze.metrikano.clickhouseservice.repositories.interfaces;

import com.romanidze.metrikano.clickhouseservice.domain.VKUser;

import java.util.List;

/**
 * 08.03.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface VKUserRepository extends CRUDRepository<VKUser, String> {

    List<VKUser> getRecordsByIDs(List<String> ids);

}
