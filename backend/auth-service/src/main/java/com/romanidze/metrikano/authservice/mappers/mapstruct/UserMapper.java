package com.romanidze.metrikano.authservice.mappers.mapstruct;

import com.romanidze.metrikano.authservice.domain.User;
import com.romanidze.metrikano.authservice.dto.response.UserDTO;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 28.04.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UserMapper {

    UserDTO domainToDTO(final User user);

}
