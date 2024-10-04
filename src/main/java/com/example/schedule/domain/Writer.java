package com.example.schedule.domain;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Writer {

    @Id
    private Long id;

    private String name;

    private String email;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private Writer(final Long id, final String name, final String email, final LocalDate createdAt,
                   final LocalDate updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Writer of(final Long id, final String name, final String email, final LocalDate createdAt,
                            final LocalDate updatedAt) {
        return new Writer(id, name, email, createdAt, updatedAt);
    }

    public static Writer from(final String name, final String email, final LocalDate createdAt,
                              final LocalDate updatedAt) {
        return new Writer(null, name, email, createdAt, updatedAt);
    }

    public static Writer createWriter(final Long id, final String name, final String email) {
        return new Writer(id, name, email, LocalDate.now(), LocalDate.now());
    }
}
