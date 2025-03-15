package com.timeclock.api.repository;

import com.timeclock.api.commons.TimeEntryUtils;
import com.timeclock.api.domain.TimeEntry;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@Import(TimeEntryUtils.class)
class TimeEntryRepositoryTest {

    @Autowired
    private TimeEntryRepository repository;

    @Autowired
    private TimeEntryUtils timeEntryUtils;

    @Test
    @DisplayName("countByTimestampBetween should return count of time entries")
    void countByTimestampBetween_ReturnsCountOfTimeEntries() {

        var timeEntries = timeEntryUtils.newTimeEntryList();

        repository.saveAll(timeEntries);

        LocalDateTime startDate = timeEntries.get(0).getTimestamp().toLocalDate().atStartOfDay();
        LocalDateTime endDate = startDate.plusDays(1);

        long count = repository.countByTimestampBetween(startDate, endDate);
        Assertions.assertThat(count).isEqualTo(4);

    }

    @Test
    @DisplayName("findByTimestampBetween should return correct entries when valid timestamps exist")
    void findByTimestampBetween_ReturnsEntries_WhenValidTimestampsExist() {
        List<TimeEntry> timeEntries = timeEntryUtils.newTimeEntryListForWorkedHoursTest();
        repository.saveAll(timeEntries);

        LocalDateTime startDate = LocalDate.of(2025, 3, 17).atStartOfDay();
        LocalDateTime endDate = startDate.plusDays(1);

        List<TimeEntry> foundEntries = repository.findByTimestampBetween(startDate, endDate);

        Assertions.assertThat(foundEntries)
                .isNotEmpty()
                .hasSize(4)
                .hasSameElementsAs(timeEntries);
    }



}