package com.example.schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
class ScheduleServiceTest {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private WriterRepository writerRepository;

    private Writer writer;

    @BeforeEach
    void setUp() {
        writer = writerRepository.save(Writer.from("테스트 작성자 1", "테스트 이메일1", LocalDate.now(), LocalDate.now()));
    }

    @Test
    @DisplayName("schedule을 저장한다.")
    void save() {
        //given
        final String task = "할 일 저장";
        final String name = "테스트 작성자 1";
        final String password = "pw11";

        final ScheduleSaveRequest request = new ScheduleSaveRequest(task, name, password);

        //when
        final ScheduleSaveResponse response = scheduleService.save(request);

        //then
        assertThat(response.task()).isEqualTo(task);
        assertThat(response.name()).isEqualTo(name);
        assertThat(response.createdAt()).isEqualTo(LocalDate.now());
        assertThat(response.updatedAt()).isEqualTo(LocalDate.now());
    }

    @Test
    @DisplayName("페이지 별로 수정일 조건과 작성자명을 기준으로 조회하고, 수정일을 기준으로 내림차순 정렬한다.")
    void findByNameAndUpdatedAtBetween() {
        //given
        final String task = "테스트 할 일1";
        final String password = "pw11";
        final Long scheduleId = 1L;

        final String task2 = "테스트 할 일2";
        final String password2 = "pw11";
        final Long scheduleId2 = 2L;

        final Schedule schedule = scheduleRepository.save(Schedule.createSchedule(scheduleId, task, writer, password));
        final Schedule schedule2 = scheduleRepository.save(
                Schedule.createSchedule(scheduleId2, task2, writer, password2));

        final LocalDate startDate = LocalDate.now().minusDays(1);
        final LocalDate endDate = LocalDate.now().plusDays(1);
        final String writer = "테스트 작성자 1";
        final Pageable pageable = PageRequest.of(1, 1);

        //when
        final Page<ScheduleReadResponse> responsePage = scheduleService.findByNameAndUpdatedAtBetween(startDate,
                endDate,
                writer, pageable);

        //then
        assertThat(responsePage.getContent().get(0).task()).isEqualTo(schedule2.getTask());
        assertThat(responsePage.getContent().get(0).name()).isEqualTo(schedule2.getWriter().getName());
    }

    @Test
    @DisplayName("ID를 기준으로 스케쥴을 하나 조회한다.")
    void findById() {
        //given
        final String task = "테스트 할 일1";
        final String password = "pw11";
        final Long scheduleId = 1L;

        final Schedule schedule = scheduleRepository.save(Schedule.createSchedule(scheduleId, task, writer, password));

        //when
        final ScheduleReadResponse response = scheduleService.findById(scheduleId);

        //then
        assertThat(response.task()).isEqualTo(schedule.getTask());
        assertThat(response.name()).isEqualTo(schedule.getWriter().getName());
        assertThat(response.createdAt()).isEqualTo(schedule.getCreatedAt());
        assertThat(response.updatedAt()).isEqualTo(schedule.getUpdatedAt());
    }

    @Test
    @DisplayName("일정 수정 [비밀번호가 맞는 경우] - 일정을 수정한다.")
    void updateSchedule_correct_password() {
        //given
        final String task = "테스트 할 일1";
        final String password = "pw11";
        final Long scheduleId = 1L;

        scheduleRepository.save(Schedule.createSchedule(scheduleId, task, writer, password));

        final String updatedTask = "할 일 바꾸기";
        final String updatedName = "작성자 바꾸기";

        final ScheduleUpdateRequest request = new ScheduleUpdateRequest(updatedTask, updatedName, password);

        //when
        scheduleService.updateSchedule(request, scheduleId);

        final ScheduleReadResponse updatedSchedule = scheduleService.findById(scheduleId);

        //then
        assertThat(updatedSchedule.task()).isEqualTo(updatedTask);
        assertThat(updatedSchedule.name()).isEqualTo(updatedName);
    }

    @Test
    @DisplayName("일정 수정 [비밀번호가 틀린 경우] - 예외가 발생한다.")
    void updateSchedule_incorrect_password() {
        //given
        final String task = "테스트 할 일1";
        final String password = "pw11";
        final Long scheduleId = 1L;

        scheduleRepository.save(Schedule.createSchedule(scheduleId, task, writer, password));

        final String updatedTask = "할 일 바꾸기";
        final String updatedName = "작성자 바꾸기";
        final String inCorrectPassword = "틀린 비밀번호";

        final ScheduleUpdateRequest request = new ScheduleUpdateRequest(updatedTask, updatedName, inCorrectPassword);

        //when & then
        assertThatThrownBy(() -> scheduleService.updateSchedule(request, scheduleId)).isInstanceOf(
                ScheduleApplicationException.class);
    }

    @Test
    @DisplayName("일정 삭제 [비밀 번호가 틀린 경우] - 예외가 발생한다.")
    void deleteSchedule_incorrect_password() {
        //given
        final String task = "테스트 할 일1";
        final String password = "pw11";
        final Long scheduleId = 1L;

        final String inCorrectPassword = "틀린 비밀번호";

        scheduleRepository.save(Schedule.createSchedule(scheduleId, task, writer, password));

        final ScheduleDeleteRequest request = new ScheduleDeleteRequest(inCorrectPassword);

        //when & then
        assertThatThrownBy(() -> scheduleService.deleteSchedule(request, scheduleId)).isInstanceOf(
                ScheduleApplicationException.class);
    }
}
