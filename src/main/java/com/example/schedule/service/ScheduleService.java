package com.example.schedule.service;

import com.example.schedule.controller.dto.ScheduleReadResponse;
import com.example.schedule.controller.dto.ScheduleSaveRequest;
import com.example.schedule.controller.dto.ScheduleSaveResponse;
import com.example.schedule.domain.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleSaveResponse save(final ScheduleSaveRequest scheduleSaveRequest) {
        final Schedule schedule = Schedule.from(scheduleSaveRequest);
        final Schedule savedSchedule = scheduleRepository.save(schedule);
        return ScheduleSaveResponse.from(savedSchedule);
    }

    public List<ScheduleReadResponse> findByNameAndUpdatedAtBetween(final LocalDate startDate, final LocalDate endDate, final String name) {
        final List<Schedule> schedules = scheduleRepository.findByNameAndUpdatedAtBetween(startDate, endDate, name);
        return ScheduleReadResponse.from(schedules);
    }

    public ScheduleReadResponse findById(final Long scheduleId) {
        final Schedule schedule = scheduleRepository.findById(scheduleId);
        return ScheduleReadResponse.from(schedule);
    }
}
