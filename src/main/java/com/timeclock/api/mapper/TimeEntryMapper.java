package com.timeclock.api.mapper;

import com.timeclock.api.domain.TimeEntry;
import com.timeclock.api.request.TimeEntryPostRequest;
import com.timeclock.api.response.TimeEntryPostResponse;
import com.timeclock.api.response.TotalWorkedHoursResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface TimeEntryMapper {

    @Mapping(target = "timestamp", expression = "java(timeEntryPostRequest.getTimestamp() != null ? timeEntryPostRequest.getTimestamp() : java.time.LocalDateTime.now())")
    @Mapping(target = "id", ignore = true)
    TimeEntry toTimeEntryEntity(TimeEntryPostRequest timeEntryPostRequest);

    TimeEntryPostResponse toTimeEntryPostResponse(TimeEntry timeEntry);

    default TotalWorkedHoursResponse toTotalWorkedHoursResponse(LocalDate date, double totalHours) {
        return new TotalWorkedHoursResponse(date, totalHours);
    }

}
