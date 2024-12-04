-- liquibase formatted sql

-- changeset med-amine-chebbi:1733219757838-1
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 select count(*) from information_schema.tables where table_name = 'reservations';
CREATE TABLE reservations
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    created_at  TIMESTAMP                         NOT NULL,
    updated_at  TIMESTAMP,
    user_id     BIGINT                            NOT NULL,
    property_id BIGINT                            NOT NULL,
    start_date  TIMESTAMP                         NOT NULL,
    end_date    TIMESTAMP                         NOT NULL,
    money_spent DECIMAL(10, 2)                    NOT NULL,
    discount    DECIMAL(10, 2)                    NOT NULL,
    tax         DECIMAL(10, 2)                    NOT NULL
);


-- changeset med-amine-chebbi:1733219757838-2
ALTER TABLE reservations
    ADD CONSTRAINT FK_RESERVATIONS_ON_PROPERTY FOREIGN KEY (property_id) REFERENCES properties (id);

-- changeset med-amine-chebbi:1733219757838-3
ALTER TABLE reservations
    ADD CONSTRAINT FK_RESERVATIONS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);


-- changeset med-amine-chebbi:1733219757838-4
-- Add index for case-insensitive search on user
CREATE INDEX idx_user_id ON reservations (user_id);

-- Add index for case-insensitive search on property
CREATE INDEX idx_property_id ON reservations (property_id);

-- Add index for case-insensitive search on start_date
CREATE INDEX idx_start_date ON reservations (start_date);

-- Add index for case-insensitive search on end_date
CREATE INDEX idx_end_date ON reservations (end_date);
