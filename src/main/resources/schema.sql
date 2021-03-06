-- CREATE SCHEMA vending_machine;

CREATE TABLE IF NOT EXISTS CREDIT (
    name VARCHAR(30) PRIMARY KEY,
    amount INTEGER NOT NULL,
    value DECIMAL(19,2) NOT NULL);

CREATE TABLE IF NOT EXISTS PRODUCT (
    name VARCHAR(30) PRIMARY KEY,
    amount INTEGER NOT NULL,
    price decimal(19,2) NOT NULL);