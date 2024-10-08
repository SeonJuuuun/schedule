package com.example.schedule.repository;

import static com.example.schedule.exception.ErrorCodes.WRITER_NOT_FOUND;

import com.example.schedule.domain.Writer;
import com.example.schedule.exception.ScheduleApplicationException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Objects;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class WriterRepository {

    private final JdbcTemplate jdbcTemplate;

    public WriterRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Writer findByName(final String name) {
        final String sql = "SELECT * FROM WRITER WHERE name = ?";

        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                    Writer.of(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getDate("created_at").toLocalDate(),
                            rs.getDate("updated_at").toLocalDate()
                    ), name);
        } catch (EmptyResultDataAccessException e) {
            throw new ScheduleApplicationException(WRITER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
    }

    public Writer save(final Writer writer) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        final String sql = "INSERT INTO WRITER (name, email, created_at, updated_at) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, writer.getName());
            ps.setString(2, writer.getEmail());
            ps.setDate(3, Date.valueOf(writer.getCreatedAt()));
            ps.setDate(4, Date.valueOf(writer.getUpdatedAt()));
            return ps;
        }, keyHolder);

        final Long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return Writer.createWriter(id, writer.getName(), writer.getEmail());
    }
}
