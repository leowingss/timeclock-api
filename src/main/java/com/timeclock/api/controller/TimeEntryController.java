package com.timeclock.api.controller;

import com.timeclock.api.domain.TimeEntry;
import com.timeclock.api.mapper.TimeEntryMapper;
import com.timeclock.api.request.TimeEntryPostRequest;
import com.timeclock.api.service.TimeEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/time-entry")
@RequiredArgsConstructor
public class TimeEntryController {

    private final TimeEntryService service;
    private final TimeEntryMapper mapper;


    @PostMapping
    public ResponseEntity<TimeEntry> registerTimeEntry(@RequestBody TimeEntryPostRequest timeEntryPostRequest) {

        var timeEntryEntity = mapper.toTimeEntryEntity(timeEntryPostRequest);
        var timeEntry1 = service.registerTimeEntry(timeEntryEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(timeEntry1);
    }


}
