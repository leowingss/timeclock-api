package com.timeclock.api.service;

import com.timeclock.api.commons.TimeEntryUtils;
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

}