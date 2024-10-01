package com.example.schedule.controller.dto;

import com.example.schedule.domain.Schedule;
import java.time.LocalDate;

public record ScheduleSaveResponse(
        String task,
        String name,
        LocalDate createdAt,
        LocalDate updatedAt
) {
    public static ScheduleSaveResponse from(final Schedule schedule) {
        return new ScheduleSaveResponse(schedule.getTask(), schedule.getName(),
                schedule.getCreatedAt(), schedule.getUpdatedAt());
    }
}
