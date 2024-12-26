-- liquibase formatted sql

-- changeset leha:1735135662532-1
CREATE TABLE ingredient_mixing_history
(
    mixing_history_id UUID         NOT NULL,
    ingredient_id     VARCHAR(255) NOT NULL,
    is_lost           BOOLEAN,
    CONSTRAINT pk_ingredient_mixing_history PRIMARY KEY (mixing_history_id, ingredient_id)
);

-- changeset leha:1735135662532-2
CREATE TABLE mixing_history
(
    id         UUID                        NOT NULL,
    user_id    UUID                        NOT NULL,
    status     VARCHAR(255)                NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_mixing_history PRIMARY KEY (id)
);

-- changeset leha:1735135662532-3
CREATE TABLE mixing_history_ingredients
(
    mixing_history_id             UUID         NOT NULL,
    ingredients_ingredient_id     VARCHAR(255) NOT NULL,
    ingredients_mixing_history_id UUID         NOT NULL
);

-- changeset leha:1735135662532-4
CREATE TABLE transactions
(
    id            UUID                        NOT NULL,
    user_id       UUID                        NOT NULL,
    type          VARCHAR(255)                NOT NULL,
    ingredient_id VARCHAR(255)                NOT NULL,
    quantity      INTEGER                     NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_transactions PRIMARY KEY (id)
);

-- changeset leha:1735135662532-5
CREATE TABLE users
(
    id       UUID         NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    coins    INTEGER      NOT NULL,
    role     VARCHAR(255) NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

-- changeset leha:1735135662532-6
ALTER TABLE mixing_history_ingredients
    ADD CONSTRAINT uc_mixing_history_ingredients_ininidinmihiid UNIQUE (ingredients_ingredient_id, ingredients_mixing_history_id);

-- changeset leha:1735135662532-7
ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);

-- changeset leha:1735135662532-8
CREATE INDEX coins_index ON users (coins);

-- changeset leha:1735135662532-9
ALTER TABLE ingredient_mixing_history
    ADD CONSTRAINT FK_INGREDIENT_MIXING_HISTORY_ON_MIXING_HISTORY FOREIGN KEY (mixing_history_id) REFERENCES mixing_history (id);

-- changeset leha:1735135662532-10
ALTER TABLE mixing_history
    ADD CONSTRAINT FK_MIXING_HISTORY_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

-- changeset leha:1735135662532-11
ALTER TABLE transactions
    ADD CONSTRAINT FK_TRANSACTIONS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

-- changeset leha:1735135662532-12
ALTER TABLE mixing_history_ingredients
    ADD CONSTRAINT fk_mixhising_on_ingredient_mixing_history FOREIGN KEY (ingredients_mixing_history_id, ingredients_ingredient_id) REFERENCES ingredient_mixing_history (mixing_history_id, ingredient_id);

-- changeset leha:1735135662532-13
ALTER TABLE mixing_history_ingredients
    ADD CONSTRAINT fk_mixhising_on_mixing_history FOREIGN KEY (mixing_history_id) REFERENCES mixing_history (id);

