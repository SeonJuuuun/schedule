package com.example.schedule.exception;

public class IncorrectPasswordException extends ScheduleException {
    public IncorrectPasswordException() {
        super("비밀 번호가 틀렸습니다.");
    }
}
