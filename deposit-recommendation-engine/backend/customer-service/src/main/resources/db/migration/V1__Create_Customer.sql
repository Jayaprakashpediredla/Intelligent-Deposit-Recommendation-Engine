-- Flyway migration for customer service
-- V1__Create_Customer.sql

CREATE TABLE customers (
    id UUID PRIMARY KEY,
    version BIGINT,

    customer_number VARCHAR(20) NOT NULL UNIQUE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,

    email VARCHAR(255) NOT NULL UNIQUE,
    mobile_number VARCHAR(15) NOT NULL UNIQUE,

    date_of_birth DATE NOT NULL,

    occupation VARCHAR(100) NOT NULL,

    annual_income NUMERIC(15,2) NOT NULL,
    monthly_income NUMERIC(15,2) NOT NULL,
    employment_type VARCHAR(30) NOT NULL,
    preferred_investment_tenure VARCHAR(30) NOT NULL,
    preferred_communication_channel VARCHAR(30) NOT NULL,

    risk_profile VARCHAR(20) NOT NULL,

    status VARCHAR(20) NOT NULL,

    kyc_verified BOOLEAN NOT NULL,

    created_date TIMESTAMP,
    last_modified_date TIMESTAMP,
    created_by VARCHAR(100),
    last_modified_by VARCHAR(100)
);

CREATE INDEX idx_customer_number
ON customers(customer_number);

CREATE INDEX idx_email
ON customers(email);

CREATE INDEX idx_mobile
ON customers(mobile_number);