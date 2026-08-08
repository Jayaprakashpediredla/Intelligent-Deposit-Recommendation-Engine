CREATE TABLE user_accounts
(
    id UUID PRIMARY KEY,

    username VARCHAR(100) NOT NULL UNIQUE,

    password_hash VARCHAR(255) NOT NULL,

    customer_id UUID UNIQUE,

    role VARCHAR(30) NOT NULL,

    enabled BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP
);

CREATE INDEX idx_user_account_username
    ON user_accounts(username);

CREATE INDEX idx_user_account_customer_id
    ON user_accounts(customer_id);