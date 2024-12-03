-- liquibase formatted sql

-- changeset med-amine-chebbi:1733051554796-1
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- precondition-sql-check expectedResult:0 select count(*) from information_schema.tables where table_name = 'properties';

CREATE TABLE properties
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    created_at    TIMESTAMP             NOT NULL,
    updated_at    TIMESTAMP             NULL,
    building_name VARCHAR(255)          NOT NULL,
    property_type VARCHAR(255)          NOT NULL,
    city          VARCHAR(255)          NOT NULL,
    country       VARCHAR(3)            NOT NULL,
    address       VARCHAR(255)          NOT NULL,
    price_per_day DECIMAL(10, 2)        NOT NULL,
    availability  BOOLEAN               NOT NULL DEFAULT TRUE,
    CONSTRAINT pk_properties PRIMARY KEY (id)
);

-- Add index for case-insensitive search on buildingName
CREATE INDEX idx_building_name_lower ON properties (building_name);

-- Add index for case-insensitive search on city
CREATE INDEX idx_city_lower ON properties (city);

-- Add index for case-insensitive search on address
CREATE INDEX idx_address_lower ON properties (address);

-- Add index for case-insensitive search on country
CREATE INDEX idx_country_lower ON properties (country);

-- Add index for range queries on price_per_day
CREATE INDEX idx_price_per_day ON properties (price_per_day);
