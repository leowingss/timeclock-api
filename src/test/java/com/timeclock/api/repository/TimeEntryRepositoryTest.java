package com.timeclock.api.repository;

import com.timeclock.api.commons.TimeEntryUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;

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
}