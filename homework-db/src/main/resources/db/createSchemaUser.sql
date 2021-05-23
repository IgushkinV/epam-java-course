CREATE USER usr WITH PASSWORD 'pass';
CREATE database epamcourses owner usr;
CREATE SCHEMA IF NOT EXISTS igushkin;
GRANT ALL ON SCHEMA igushkin TO usr;
