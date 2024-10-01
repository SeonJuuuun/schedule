package com.example.schedule.controller.dto;

public record ScheduleSaveRequest(
        String task,
        String name,
        String password
) {
}
