package com.romanidze.metrikano.clickhouseservice.repositories.interfaces;

import java.util.List;

/**
 * 08.03.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
public interface CRUDRepository<M, I> {

    List<M> findAll();
    void save(M model);
    M find(I id);
    void delete(I id);
    void update(M model);

}
