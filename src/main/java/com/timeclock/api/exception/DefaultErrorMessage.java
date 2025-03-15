package com.timeclock.api.exception;

import lombok.Getter;

@Getter
public class DefaultErrorMessage {
    public String message;

    public DefaultErrorMessage(String message) {
        this.message = message;
    }

}
