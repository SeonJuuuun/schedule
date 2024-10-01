package com.example.schedule.controller.dto;

public record ScheduleUpdateRequest (
        String task,
        String name,
        String password
){
}
