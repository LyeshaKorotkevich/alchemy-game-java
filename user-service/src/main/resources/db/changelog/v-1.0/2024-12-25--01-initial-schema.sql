CREATE TYPE user_role_type AS ENUM ('ADMIN', 'USER');
CREATE TYPE transaction_type AS ENUM ('BUY', 'SELL');
CREATE TYPE mixing_status_type AS ENUM ('SUCCESS', 'FAILURE');

CREATE TABLE users
(
    id       UUID         NOT NULL,
    username VARCHAR(50)  NOT NULL,
    password VARCHAR(50)  NOT NULL,
    coins    INTEGER      NOT NULL,
    role     user_role_type  NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uc_users_username UNIQUE (username)
);

CREATE TABLE transactions
(
    id            UUID                        NOT NULL,
    user_id       UUID                        NOT NULL,
    type          transaction_type            NOT NULL,
    ingredient_id VARCHAR(36)                 NOT NULL,
    quantity      INTEGER                     NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_transactions PRIMARY KEY (id),
    CONSTRAINT fk_transactions_on_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE mixing_history
(
    id         UUID                        NOT NULL,
    user_id    UUID                        NOT NULL,
    status     mixing_status_type          NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_mixing_history PRIMARY KEY (id),
    CONSTRAINT fk_mixing_history_on_user FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE mixing_history_ingredient
(
    mixing_history_id UUID         NOT NULL,
    ingredient_id     VARCHAR(36)  NOT NULL,
    is_lost           BOOLEAN      NOT NULL,
    CONSTRAINT pk_mixing_history_ingredient PRIMARY KEY (mixing_history_id, ingredient_id),
    CONSTRAINT fk_mixing_history_ingredient_on_mixing_history FOREIGN KEY (mixing_history_id) REFERENCES mixing_history (id)
);

CREATE INDEX idx_users_coins ON users (coins);