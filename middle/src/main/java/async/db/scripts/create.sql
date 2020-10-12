CREATE TABLE country(
id serial primary key,
name varchar(100) UNIQUE
);

CREATE TABLE users(
id serial primary key,
firstname varchar(100),
lastname varchar(100),
rating int,
country int references country(id)
);
