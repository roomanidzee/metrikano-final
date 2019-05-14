package com.romanidze.metrikano.clickhouseservice.mappers;

import com.romanidze.metrikano.clickhouseservice.domain.VKUser;
import com.romanidze.metrikano.clickhouseservice.dto.VKUserDTO;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * 04.03.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface VKUserMapper {

     @Mapping(source = "cityDTO.id", target = "cityID", defaultExpression = "java(Long.valueOf(0))")
     @Mapping(source = "cityDTO.title", target = "cityTitle", defaultExpression = "java(new String())")
     @Mapping(source = "countryDTO.id", target = "countryID", defaultExpression = "java(Long.valueOf(0))")
     @Mapping(source = "countryDTO.title", target = "countryTitle", defaultExpression = "java(new String())")
     @Mapping(source = "lastSeenDTO.time", target = "lastSeenTime")
     @Mapping(source = "lastSeenDTO.platform", target = "platform")
     @Mapping(source = "countersDTO.photosCounter", target = "photoCounter", defaultExpression = "java(Long.valueOf(0))")
     @Mapping(source = "countersDTO.friendsCounter", target = "friendCounter", defaultExpression = "java(Long.valueOf(0))")
     @Mapping(source = "closed", target = "isClosed")
     VKUser dtoToDomain(final VKUserDTO vkUserDTO);

     @Mapping(source = "cityID", target = "cityDTO.id", defaultExpression = "java(Long.valueOf(0))")
     @Mapping(source = "cityTitle", target = "cityDTO.title", defaultExpression = "java(new String())")
     @Mapping(source = "countryID", target = "countryDTO.id", defaultExpression = "java(Long.valueOf(0))")
     @Mapping(source = "countryTitle", target = "countryDTO.title", defaultExpression = "java(new String())")
     @Mapping(source = "lastSeenTime", target = "lastSeenDTO.time")
     @Mapping(source = "platform", target = "lastSeenDTO.platform")
     @Mapping(source = "photoCounter", target = "countersDTO.photosCounter", defaultExpression = "java(Long.valueOf(0))")
     @Mapping(source = "friendCounter", target = "countersDTO.friendsCounter", defaultExpression = "java(Long.valueOf(0))")
     @Mapping(source = "isClosed", target = "closed")
     VKUserDTO domainToDTO(final VKUser vkUser);

}
