package com.example.schedule.service;

import static com.example.schedule.exception.ErrorCodes.INCORRECT_PASSWORD;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.example.schedule.controller.dto.ScheduleDeleteRequest;
import com.example.schedule.controller.dto.ScheduleReadResponse;
import com.example.schedule.controller.dto.ScheduleSaveRequest;
import com.example.schedule.controller.dto.ScheduleSaveResponse;
import com.example.schedule.controller.dto.ScheduleUpdateRequest;
import com.example.schedule.domain.Schedule;
import com.example.schedule.domain.Writer;
import com.example.schedule.exception.ScheduleApplicationException;
import com.example.schedule.repository.ScheduleRepository;
import com.example.schedule.repository.WriterRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final WriterRepository writerRepository;

    public ScheduleSaveResponse save(final ScheduleSaveRequest scheduleSaveRequest) {
        final Writer writer = writerRepository.findByName(scheduleSaveRequest.name()); // 요청에 있는 이름을 기준으로 작성자명 검색
        final Schedule schedule = Schedule.of(scheduleSaveRequest, writer); // Schedule 객체로 변환
        final Schedule savedSchedule = scheduleRepository.save(schedule);
        return ScheduleSaveResponse.from(savedSchedule);
    }

    public Page<ScheduleReadResponse> findByNameAndUpdatedAtBetween(
            final LocalDate startDate,
            final LocalDate endDate,
            final String name,
            final Pageable pageable
    ) {
        final Writer writer = writerRepository.findByName(name);
        final List<Schedule> schedules = scheduleRepository.findByNameAndUpdatedAtBetween(startDate, endDate, writer,
                pageable); // 작성자명과 수정일을 기준으로 검색

        return new PageImpl<>(ScheduleReadResponse.from(schedules)); //page 객체로 변환
    }

    public ScheduleReadResponse findById(final Long scheduleId) {
        final Schedule schedule = scheduleRepository.findById(scheduleId);
        return ScheduleReadResponse.from(schedule);
    }

    public void updateSchedule(final ScheduleUpdateRequest scheduleUpdateRequest, final Long scheduleId) {
        final Schedule schedule = scheduleRepository.findById(scheduleId);
        // 패스워드가 올바른지 확인하는 로직
        if (!schedule.getPassword().equals(scheduleUpdateRequest.password())) {
            throw new ScheduleApplicationException(INCORRECT_PASSWORD, UNAUTHORIZED);
        }
        scheduleRepository.updateSchedule(scheduleUpdateRequest.task(), scheduleUpdateRequest.name(), scheduleId);
    }

    public void deleteSchedule(final ScheduleDeleteRequest scheduleDeleteRequest, final Long scheduleId) {
        final Schedule schedule = scheduleRepository.findById(scheduleId);
        if (!schedule.getPassword().equals(scheduleDeleteRequest.password())) {
            throw new ScheduleApplicationException(INCORRECT_PASSWORD, UNAUTHORIZED);
        }
        scheduleRepository.deleteById(scheduleId);
    }
}
