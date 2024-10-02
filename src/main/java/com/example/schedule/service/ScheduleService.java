package com.example.schedule.service;

import com.example.schedule.controller.dto.ScheduleDeleteRequest;
import com.example.schedule.controller.dto.ScheduleReadResponse;
import com.example.schedule.controller.dto.ScheduleSaveRequest;
import com.example.schedule.controller.dto.ScheduleSaveResponse;
import com.example.schedule.controller.dto.ScheduleUpdateRequest;
import com.example.schedule.domain.Schedule;
import com.example.schedule.domain.Writer;
import com.example.schedule.exception.IncorrectPasswordException;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.WriterRepository;
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
    private final WriterRepository writerRepository;

    public ScheduleSaveResponse save(final ScheduleSaveRequest scheduleSaveRequest) {
        final Writer writer = writerRepository.findByName(scheduleSaveRequest.name());
        final Schedule schedule = Schedule.of(scheduleSaveRequest, writer);
        final Schedule savedSchedule = scheduleRepository.save(schedule);
        return ScheduleSaveResponse.from(savedSchedule);
    }

    public List<ScheduleReadResponse> findByNameAndUpdatedAtBetween(final LocalDate startDate, final LocalDate endDate,
                                                                    final String name) {
        final Writer writer = writerRepository.findByName(name);
        final List<Schedule> schedules = scheduleRepository.findByNameAndUpdatedAtBetween(startDate, endDate, writer);
        return ScheduleReadResponse.from(schedules);
    }

    public ScheduleReadResponse findById(final Long scheduleId) {
        final Schedule schedule = scheduleRepository.findById(scheduleId);
        return ScheduleReadResponse.from(schedule);
    }

    public void updateSchedule(final ScheduleUpdateRequest scheduleUpdateRequest, final Long scheduleId) {
        final Schedule schedule = scheduleRepository.findById(scheduleId);
        if (!schedule.getPassword().equals(scheduleUpdateRequest.password())) {
            throw new IncorrectPasswordException();
        }
        scheduleRepository.updateSchedule(scheduleUpdateRequest.task(), scheduleUpdateRequest.name(), scheduleId);
    }

    public void deleteSchedule(final ScheduleDeleteRequest scheduleDeleteRequest, final Long scheduleId) {
        final Schedule schedule = scheduleRepository.findById(scheduleId);
        if (!schedule.getPassword().equals(scheduleDeleteRequest.password())) {
            throw new IncorrectPasswordException();
        }
        scheduleRepository.deleteById(scheduleId);
    }
}
