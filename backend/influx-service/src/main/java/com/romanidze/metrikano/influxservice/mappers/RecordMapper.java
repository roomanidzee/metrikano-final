package com.romanidze.metrikano.influxservice.mappers;

import com.romanidze.metrikano.influxservice.domain.Record;
import com.romanidze.metrikano.influxservice.dto.RecordDTO;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 28.02.2019
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface RecordMapper {

    RecordDTO domainToDTO(final Record record);
    Record dtoToDomain(final RecordDTO recordDTO);
}
