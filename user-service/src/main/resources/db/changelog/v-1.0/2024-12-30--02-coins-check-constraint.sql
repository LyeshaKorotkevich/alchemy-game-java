ALTER TABLE users
    ADD CONSTRAINT check_coins_nonnegative CHECK (coins >= 0);
