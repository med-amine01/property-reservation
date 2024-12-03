-- changeset med-amine-chebbi:1733219636252-1
-- preconditions onFail:MARK_RAN onError:MARK_RAN
INSERT INTO users (id, created_at, updated_at, name)
VALUES (1, CURRENT_TIMESTAMP, NULL, 'John Doe');

INSERT INTO users (id, created_at, updated_at, name)
VALUES (2, CURRENT_TIMESTAMP, NULL, 'Jane Smith');
