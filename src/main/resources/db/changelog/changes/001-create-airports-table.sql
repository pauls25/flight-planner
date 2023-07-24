--liquibase formatted sql

--changeset pauls:2

CREATE TABLE airports
(
    airport_id SERIAL,
    country varchar(255) NOT NULL,
    city varchar(255) NOT NULL,
    airport varchar(255) NOT NULL
)