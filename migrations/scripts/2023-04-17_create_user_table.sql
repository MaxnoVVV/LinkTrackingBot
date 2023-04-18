--liquibase formatted sql

--changeset m.nov:create_user_table


CREATE TABLE user(
    user_id SERIAL PRIMARY KEY,
    Id INT
);