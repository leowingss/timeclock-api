package com.timeclock.api.repository;

import com.timeclock.api.domain.TimeEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {
    Long countByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate);
}
