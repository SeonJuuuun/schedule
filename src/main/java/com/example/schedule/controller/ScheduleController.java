package com.example.schedule.controller;

import com.example.schedule.controller.dto.ScheduleDeleteRequest;
import com.example.schedule.controller.dto.ScheduleReadResponse;
import com.example.schedule.controller.dto.ScheduleSaveRequest;
import com.example.schedule.controller.dto.ScheduleSaveResponse;
import com.example.schedule.controller.dto.ScheduleUpdateRequest;
import com.example.schedule.service.ScheduleService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedule")
    public ResponseEntity<ScheduleSaveResponse> save(@RequestBody final ScheduleSaveRequest scheduleSaveRequest) {
        final ScheduleSaveResponse response = scheduleService.save(scheduleSaveRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/read/schedules")
    public ResponseEntity<List<ScheduleReadResponse>> readSchedules(
            @RequestParam(value = "startDate", required = false) final LocalDate startDate, // 수정일 검색 시작 날짜
            @RequestParam(value = "endDate", required = false) final LocalDate endDate, // 수정일 검색 끝 날짜
            @RequestParam(value = "name", required = false) final String name, // 작성자 명 검색 키워드
            @RequestParam(defaultValue = "0", value = "page") int page,
            @RequestParam(defaultValue = "10", value = "size") int size
    ) {
        final Page<ScheduleReadResponse> responses = scheduleService.findByNameAndUpdatedAtBetween(startDate, endDate,
                name, PageRequest.of(page, size));
        return ResponseEntity.ok(responses.getContent());
    }

    @GetMapping("/read/{scheduleId}")
    public ResponseEntity<ScheduleReadResponse> readSchedule(@PathVariable(name = "scheduleId") final Long scheduleId) {
        final ScheduleReadResponse response = scheduleService.findById(scheduleId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/schedule/{scheduleId}")
    public ResponseEntity<Void> updateSchedule(
            @RequestBody final ScheduleUpdateRequest scheduleUpdateRequest,
            @PathVariable(name = "scheduleId") final Long scheduleId) {
        scheduleService.updateSchedule(scheduleUpdateRequest, scheduleId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/schedule/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @RequestBody final ScheduleDeleteRequest scheduleDeleteRequest,
            @PathVariable(name = "scheduleId") final Long scheduleId
    ) {
        scheduleService.deleteSchedule(scheduleDeleteRequest, scheduleId);
        return ResponseEntity.noContent().build();
    }
}
