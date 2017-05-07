CREATE TABLE income (
    id IDENTITY PRIMARY KEY,
    begin DATE NOT NULL,
    end DATE NULL,
    amount DECIMAL(10, 2) NOT NULL,
    description VARCHAR(255) NOT NULL
);
