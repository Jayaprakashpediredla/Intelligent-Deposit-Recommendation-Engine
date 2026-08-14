CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,

    transaction_reference VARCHAR(50) NOT NULL UNIQUE,

    customer_id BIGINT NOT NULL,

    transaction_type VARCHAR(30) NOT NULL,

    amount NUMERIC(19, 2) NOT NULL,

    currency VARCHAR(3) NOT NULL,

    transaction_date TIMESTAMP NOT NULL,

    status VARCHAR(20) NOT NULL,

    description VARCHAR(255),

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_transaction_customer_id
    ON transactions(customer_id);

CREATE INDEX idx_transaction_date
    ON transactions(transaction_date);

CREATE INDEX idx_transaction_status
    ON transactions(status);