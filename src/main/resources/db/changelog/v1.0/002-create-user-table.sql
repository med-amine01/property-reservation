-- liquibase formatted sql

-- changeset med-amine-chebbi:1733219636252-1
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 select count(*) from information_schema.tables where table_name = 'users';
CREATE TABLE users
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at TIMESTAMP             NOT NULL,
    updated_at TIMESTAMP,
    name       VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

