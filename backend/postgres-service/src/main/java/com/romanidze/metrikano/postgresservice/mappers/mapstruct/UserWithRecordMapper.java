package com.romanidze.metrikano.postgresservice.mappers.mapstruct;

import com.romanidze.metrikano.postgresservice.domain.UserWithRecord;
import com.romanidze.metrikano.postgresservice.dto.UserWithRecordDTO;

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
public interface UserWithRecordMapper {

    UserWithRecord dtoToDomain(final UserWithRecordDTO userWithRecordDTO);
    UserWithRecordDTO domainToDTO(final UserWithRecord userWithRecord);

}
