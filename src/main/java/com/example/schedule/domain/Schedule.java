package com.example.schedule.domain;

import com.example.schedule.controller.dto.ScheduleSaveRequest;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {

    @Id
    private Long id;

    private String task;

    private Writer writer;

    private String password;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private Schedule(final Long id, final String task, final Writer writer, final String password,
                     final LocalDate createdAt, final LocalDate updatedAt) {
        this.id = id;
        this.task = task;
        this.writer = writer;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Schedule createSchedule(final Long id, final String task, final Writer writer,
                                          final String password) {
        return new Schedule(id, task, writer, password, LocalDate.now(), LocalDate.now());
    }

    public static Schedule of(final ScheduleSaveRequest scheduleSaveRequest, final Writer writer) {
        return new Schedule(null, scheduleSaveRequest.task(), writer,
                scheduleSaveRequest.password(), LocalDate.now(), LocalDate.now());
    }

    public static Schedule of(final Long id, final String task, final Writer writer, final LocalDate createdAt,
                              final LocalDate updatedAt) {
        return new Schedule(id, task, writer, null, createdAt, updatedAt);
    }
}
