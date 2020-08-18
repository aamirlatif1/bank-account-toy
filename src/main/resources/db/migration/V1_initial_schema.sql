
CREATE TABLE IF NOT EXISTS bank_account (
    id                  INT PRIMARY KEY,
    first_name          VARCHAR(40) NOT NULL,
    last_name           VARCHAR(40) NOT NULL,
    address             VARCHAR(256) NOT NULL,
    iban                VARCHAR(30),
    account_number      VARCHAR(20),
    account_status      VARCHAR(20) NOT NULL,
    current_balance     FLOAT,
    overdraft_limit     FLOAT
);

CREATE SEQUENCE IF NOT EXISTS account_sequence
INCREMENT 1
MINVALUE 1
START 1;


CREATE TABLE IF NOT EXISTS transactions (
    id                     INT PRIMARY KEY,
    amount                 FLOAT,
    running_balance        FLOAT,
    transaction_type       VARCHAR(50) NOT NULL,
    reference              VARCHAR(50) NOT NULL,
    details                VARCHAR(256) NOT NULL,
    account_id             INT,
    related_transaction_id INT,
    transaction_date       TIMESTAMP NOT NULL,
    CONSTRAINT fk_bank_account_id
      FOREIGN KEY(account_id)
	  REFERENCES bank_account(id)
);

CREATE SEQUENCE IF NOT EXISTS transaction_sequence
INCREMENT 1
MINVALUE 1
START 1;

CREATE UNIQUE INDEX iban_idx ON bank_account (iban);
