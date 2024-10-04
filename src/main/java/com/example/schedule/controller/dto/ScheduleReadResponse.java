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
    // Schedule을 파라미터로 받아 응답 객체로 바꿔주는 메서드
    public static ScheduleReadResponse from(final Schedule schedule) {
        return new ScheduleReadResponse(schedule.getTask(), schedule.getWriter().getName(), schedule.getCreatedAt(),
                schedule.getUpdatedAt());
    }

    // 전체 조회를 위한 응답을 리스트로 반환할 때 사용하는 메서드
    public static List<ScheduleReadResponse> from(final List<Schedule> schedules) {
        return schedules.stream()
                .map(ScheduleReadResponse::from)
                .toList();
    }
}
