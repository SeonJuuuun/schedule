package com.example.schedule.service;

import com.example.schedule.controller.dto.ScheduleSaveRequest;
import com.example.schedule.controller.dto.ScheduleSaveResponse;
import com.example.schedule.domain.Schedule;
import com.example.schedule.repository.ScheduleRepository;
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
}
