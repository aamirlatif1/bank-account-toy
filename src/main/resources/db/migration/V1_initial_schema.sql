
CREATE TABLE IF NOT EXISTS bank_account (
    id                  INTEGER PRIMARY KEY,
    full_name           VARCHAR(40) NOT NULL,
    start_time          TIME ,
    end_time            TIME ,
    age_limit_start     INTEGER ,
    age_limit_end       INTEGER,
    price               FLOAT
);


CREATE TABLE IF NOT EXISTS transactions (
    id             SERIAL PRIMARY KEY,
    username       VARCHAR(50) NOT NULL,
    first_name     VARCHAR(50) NOT NULL,
    last_name      VARCHAR(50) NOT NULL,
    phone_number   VARCHAR(30) NOT NULL,
    dob            DATE,
    created_date   TIMESTAMP NOT NULL
);