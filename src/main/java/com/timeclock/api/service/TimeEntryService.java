package com.timeclock.api.service;

import com.timeclock.api.domain.TimeEntry;
import com.timeclock.api.exception.EntriesPerDayException;
import com.timeclock.api.exception.WeekendEntryNotAllowedException;
import com.timeclock.api.repository.TimeEntryRepository;
import com.timeclock.api.response.TotalWorkedHoursResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    public double calculateWorkedHours(LocalDate date) {
        var startDate = date.atStartOfDay();
        var endDate = startDate.plusDays(1);

        List<TimeEntry> entries = repository.findByTimestampBetween(startDate, endDate);

        if (entries.isEmpty()) {
            return 0.0;
        }

        if (entries.size() < 4) {
            throw new IllegalArgumentException("At least 4 entries are required to calculate worked hours.");
        }

        if (entries.size() % 2 != 0) {
            entries = entries.subList(0, entries.size() - 1);
        }

        double totalMinutes = 0.0;

        for (int i = 0; i < entries.size(); i += 2) {
            var clockIn = entries.get(i).getTimestamp();
            var clockOut = entries.get(i + 1).getTimestamp();
            totalMinutes += Duration.between(clockIn, clockOut).toMinutes();
        }

        return totalMinutes / 60.0;


    }

}
