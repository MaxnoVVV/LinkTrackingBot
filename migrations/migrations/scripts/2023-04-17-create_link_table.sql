--liquibase formatted sql

--changeset m.nov:create_link_table

CREATE TABLE links(
    link_id SERIAL PRIMARY KEY,
    link VARCHAR(256),
    tracking_user INT,
    last_check TIMESTAMP,
    FOREIGN KEY (tracking_user) REFERENCES users (Id) ON DELETE CASCADE
);