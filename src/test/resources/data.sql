CREATE TABLE IF NOT EXISTS bank_account
(
    id              SERIAL PRIMARY KEY,
    first_name      VARCHAR(40)  NOT NULL,
    last_name       VARCHAR(40)  NOT NULL,
    address         VARCHAR(256) NOT NULL,
    iban            VARCHAR(30)  NOT NULL,
    account_number  VARCHAR(20)  NOT NULL,
    account_status  VARCHAR(20)  NOT NULL,
    current_balance FLOAT,
    overdraft_limit  FLOAT
);


CREATE TABLE IF NOT EXISTS transactions
(
    id                     SERIAL PRIMARY KEY,
    amount                 FLOAT,
    running_balance        FLOAT,
    transaction_type       VARCHAR(50)  NOT NULL,
    reference              VARCHAR(50)  NOT NULL,
    details                VARCHAR(256) NOT NULL,
    account_id             INT,
    related_transaction_id INT,
    transaction_date       TIMESTAMP    NOT NULL,
    CONSTRAINT fk_bank_accound_id
        FOREIGN KEY (account_id)
            REFERENCES bank_account (id)
);
CREATE TABLE IF NOT EXISTS transactions
(
    id               SERIAL PRIMARY KEY,
    amount           FLOAT,
    running_balance  FLOAT,
    transaction_type VARCHAR(50)  NOT NULL,
    reference        VARCHAR(50)  NOT NULL,
    details          VARCHAR(256) NOT NULL,
    account_id       INTEGER,
    related_transaction_id INTEGER,
    transaction_date TIMESTAMP    NOT NULL
);

CREATE UNIQUE INDEX iban_idx ON bank_account (iban);

insert into bank_account (id, first_name, last_name, address, iban, account_number, account_status,
                          current_balance, overdraft_limit)
VALUES (1, 'Aamir', 'Latif', 'Berlin', 'DE64120300000001234567', '0001234567', 'ACTIVE', 1000.0, 200.0),
       (2, 'Mustafa', 'Aamir', 'Berlin', 'DE80120300000007654321', '0007654321', 'ACTIVE', 2000.0, 200.0);



insert into transactions (id, amount, running_balance, transaction_type, reference, details, account_id,
                          related_transaction_id, transaction_date)
VALUES (1, 30.0, 1130.0, 'DEPOSIT', 'Sample reference 1', 'Sample Detail 1', 1, NULL, '2020-08-08T16:59:08'),
       (2, 130.0, 1000.0, 'WITHDRAWAL', 'Sample reference 2', 'Sample Detail 2', 1, 3, '2020-08-10T05:59:08'),
       (3, 130.0, 2000.0, 'DEPOSIT', 'Sample reference 3', 'Sample Detail 3', 2, 2, '2020-08-10T05:59:08');
