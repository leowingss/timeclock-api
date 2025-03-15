package com.timeclock.api.service;

import com.timeclock.api.domain.TimeEntry;
import com.timeclock.api.exception.WeekendEntryNotAllowedException;
import com.timeclock.api.repository.TimeEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;

@Service
@RequiredArgsConstructor
public class TimeEntryService {
    private final TimeEntryRepository repository;

    public TimeEntry registerTimeEntry(TimeEntry timeEntry) {
        checkIfIsWeekend(timeEntry);
        return repository.save(timeEntry);
    }

    private static void checkIfIsWeekend(TimeEntry timeEntry) {
        var dayOfWeek = timeEntry.getTimestamp().getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new WeekendEntryNotAllowedException("You cannot register on weekends");
        }
    }

}
