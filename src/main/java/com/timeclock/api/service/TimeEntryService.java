package com.timeclock.api.service;

import com.timeclock.api.domain.TimeEntry;
import com.timeclock.api.repository.TimeEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimeEntryService {
    private final TimeEntryRepository repository;

    public TimeEntry registerTimeEntry(TimeEntry timeEntry) {
        return repository.save(timeEntry);
    }

}
