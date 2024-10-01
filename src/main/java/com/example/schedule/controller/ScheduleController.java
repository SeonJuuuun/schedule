package com.example.schedule.controller;

import com.example.schedule.controller.dto.ScheduleReadResponse;
import com.example.schedule.controller.dto.ScheduleSaveRequest;
import com.example.schedule.controller.dto.ScheduleSaveResponse;
import com.example.schedule.service.ScheduleService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
        final List<ScheduleReadResponse> responses = scheduleService.findById(startDate, endDate, name);
        return ResponseEntity.ok(responses);
    }
}
