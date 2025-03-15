package com.timeclock.api.exception;

public class EntriesPerDayException extends IllegalArgumentException {
    public EntriesPerDayException() {
    }

    public EntriesPerDayException(String message) {
        super(message);
    }
}
