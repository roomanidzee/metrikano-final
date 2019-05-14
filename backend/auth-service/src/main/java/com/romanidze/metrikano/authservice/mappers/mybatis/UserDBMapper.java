package com.romanidze.metrikano.authservice.mappers.mybatis;

import com.romanidze.metrikano.authservice.domain.User;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * 28.04.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Mapper
public interface UserDBMapper {

    @Insert("INSERT INTO users(username, password, user_role, user_state) VALUES(#{username}, #{password}, #{role}, #{state})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void save(User user);

    @Select("SELECT * FROM users")
    @Results({
            @Result(column = "user_role", property = "role"),
            @Result(column = "user_state", property = "state")
    })
    List<User> findAll();

    @Select("SELECT * FROM users WHERE username = #{username}")
    @Results({
            @Result(column = "user_role", property = "role"),
            @Result(column = "user_state", property = "state")
    })
    Optional<User> findByUsername(@Param("username") String username);

}
