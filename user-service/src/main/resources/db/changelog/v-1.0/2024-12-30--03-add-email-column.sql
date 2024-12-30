ALTER TABLE users
    ADD COLUMN email VARCHAR(100) NOT NULL;

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);