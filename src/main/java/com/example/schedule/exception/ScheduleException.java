package com.example.schedule.exception;

public class ScheduleException extends RuntimeException {
    public ScheduleException(final String message) {
        super("[ERROR] " + message);
    }
}
