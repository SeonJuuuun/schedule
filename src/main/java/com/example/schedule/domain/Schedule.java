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

    private String name;

    private String password;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private Schedule(final Long id, final String task, final String name, final String password,
                    final LocalDate createdAt, final LocalDate updatedAt) {
        this.id = id;
        this.task = task;
        this.name = name;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Schedule createSchedule(final Long id, final String task, final String name, final String password) {
        return new Schedule(id, task, name, password, LocalDate.now(), LocalDate.now());
    }

    public static Schedule from(final ScheduleSaveRequest scheduleSaveRequest) {
        return new Schedule(null, scheduleSaveRequest.task(), scheduleSaveRequest.name(),
                scheduleSaveRequest.password(), LocalDate.now(), LocalDate.now());
    }

    public static Schedule findSchedule(final Long id, final String task, final String name,
                                                final String password, final LocalDate createdAt, final LocalDate updatedAt) {
        return new Schedule(id, task, name, password, createdAt, updatedAt);
    }
}
