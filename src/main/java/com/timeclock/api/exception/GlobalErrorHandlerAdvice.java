package com.timeclock.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorHandlerAdvice {
    @ExceptionHandler(WeekendEntryNotAllowedException.class)
    public ResponseEntity<DefaultErrorMessage> handleWeekendEntryNotAllowed(WeekendEntryNotAllowedException e) {
        var error = new DefaultErrorMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(EntriesPerDayException.class)
    public ResponseEntity<DefaultErrorMessage> handleEntriesPerDayNotAllowed(EntriesPerDayException e) {
        var error = new DefaultErrorMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<DefaultErrorMessage> handleIllegalArgumentException(HttpMessageNotReadableException e) {
        var message = "Type is required and must be CLOCK_IN or CLOCK_OUT.";
        var error = new DefaultErrorMessage(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


}
