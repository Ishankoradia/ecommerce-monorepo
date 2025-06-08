CREATE TABLE `role`
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    created_at     datetime NULL,
    last_upated_at datetime NULL,
    created_by     VARCHAR(255) NULL,
    state          SMALLINT NULL,
    name           VARCHAR(255) NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE user
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    created_at     datetime NULL,
    last_upated_at datetime NULL,
    created_by     VARCHAR(255) NULL,
    state          SMALLINT NULL,
    name           VARCHAR(255) NULL,
    email          VARCHAR(255) NULL,
    password       VARCHAR(255) NULL,
    phone_number   VARCHAR(255) NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL
);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (role_id) REFERENCES `role` (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES user (id);