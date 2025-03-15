package com.timeclock.api.commons;

import com.timeclock.api.domain.EntryType;
import com.timeclock.api.domain.TimeEntry;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class TimeEntryUtils {

    public List<TimeEntry> newTimeEntryList() {
        var entrie1 = TimeEntry.builder().timestamp(LocalDateTime.now()).type(EntryType.CLOCK_IN).build();
        var entrie2 = TimeEntry.builder().timestamp(LocalDateTime.now()).type(EntryType.CLOCK_OUT).build();
        var entrie3 = TimeEntry.builder().timestamp(LocalDateTime.now()).type(EntryType.CLOCK_IN).build();
        var entrie4 = TimeEntry.builder().timestamp(LocalDateTime.now()).type(EntryType.CLOCK_OUT).build();

        return new ArrayList<>(List.of(entrie1, entrie2, entrie3, entrie4));
    }

    public TimeEntry newTimeEntryValidToSave() {
        return TimeEntry.builder()
                .timestamp(LocalDateTime.of(2025, 3, 19, 9, 0))
                .type(EntryType.CLOCK_IN)
                .build();
    }

    public TimeEntry newTimeEntryInvalidToSave() {
        return TimeEntry.builder()
                .timestamp(LocalDateTime.of(2025, 3, 16, 9, 0))
                .type(EntryType.CLOCK_IN)
                .build();
    }



}
