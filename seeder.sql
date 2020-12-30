DROP DATABASE IF EXISTS restful_db;
CREATE DATABASE IF NOT EXISTS restful_db;

USE restful_db;

INSERT INTO jobs (job_title, salary)
VALUES ('Junior Developer', 55000),
       ('Senior Developer', 75000),
       ('Software Development Consultant', 100000);

INSERT INTO persons (name, age, date_joined, date_updated, job_id)
VALUES ('John McNay', 38, '2020-07-20', '2020-12-19', 1),
       ('Bob Smith', 42, '2016-03-21', '2017-11-01', 1),
       ('Bob Thompson', 54, '2012-08-14', '2015-03-04', 2),
       ('Eric Matthews', 27, '2015-05-05', '2017-07-07', 3);