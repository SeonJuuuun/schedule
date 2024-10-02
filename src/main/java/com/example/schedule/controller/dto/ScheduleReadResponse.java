package com.example.schedule.controller.dto;

import com.example.schedule.domain.Schedule;
import java.time.LocalDate;
import java.util.List;

public record ScheduleReadResponse(
        String task,
        String name,
        LocalDate createdAt,
        LocalDate updatedAt
) {
    public static ScheduleReadResponse from(final Schedule schedule) {
        return new ScheduleReadResponse(schedule.getTask(), schedule.getWriter().getName(), schedule.getCreatedAt(),
                schedule.getUpdatedAt());
    }

    public static List<ScheduleReadResponse> from(final List<Schedule> schedules) {
        return schedules.stream()
                .map(ScheduleReadResponse::from)
                .toList();
    }
}
