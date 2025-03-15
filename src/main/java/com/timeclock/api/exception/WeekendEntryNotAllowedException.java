package com.timeclock.api.exception;

public class WeekendEntryNotAllowedException extends IllegalArgumentException {
    public WeekendEntryNotAllowedException() {
    }

    public WeekendEntryNotAllowedException(String message) {
        super(message);
    }
}
