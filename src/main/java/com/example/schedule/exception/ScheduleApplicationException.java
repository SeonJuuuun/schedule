package com.example.schedule.exception;

import org.springframework.http.HttpStatus;

public class ScheduleApplicationException extends RuntimeException {

    private final ErrorCodes errorCodes;
    private final HttpStatus status;

    public ScheduleApplicationException(ErrorCodes errorCodes, HttpStatus status) {
        this.errorCodes = errorCodes;
        this.status = status;
    }

    public ErrorCodes getErrorCodes() {
        return errorCodes;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
