-- liquibase formatted sql

-- changeset med-amine-chebbi:1733051554796
-- Insert multiple properties into the properties table
INSERT INTO properties(id, created_at, updated_at, building_name, property_type, city, country, address, price_per_day,
                       availability)
VALUES (1, CURRENT_TIMESTAMP, NULL, 'Hotel Paris', 'HOTEL_ROOM', 'Paris', 'FRA', 'Champs Elysees', 300.00, true),
       (2, CURRENT_TIMESTAMP, NULL, 'Beach Resort', 'HOTEL_ROOM', 'Miami', 'USA', 'Ocean Drive', 500.00, true),
       (3, CURRENT_TIMESTAMP, NULL, 'Mountain Lodge', 'FLAT', 'Aspen', 'USA', 'Rocky Mountain Road', 250.00, false),
       (4, CURRENT_TIMESTAMP, NULL, 'Urban Suites', 'FLAT', 'New York', 'USA', '5th Avenue', 400.00, true),
       (5, CURRENT_TIMESTAMP, NULL, 'Countryside Cottage', 'FLAT', 'Oxford', 'GBR', 'Green Hill', 150.00, true),
       (6, CURRENT_TIMESTAMP, NULL, 'Desert Villa', 'FLAT', 'Dubai', 'UAE', 'Palm Jumeirah', 1200.00, true),
       (7, CURRENT_TIMESTAMP, NULL, 'City Hostel', 'HOTEL_ROOM', 'Berlin', 'DEU', 'Alexanderplatz', 50.00, true),
       (8, CURRENT_TIMESTAMP, NULL, 'Boutique Inn', 'HOTEL_ROOM', 'Rome', 'ITA', 'Via Condotti', 200.00, true),
       (9, CURRENT_TIMESTAMP, NULL, 'Lakeview BnB', 'FLAT', 'Zurich', 'CHE', 'Lake Zurich Street', 180.00, false),
       (10, CURRENT_TIMESTAMP, NULL, 'Ski Resort', 'HOTEL_ROOM', 'Chamonix', 'FRA', 'Mont Blanc', 450.00, true);
