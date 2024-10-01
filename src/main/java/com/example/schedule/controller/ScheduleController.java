package com.example.schedule.controller;

import com.example.schedule.controller.dto.ScheduleReadResponse;
import com.example.schedule.controller.dto.ScheduleSaveRequest;
import com.example.schedule.controller.dto.ScheduleSaveResponse;
import com.example.schedule.controller.dto.ScheduleUpdateRequest;
import com.example.schedule.service.ScheduleService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            @RequestParam(value = "startDate", required = false) final LocalDate startDate,
            @RequestParam(value = "endDate", required = false) final LocalDate endDate,
            @RequestParam(value = "name", required = false) final String name
    ) {
        final List<ScheduleReadResponse> responses = scheduleService.findByNameAndUpdatedAtBetween(startDate, endDate,
                name);
        return ResponseEntity.ok(responses);
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
}
