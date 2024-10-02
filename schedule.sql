DROP TABLE IF EXISTS SCHEDULE;

CREATE TABLE SCHEDULE
(
    id         BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    task       VARCHAR(20) NOT NULL,
    name       VARCHAR(20) NOT NULL,
    password   VARCHAR(20) NOT NULL,
    writer_id  BIGINT,
    created_at timestamp,
    updated_at timestamp,
    FOREIGN KEY (writer_id) REFERENCES WRITER (id)
);

CREATE TABLE WRITER
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255),
    email      VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
