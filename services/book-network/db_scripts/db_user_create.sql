create database book_social_network;
GRANT ALL PRIVILEGES ON DATABASE book_social_network TO bsnadmin;
GRANT CONNECT ON DATABASE book_social_network TO bsnadmin;
GRANT USAGE ON SCHEMA public TO bsnadmin;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO bsnadmin;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO bsnadmin;
GRANT CREATE ON SCHEMA public TO bsnadmin;