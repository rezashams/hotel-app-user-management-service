    DROP TABLE IF EXISTS user_;

    CREATE TABLE user_ (
    id INT  PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    is_student BOOL NOT NULL,
    is_manager BOOL NOT NULL
    );

