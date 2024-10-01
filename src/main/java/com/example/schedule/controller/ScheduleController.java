package com.example.schedule.controller;

import com.example.schedule.controller.dto.ScheduleSaveRequest;
import com.example.schedule.controller.dto.ScheduleSaveResponse;
import com.example.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
}
