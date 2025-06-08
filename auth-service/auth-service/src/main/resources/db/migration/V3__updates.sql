ALTER TABLE `role`
    ADD update_at datetime NULL;

ALTER TABLE token
    ADD update_at datetime NULL;

ALTER TABLE user
    ADD update_at datetime NULL;

ALTER TABLE `role`
DROP
COLUMN created_by;

ALTER TABLE `role`
DROP
COLUMN last_upated_at;

ALTER TABLE token
DROP
COLUMN created_by;

ALTER TABLE token
DROP
COLUMN last_upated_at;

ALTER TABLE user
DROP
COLUMN created_by;

ALTER TABLE user
DROP
COLUMN last_upated_at;