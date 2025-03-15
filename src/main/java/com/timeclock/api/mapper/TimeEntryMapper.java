package com.timeclock.api.mapper;

import com.timeclock.api.domain.TimeEntry;
import com.timeclock.api.request.TimeEntryPostRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TimeEntryMapper {

    @Mapping(target = "timestamp", expression = "java(java.time.LocalDateTime.now())")
    TimeEntry toTimeEntryEntity(TimeEntryPostRequest timeEntryPostRequest);

}
