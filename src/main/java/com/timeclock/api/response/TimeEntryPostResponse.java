package com.timeclock.api.response;

import com.timeclock.api.domain.EntryType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class TimeEntryPostResponse {
    private Long id;
    private String type;
    private LocalDateTime timestamp;

}
