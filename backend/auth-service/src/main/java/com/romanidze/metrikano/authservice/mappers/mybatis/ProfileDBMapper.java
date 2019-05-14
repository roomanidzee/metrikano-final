package com.romanidze.metrikano.authservice.mappers.mybatis;

import com.romanidze.metrikano.authservice.domain.Profile;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 28.04.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Mapper
public interface ProfileDBMapper {

    @Insert("INSERT INTO profiles(user_id, surname, name, patronymic, email, phone_number) " +
            "VALUES(#{userID}, #{surname}, #{name}, #{patronymic}, #{email}, #{phoneNumber})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void save(Profile profile);

    @Select("SELECT * FROM profiles")
    @Results({
            @Result(column = "phone_number", property = "phoneNumber"),
            @Result(column = "user_id", property = "userID")
    })
    List<Profile> findAll();

    @Select("SELECT * FROM profiles WHERE email = #{email}")
    @Results({
            @Result(column = "phone_number", property = "phoneNumber"),
            @Result(column = "user_id", property = "userID")
    })
    Profile findByEmail(@Param("email") String email);

    @Select("SELECT * FROM profiles WHERE user_id = #{userID}")
    @Results({
            @Result(column = "phone_number", property = "phoneNumber"),
            @Result(column = "user_id", property = "userID")
    })
    Profile findByUser(@Param("userID") Long userID);

}
