DROP TABLE IF EXISTS SCHEDULE;

CREATE TABLE SCHEDULE
(
    id      BIGINT      NOT NULL AUTO_INCREMENT,
    task    VARCHAR(20) NOT NULL,
    name    VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    created_at timestamp,
    updated_at timestamp,
    PRIMARY KEY (id)
);
