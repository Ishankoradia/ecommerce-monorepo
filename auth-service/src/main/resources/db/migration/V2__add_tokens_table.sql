CREATE TABLE token
(
    id             BIGINT AUTO_INCREMENT NOT NULL,
    created_at     datetime NULL,
    last_upated_at datetime NULL,
    created_by     VARCHAR(255) NULL,
    state          SMALLINT NULL,
    value          VARCHAR(255) NULL,
    expires_at     datetime NULL,
    user_id        BIGINT NULL,
    CONSTRAINT pk_token PRIMARY KEY (id)
);

ALTER TABLE token
    ADD CONSTRAINT FK_TOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);