package com.example.schedule.exception;

public enum ErrorCodes {

    // 비밀 번호 관련
    INCORRECT_PASSWORD("올바른 패스워드가 아닙니다.", 1001L),

    // 스케쥴 관련
    SCHEDULE_NOT_FOUND("일정이 존재하지 않습니다.", 2001L),

    // 작성자 관련
    WRITER_NOT_FOUND("작성자가 존재하지 않습니다.", 3001L),

    BAD_REQUEST("BAD_REQUEST", 9404L),
    BAD_REQUEST_JSON_PARSE_ERROR("[BAD_REQUEST] JSON_PARSE_ERROR - 올바른 JSON 형식이 아님", 9405L),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", 9999L);

    public final String message;
    public final Long code;

    ErrorCodes(String message, Long code) {
        this.message = message;
        this.code = code;
    }
}
