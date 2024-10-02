package com.example.schedule.repository;

import com.example.schedule.domain.Schedule;
import com.example.schedule.domain.Writer;
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
        final String sql = "INSERT INTO SCHEDULE (task, writer_id, password, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, schedule.getTask());
            ps.setLong(2, schedule.getWriter().getId());
            ps.setString(3, schedule.getPassword());
            ps.setDate(4, Date.valueOf(schedule.getCreatedAt()));
            ps.setDate(5, Date.valueOf(schedule.getUpdatedAt()));
            return ps;
        }, keyHolder);

        final Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return Schedule.createSchedule(id, schedule.getTask(), schedule.getWriter(), schedule.getPassword());
    }

    public List<Schedule> findByNameAndUpdatedAtBetween(
            final LocalDate startDate,
            final LocalDate endDate,
            final Writer writer
    ) {
        final String sql =
                "SELECT s.* FROM SCHEDULE s " +
                        "JOIN WRITER w ON s.writer_id = w.id " +
                        "WHERE s.updated_at BETWEEN ? AND ? OR w.name = ? " +
                        "ORDER BY s.updated_at DESC";

        return jdbcTemplate.query(sql, ps -> {
                    ps.setDate(1, java.sql.Date.valueOf(startDate));
                    ps.setDate(2, java.sql.Date.valueOf(endDate));
                    ps.setLong(3, writer.getId());
                }, (rs, rowNum) ->
                        Schedule.of(
                                rs.getLong("id"),
                                rs.getString("task"),
                                writer,
                                rs.getDate("created_at").toLocalDate(),
                                rs.getDate("updated_at").toLocalDate()
                        )
        );
    }

    public Schedule findById(final Long scheduleId) {
        final String sql =
                "SELECT s.*, w.name FROM SCHEDULE s " +
                        "JOIN WRITER w ON s.writer_id = w.id " +
                        "WHERE s.id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                Schedule.of(
                        rs.getLong("id"),
                        rs.getString("task"),
                        Writer.from(rs.getString("w.name")),
                        rs.getDate("created_at").toLocalDate(),
                        rs.getDate("updated_at").toLocalDate()
                ), scheduleId);
    }

    public int updateSchedule(final String task, final String name, final Long id) {
        final String sql = "UPDATE SCHEDULE SET task = ?, name = ?, updated_at = ? WHERE id = ?";

        return jdbcTemplate.update(sql, ps -> {
            ps.setString(1, task);
            ps.setString(2, name);
            ps.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            ps.setLong(4, id);
        });
    }

    public int deleteById(final Long scheduleId) {
        final String sql = "DELETE FROM SCHEDULE WHERE id = ?";
        return jdbcTemplate.update(sql, scheduleId);
    }
}
