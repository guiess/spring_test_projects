drop table Customers if exists;

create table Customers(
  id         INTEGER IDENTITY PRIMARY KEY,
  firstName VARCHAR(30),
  lastName  VARCHAR(30),
  recordCurrentDate DATE
);