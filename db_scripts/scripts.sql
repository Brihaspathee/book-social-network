CREATE database booksocialnetdb;

CREATE USER booksocialnetadmin WITH PASSWORD 'password';

GRANT ALL PRIVILEGES ON DATABASE booksocialnetdb TO booksocialnetadmin;

-- Run the below queries after logging into boolsocialnetdb with booksocialnetadmin

CREATE SCHEMA booksocialnet AUTHORIZATION booksocialnetadmin;

ALTER ROLE booksocialnetadmin SET search_path TO booksocialnet;

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA booksocialnet TO booksocialnetadmin;