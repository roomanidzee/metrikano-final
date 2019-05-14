package com.romanidze.metrikano.postgresservice.mappers.mapstruct;

import com.romanidze.metrikano.postgresservice.domain.UserWithLink;
import com.romanidze.metrikano.postgresservice.dto.UserWithLinkDTO;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 16.04.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UserWithLinkMapper {

    UserWithLink dtoToDomain(final UserWithLinkDTO userWithLinkDTO);
    UserWithLinkDTO domainToDTO(final UserWithLink userWithLink);

}
