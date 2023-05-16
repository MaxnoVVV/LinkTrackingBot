--liquibase formatted sql

--changeset m.nov:create_link_table

CREATE TABLE links(
    link_id SERIAL PRIMARY KEY,
    link VARCHAR(1024),
    tracking_user INT,
    last_check TIMESTAMP
);