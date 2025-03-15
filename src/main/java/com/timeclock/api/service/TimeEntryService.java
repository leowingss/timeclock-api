package com.timeclock.api.service;

import com.timeclock.api.domain.TimeEntry;
import com.timeclock.api.exception.EntriesPerDayException;
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
        checkEntriesPerDay(timeEntry);
        return repository.save(timeEntry);
    }

    private void checkEntriesPerDay(TimeEntry timeEntry) {
        var startDate = timeEntry.getTimestamp().toLocalDate().atStartOfDay();
        var endDate = startDate.plusDays(1);

        long countDays = repository.countByTimestampBetween(startDate, endDate);

        if (countDays >= 4) {
            throw new EntriesPerDayException("Only 4 entries per day");
        }
    }

    private static void checkIfIsWeekend(TimeEntry timeEntry) {
        var dayOfWeek = timeEntry.getTimestamp().getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new WeekendEntryNotAllowedException("You cannot register on weekends");
        }
    }

}
