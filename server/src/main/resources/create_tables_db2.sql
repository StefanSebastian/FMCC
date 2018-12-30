create table receipts(
    id SERIAL PRIMARY KEY,
    totalPrice REAL NOT NULL,
    address TEXT NOT NULL,
    description TEXT NOT NULL
);