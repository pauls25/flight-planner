--liquibase formatted sql

--changeset pauls:1

CREATE TABLE airports
(
    airport_id SERIAL PRIMARY KEY,
    country varchar(255) NOT NULL,
    city varchar(255) NOT NULL,
    airport varchar(255) NOT NULL
);

CREATE TABLE flights
(
    flight_id SERIAL PRIMARY KEY ,
    from_airport_id bigint,
    to_airport_id bigint,
    carrier varchar(255),
    departure_time varchar(255),
    arrival_time varchar(255),
    FOREIGN KEY (from_airport_id) REFERENCES airports (airport_id) ON UPDATE CASCADE,
    FOREIGN KEY (to_airport_id) REFERENCES airports (airport_id) ON UPDATE CASCADE
);