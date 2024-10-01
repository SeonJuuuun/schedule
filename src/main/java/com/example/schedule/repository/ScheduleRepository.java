package com.example.schedule.repository;

import com.example.schedule.domain.Schedule;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Schedule save(final Schedule schedule) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "INSERT INTO SCHEDULE (task, name, password, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, schedule.getTask());
            ps.setString(2, schedule.getName());
            ps.setString(3, schedule.getPassword());
            ps.setDate(4, Date.valueOf(schedule.getCreatedAt()));
            ps.setDate(5, Date.valueOf(schedule.getUpdatedAt()));
            return ps;
        }, keyHolder);
        final Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return Schedule.createSchedule(id, schedule.getTask(), schedule.getName(), schedule.getPassword());
    }

    public List<Schedule> findByNameAndUpdatedAtBetween(
            final LocalDate startDate,
            final LocalDate endDate,
            final String name
    ) {
        final String sql = "select * from SCHEDULE WHERE updated_at BETWEEN ? AND ? OR name = ? ORDER BY updated_at DESC";
        return jdbcTemplate.query(sql, (PreparedStatement ps) -> {
                    ps.setDate(1, java.sql.Date.valueOf(startDate));
                    ps.setDate(2, java.sql.Date.valueOf(endDate));
                    ps.setString(3, name);
                }, (rs, rowNum) ->
                        new Schedule(
                                rs.getLong("id"),
                                rs.getString("task"),
                                rs.getString("name"),
                                rs.getString("password"),
                                rs.getDate("created_at").toLocalDate(),
                                rs.getDate("updated_at").toLocalDate()
                        )
        );
    }
}
