package com.timeclock.api.service;

import com.timeclock.api.domain.TimeEntry;
import com.timeclock.api.repository.TimeEntryRepository;
import org.springframework.stereotype.Service;

@Service
public class TimeEntryService {
    private final TimeEntryRepository repository;

    public TimeEntryService(TimeEntryRepository repository ) {
        this.repository = repository;
    }

    public TimeEntry registerTimeEntry(TimeEntry timeEntry) {
        return repository.save(timeEntry);
    }

}
