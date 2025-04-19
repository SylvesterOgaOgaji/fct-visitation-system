CREATE TABLE IF NOT EXISTS officers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    facility_id BIGINT,
    name VARCHAR(255),
    position VARCHAR(255),
    department VARCHAR(255),
    duty_status VARCHAR(255),
    email VARCHAR(255),
    phone_number VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS visitors (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone_number VARCHAR(20) NOT NULL,
    check_in_time TIMESTAMP,
    check_out_time TIMESTAMP,
    officer_id BIGINT
);