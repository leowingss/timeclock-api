package com.timeclock.api.repository;

import com.timeclock.api.commons.TimeEntryUtils;
import com.timeclock.api.domain.TimeEntry;
import lombok.Data;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

        var timeEntries = timeEntryUtils.newTimeEntry();

        repository.saveAll(timeEntries);

        LocalDateTime startDate = timeEntries.get(0).getTimestamp().toLocalDate().atStartOfDay();
        LocalDateTime endDate = startDate.plusDays(1);

        long count = repository.countByTimestampBetween(startDate, endDate);
        Assertions.assertThat(count).isEqualTo(4);

    }
}