package com.timeclock.api.controller;

import com.timeclock.api.exception.DefaultErrorMessage;
import com.timeclock.api.mapper.TimeEntryMapper;
import com.timeclock.api.request.TimeEntryPostRequest;
import com.timeclock.api.response.TimeEntryPostResponse;
import com.timeclock.api.response.TotalWorkedHoursResponse;
import com.timeclock.api.service.TimeEntryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/time-entry")
@RequiredArgsConstructor
@Tag(name = "TimeClock API", description = "API for managing employee time entries")
public class TimeEntryController {

    private final TimeEntryService service;
    private final TimeEntryMapper mapper;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Register a new time entry",
            description = "Creates a new time entry for current day. If the timestamp is not provided, the current time is" +
                    " automatically assigned. Time entries are not allowed on weekends."
    )
    public ResponseEntity<TimeEntryPostResponse> registerTimeEntry(@RequestBody TimeEntryPostRequest timeEntryPostRequest) {

        var timeEntryEntity = mapper.toTimeEntryEntity(timeEntryPostRequest);
        var timeEntryRegistered = service.registerTimeEntry(timeEntryEntity);

        var timeEntryPostResponse = mapper.toTimeEntryPostResponse(timeEntryRegistered);

        return ResponseEntity.status(HttpStatus.CREATED).body(timeEntryPostResponse);
    }

    @GetMapping("/worked-hours")
    @Operation(
            summary = "Get total worked hours for a specific day",
            description = "Retrieve the total number of hours worked. If no records exist, it returns 0.0"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Total worked hours retrieved successfully",
                    content = @Content(schema = @Schema(implementation = TotalWorkedHoursResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid date format or insufficient entries",
                    content = @Content(schema = @Schema(implementation = DefaultErrorMessage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<TotalWorkedHoursResponse> getWorkedHours(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        double totalHours = service.calculateWorkedHours(date);
        var totalWorkedHoursResponse = mapper.toTotalWorkedHoursResponse(date, totalHours);

        return ResponseEntity.ok(totalWorkedHoursResponse);
    }


}
