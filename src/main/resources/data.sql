INSERT INTO CREDIT (name, amount, value)
SELECT 'DOLLAR', 0, 1 FROM DUAL WHERE NOT EXISTS (SELECT * FROM CREDIT
    WHERE name = 'DOLLAR' AND amount = 0 AND value = 1);

    INSERT INTO CREDIT (name, amount, value)
SELECT 'QUARTER', 0, 0.25 FROM DUAL WHERE NOT EXISTS (SELECT * FROM CREDIT
    WHERE name = 'QUARTER' AND amount = 0 AND value = 0.25);

    INSERT INTO CREDIT (name, amount, value)
SELECT 'DIME', 0, 0.1 FROM DUAL WHERE NOT EXISTS (SELECT * FROM CREDIT
    WHERE name = 'DIME' AND amount = 0 AND value = 0.1);

INSERT INTO CREDIT (name, amount, value)
SELECT 'NICKEL', 0, 0.05 FROM DUAL WHERE NOT EXISTS (SELECT * FROM CREDIT
    WHERE name = 'NICKEL' AND amount = 0 AND value = 0.05);

--     ('DOLLAR', 0, 1),
--     ('QUARTER', 0, 0.25),
--     ('DIME', 0, 0.1),
--     ('NICKEL', 0, 0.05);

    INSERT INTO PRODUCT(name, amount, price)
SELECT 'PEPSI', 3, 1.1 FROM DUAL WHERE NOT EXISTS (SELECT * FROM PRODUCT
    WHERE NAME = 'PEPSI' AND amount = 3 AND price = 1.1);

    INSERT INTO PRODUCT(name, amount, price)
SELECT 'COCA_COLA', 2, 1 FROM DUAL WHERE NOT EXISTS (SELECT * FROM PRODUCT
    WHERE NAME = 'COCA_COLA' AND amount = 2 AND price = 1);

     INSERT INTO PRODUCT(name, amount, price)
SELECT 'REDBULL', 0, 1.6 FROM DUAL WHERE NOT EXISTS (SELECT * FROM PRODUCT
    WHERE NAME = 'REDBULL' AND amount = 0 AND price = 1.6);

-- INSERT INTO PRODUCT (name, amount, price) VALUES
--     ('PEPSI', 3, 1.1),
--     ('COCA_COLA', 2, 1),
--     ('REDBULL', 0, 1.6 );


