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

    public Schedule(final Long id, final String task, final String name, final String password) {
        this.id = id;
        this.task = task;
        this.name = name;
        this.password = password;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    public Schedule(final String task, final String name, final String password) {
        this.task = task;
        this.name = name;
        this.password = password;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    public static Schedule from(final ScheduleSaveRequest scheduleSaveRequest) {
        return new Schedule(scheduleSaveRequest.task(), scheduleSaveRequest.name(), scheduleSaveRequest.password());
    }
}
