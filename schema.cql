CREATE KEYSPACE killr_video WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1};

USE killr_video;

CREATE TABLE users (
    user_id uuid,
    first_name text,
    last_name text,
    email text,
    created_date timestamp,
    PRIMARY KEY (user_id)
);
