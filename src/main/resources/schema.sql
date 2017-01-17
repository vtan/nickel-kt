CREATE TABLE expense (
    id IDENTITY PRIMARY KEY,
    date DATE NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    category VARCHAR(255) NOT NULL
);
