ALTER TABLE `role`
    ADD updated_at datetime NULL;

ALTER TABLE token
    ADD updated_at datetime NULL;

ALTER TABLE user
    ADD updated_at datetime NULL;

ALTER TABLE `role`
DROP
COLUMN update_at;

ALTER TABLE token
DROP
COLUMN update_at;

ALTER TABLE user
DROP
COLUMN update_at;