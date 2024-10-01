package com.example.schedule.repository;

import com.example.schedule.domain.Schedule;
import java.sql.Date;
import java.sql.PreparedStatement;
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
        return new Schedule(id, schedule.getTask(), schedule.getName(), schedule.getPassword());
    }
}
