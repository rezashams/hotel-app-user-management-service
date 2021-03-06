    DROP TABLE IF EXISTS user_;

    CREATE TABLE user_ (
    id BIGINT  PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    is_student BOOL NOT NULL,
    is_manager BOOL NOT NULL,
    last_update DATE
    );

