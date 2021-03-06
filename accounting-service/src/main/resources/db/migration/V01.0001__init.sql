CREATE SCHEMA IF NOT EXISTS accountingservice;

CREATE TABLE IF NOT EXISTS accountingservice.accounting
(
    ID               SERIAL PRIMARY KEY,
    PASSENGER        BIGINT,
    SEAT             VARCHAR(50),
    ACCOUNTINGSTATUS VARCHAR(25),
    UNIQUE (SEAT)
);