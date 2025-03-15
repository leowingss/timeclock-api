package com.timeclock.api.service;

import com.timeclock.api.commons.TimeEntryUtils;
import com.timeclock.api.domain.TimeEntry;
import com.timeclock.api.repository.TimeEntryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TimeEntryServiceTest {

    @InjectMocks
    private TimeEntryService service;

    @Mock
    private TimeEntryRepository repository;

    @InjectMocks
    private TimeEntryUtils timeEntryUtils;

    @Test
    @DisplayName("registerTimeEntry should register time entry when valid")
    void registerTimeEntry_RegisterTime_WhenSuccesful() {
        var timeEntryToBeSave = timeEntryUtils.newTimeEntryValidToSave();
        BDDMockito.when(repository.save(timeEntryToBeSave)).thenReturn(timeEntryToBeSave);

        var timeEntrySaved = service.registerTimeEntry(timeEntryToBeSave);

        Assertions.assertThat(timeEntrySaved).isNotNull().isEqualTo(timeEntryToBeSave);
    }


    @Test
    @DisplayName("registerTimeEntry should throws IllegalArgumentException when is an invalid date")
    void registerTimeEntry_ThrowsIllegalArgumentException_WhenIsWeekend() {
        var invalidTimeEntryToBeSave = timeEntryUtils.newTimeEntryInvalidToSave();

        Assertions.assertThatException()
                .isThrownBy(() -> service.registerTimeEntry(invalidTimeEntryToBeSave))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    @DisplayName("registerTimeEntry should throws IllegalArgumentException when more than 4 entries are attempted")
    void registerTimeEntry_ThrowsIllegalArgumentException_WhenMoreThanEntriesExceed() {
        var timeEntryToBeSave = timeEntryUtils.newTimeEntryValidToSave();
        BDDMockito.when(repository.countByTimestampBetween(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(4L);


        Assertions.assertThatException()
                .isThrownBy(() -> service.registerTimeEntry(timeEntryToBeSave))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("calculateWorkedHours should returns total hours worked by day")
    void calculateWorkedHours_ReturnsTotalHoursWorked_WhenValidEntrie() {
        LocalDate date = LocalDate.of(2025, 3, 17);
        List<TimeEntry> entries = timeEntryUtils.newTimeEntryListForWorkedHoursTest();

        BDDMockito.when(repository.findByTimestampBetween(date.atStartOfDay(), date.atStartOfDay().plusDays(1)))
                .thenReturn(entries);

        var totalHours = service.calculateWorkedHours(date);

        Assertions.assertThat(totalHours).isEqualTo(8.0);

    }

    @Test
    @DisplayName("calculateWorkedHours should return 0.0 when no entries exist")
    void calculateWorkedHours_ReturnsZero_WhenNoEntriesExist() {
        LocalDate date = LocalDate.of(2025, 3, 17);
        BDDMockito.when(repository.findByTimestampBetween(
                        date.atStartOfDay(), date.atStartOfDay().plusDays(1)))
                .thenReturn(Collections.emptyList());

        double totalHours = service.calculateWorkedHours(date);

        Assertions.assertThat(totalHours).isEqualTo(0.0);
    }


}