package com.timeclock.api.request;

import com.timeclock.api.domain.EntryType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TimeEntryPostRequest {
    private EntryType type;

}
