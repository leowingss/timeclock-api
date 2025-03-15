package com.timeclock.api.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimeEntryPostResponse {
    private Long id;
    private String type;
    private LocalDateTime timestamp;

}
