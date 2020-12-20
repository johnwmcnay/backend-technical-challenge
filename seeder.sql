DROP DATABASE IF EXISTS restful_db;
CREATE DATABASE IF NOT EXISTS restful_db;

USE restful_db;


INSERT INTO persons (name, age, date_joined, date_updated)
VALUES ('John McNay', 38, '2020-07-20', '2020-12-19'),
       ('Bob Smith', 42, '2016-03-21', '2017-11-01'),
       ('Bob Thompson', 54, '2012-08-14', '2015-03-04');