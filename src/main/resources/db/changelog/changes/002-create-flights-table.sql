--liquibase formatted sql

--changeset pauls:1

CREATE TABLE flights
(
    flight_id SERIAL PRIMARY KEY ,
    from_airport_id bigint,
    to_airport_id bigint,
    carrier varchar(255),
    departure_time timestamp,
    arrival_time timestamp,
    CONSTRAINT fk_from_airport_id FOREIGN KEY (from_airport_id) REFERENCES airports (airport_id),
    CONSTRAINT fk_to_airport_id FOREIGN KEY (to_airport_id) REFERENCES airports (airport_id)
);