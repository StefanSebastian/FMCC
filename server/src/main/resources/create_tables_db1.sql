create table beers(
    id SERIAL PRIMARY KEY,
    name CHAR(50) NOT NULL,
    style CHAR(50) NOT NULL,
    description TEXT,
    producer CHAR(100) NOT NULL
);

create table stocks(
    beer_id INT PRIMARY KEY,
    available INT NOT NULL CHECK(available >= 0),
    price REAL NOT NULL CHECK(price > 0)
);