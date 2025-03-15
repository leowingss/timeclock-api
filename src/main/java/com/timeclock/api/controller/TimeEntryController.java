package com.timeclock.api.controller;

import com.timeclock.api.domain.TimeEntry;
import com.timeclock.api.service.TimeEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;

@RestController
@RequestMapping("/api/time-entry")
@RequiredArgsConstructor
public class TimeEntryController {

    private final TimeEntryService service;

    @PostMapping
    public ResponseEntity<TimeEntry> registerTimeEntry(@RequestBody TimeEntry timeEntry) {
        var timeEntry1 = service.registerTimeEntry(timeEntry);

        return ResponseEntity.status(HttpStatus.CREATED).body(timeEntry1);
    }

}
