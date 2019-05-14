package com.romanidze.metrikano.postgresservice.mappers.mybatis;

import com.romanidze.metrikano.postgresservice.domain.UserWithLink;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 16.04.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Mapper
public interface UserWithLinkDBMapper {

    @Insert("INSERT INTO user_with_link(username, link, link_type) VALUES(#{username}, #{link}, #{linkType})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void save(UserWithLink userWithLink);

    @Select("SELECT * FROM user_with_link")
    @Results({
            @Result(property = "linkType", column = "link_type")
    })
    List<UserWithLink> findAll();

    @Select("SELECT * FROM user_with_link WHERE username = #{username}")
    @Results({
            @Result(property = "linkType", column = "link_type")
    })
    List<UserWithLink> findByUsername(@Param("username") String username);

}
