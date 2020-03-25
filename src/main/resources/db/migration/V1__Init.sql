DROP TABLE IF EXISTS solar_park, hourly_production_data;

SET timezone TO 'UTC';

CREATE TABLE IF NOT EXISTS solar_park
(
    id                   BIGSERIAL     NOT NULL PRIMARY KEY,
    name                 VARCHAR(50)   NOT NULL,
    capacity_per_hour_mw NUMERIC(6, 2) NOT NULL,
    time_zone            VARCHAR(50)   NOT NULL CHECK (now() AT TIME ZONE time_zone IS NOT NULL)
);

CREATE TABLE IF NOT EXISTS hourly_production_data
(
    id                      BIGSERIAL     NOT NULL PRIMARY KEY,
    solar_park_id           BIGINT        NOT NULL,
    time_stamp              TIMESTAMP     NOT NULL,
    electricity_produced_mw NUMERIC(6, 2) NOT NULL,
    FOREIGN KEY (solar_park_id) REFERENCES solar_park (id)
);


INSERT INTO solar_park(id, name, capacity_per_hour_mw, time_zone)
VALUES (1, 'Japan', 10, 'JST');
INSERT INTO solar_park(id, name, capacity_per_hour_mw, time_zone)
VALUES (2, 'Eastern Europe', 20, 'EST');
INSERT INTO solar_park(id, name, capacity_per_hour_mw, time_zone)
VALUES (3, 'Greenwich', 30, 'GMT');

--Japan
INSERT INTO hourly_production_data(solar_park_id, time_stamp, electricity_produced_mw)
VALUES (1, '2020-03-01T00:00:00.000Z', 5.0);
INSERT INTO hourly_production_data(solar_park_id, time_stamp, electricity_produced_mw)
VALUES (1, '2020-03-01T01:00:00.000Z', 5.0);
INSERT INTO hourly_production_data(solar_park_id, time_stamp, electricity_produced_mw)
VALUES (1, '2020-03-02T00:00:00.000Z', 10.0);

--Eastern Europe
INSERT INTO hourly_production_data(solar_park_id, time_stamp, electricity_produced_mw)
VALUES (2, '2020-03-04T00:00:00.000Z', 0.0);
INSERT INTO hourly_production_data(solar_park_id, time_stamp, electricity_produced_mw)
VALUES (2, '2020-03-04T01:00:00.000Z', 20.0);

--Greenwich
INSERT INTO hourly_production_data(solar_park_id, time_stamp, electricity_produced_mw)
VALUES (3, '2020-03-01T00:00:00.000Z', 30);
