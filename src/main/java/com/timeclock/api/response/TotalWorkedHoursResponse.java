package com.timeclock.api.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TotalWorkedHoursResponse {
    private LocalDate date;
    private double totalHours;
}
