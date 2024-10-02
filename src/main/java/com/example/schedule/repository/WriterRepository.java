package com.example.schedule.repository;

import com.example.schedule.domain.Writer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WriterRepository {

    private final JdbcTemplate jdbcTemplate;

    public WriterRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Writer findByName(final String name) {
        final String sql = "SELECT * FROM WRITER WHERE name = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                Writer.of(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getDate("created_at").toLocalDate(),
                        rs.getDate("updated_at").toLocalDate()
                ), name);
    }
}
