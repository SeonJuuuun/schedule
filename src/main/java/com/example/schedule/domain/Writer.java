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

    private String email;

    private LocalDate createdAt;

    private LocalDate updatedAt;
}
