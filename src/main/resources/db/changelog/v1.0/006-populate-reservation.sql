-- changeset med-amine-chebbi:1733219757838-1
-- preconditions onFail:MARK_RAN onError:MARK_RAN
INSERT INTO reservations (created_at, updated_at, user_id, property_id, start_date, end_date, money_spent, discount,
                          tax)
VALUES (CURRENT_TIMESTAMP, NULL, 1, 1, '2024-12-10', '2024-12-15', 500.00, 50.00, 25.00);

INSERT INTO reservations (created_at, updated_at, user_id, property_id, start_date, end_date, money_spent, discount,
                          tax)
VALUES (CURRENT_TIMESTAMP, NULL, 1, 3, '2024-12-18', '2024-12-22', 400.00, 40.00, 20.00);

INSERT INTO reservations (created_at, updated_at, user_id, property_id, start_date, end_date, money_spent, discount,
                          tax)
VALUES (CURRENT_TIMESTAMP, NULL, 1, 1, '2024-12-12', '2024-12-20', 800.00, 80.00, 40.00);

INSERT INTO reservations (created_at, updated_at, user_id, property_id, start_date, end_date, money_spent, discount,
                          tax)
VALUES (CURRENT_TIMESTAMP, NULL, 2, 2, '2024-12-12', '2024-12-20', 800.00, 80.00, 40.00);
