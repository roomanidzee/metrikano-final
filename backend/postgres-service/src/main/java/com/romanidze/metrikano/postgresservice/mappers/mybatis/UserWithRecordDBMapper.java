package com.romanidze.metrikano.postgresservice.mappers.mybatis;

import com.romanidze.metrikano.postgresservice.domain.UserWithRecord;

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
public interface UserWithRecordDBMapper {

    @Insert("INSERT INTO user_with_record(username, record_id, record_type) " +
            "VALUES (#{username}, #{recordID}, #{recordType})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void save(UserWithRecord userWithRecord);

    @Select("SELECT * FROM user_with_record")
    @Results({
            @Result(property = "recordID", column = "record_id"),
            @Result(property = "recordType", column = "record_type")
    })
    List<UserWithRecord> findAll();

    @Select("SELECT * FROM user_with_record WHERE username = #{username}")
    @Results({
            @Result(property = "recordID", column = "record_id"),
            @Result(property = "recordType", column = "record_type")
    })
    List<UserWithRecord> findAllByUsername(@Param("username") String username);

}
