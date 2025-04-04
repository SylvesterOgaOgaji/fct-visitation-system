--liquibase formatted sql

--changeset author:fct-visitation:1
CREATE TABLE facilities (
    facility_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    facility_name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    contact_number VARCHAR(15) NOT NULL,
    email VARCHAR(100)
);

CREATE TABLE officers (
    officer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    facility_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    position VARCHAR(50) NOT NULL,
    department VARCHAR(50) NOT NULL,
    duty_status VARCHAR(10) NOT NULL DEFAULT 'ON_DUTY',
    email VARCHAR(100),
    phone_number VARCHAR(15),
    FOREIGN KEY (facility_id) REFERENCES facilities(facility_id)
);

CREATE TABLE purpose_of_visit (
    purpose_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    facility_id BIGINT NOT NULL,
    description VARCHAR(100) NOT NULL,
    FOREIGN KEY (facility_id) REFERENCES facilities(facility_id)
);

CREATE TABLE visitors (
    visitor_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(15) NOT NULL,
    nin VARCHAR(20) NOT NULL UNIQUE,
    car_type VARCHAR(10) NOT NULL,
    facility_id BIGINT NOT NULL,
    officer_id BIGINT NOT NULL,
    purpose_id BIGINT NOT NULL,
    appointment_datetime DATETIME NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    qr_code VARCHAR(255) UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (facility_id) REFERENCES facilities(facility_id),
    FOREIGN KEY (officer_id) REFERENCES officers(officer_id),
    FOREIGN KEY (purpose_id) REFERENCES purpose_of_visit(purpose_id)
);

CREATE TABLE car_details (
    car_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    visitor_id BIGINT NOT NULL,
    car_type VARCHAR(10) NOT NULL,
    registration_number VARCHAR(20) NOT NULL,
    model VARCHAR(50) NOT NULL,
    color VARCHAR(30) NOT NULL,
    driver_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (visitor_id) REFERENCES visitors(visitor_id)
);

CREATE TABLE rented_car_drivers (
    driver_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    visitor_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    nin VARCHAR(20) NOT NULL UNIQUE,
    license_number VARCHAR(20) NOT NULL,
    contact_number VARCHAR(15) NOT NULL,
    verification_status VARCHAR(10) NOT NULL DEFAULT 'PENDING',
    verified_by BIGINT,
    verification_timestamp TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (visitor_id) REFERENCES visitors(visitor_id)
);

ALTER TABLE car_details 
ADD CONSTRAINT fk_car_driver 
FOREIGN KEY (driver_id) REFERENCES rented_car_drivers(driver_id);

CREATE TABLE parking_spaces (
    space_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    facility_id BIGINT NOT NULL,
    location_code VARCHAR(10) NOT NULL,
    description VARCHAR(100),
    space_type VARCHAR(10) NOT NULL DEFAULT 'REGULAR',
    status VARCHAR(15) NOT NULL DEFAULT 'AVAILABLE',
    visitor_id BIGINT,
    car_id BIGINT,
    allocated_at TIMESTAMP,
    released_at TIMESTAMP,
    FOREIGN KEY (facility_id) REFERENCES facilities(facility_id),
    FOREIGN KEY (visitor_id) REFERENCES visitors(visitor_id),
    FOREIGN KEY (car_id) REFERENCES car_details(car_id)
);

CREATE TABLE checkpoints (
    checkpoint_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    location_name VARCHAR(100) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE qr_scan_log (
    scan_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    visitor_id BIGINT NOT NULL,
    checkpoint_id BIGINT NOT NULL,
    scan_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (visitor_id) REFERENCES visitors(visitor_id),
    FOREIGN KEY (checkpoint_id) REFERENCES checkpoints(checkpoint_id)
);

CREATE TABLE security_personnel (
    security_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'GUARD',
    contact_number VARCHAR(15)
);

CREATE TABLE response_teams (
    team_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    team_name VARCHAR(100) NOT NULL,
    contact_number VARCHAR(15) NOT NULL,
    email VARCHAR(100)
);

CREATE TABLE incident_alerts (
    alert_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    visitor_id BIGINT,
    reported_by BIGINT NOT NULL,
    time_reported TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    location VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    response_team_id BIGINT NOT NULL,
    response_time TIMESTAMP,
    status VARCHAR(15) NOT NULL DEFAULT 'PENDING',
    FOREIGN KEY (visitor_id) REFERENCES visitors(visitor_id),
    FOREIGN KEY (reported_by) REFERENCES security_personnel(security_id),
    FOREIGN KEY (response_team_id) REFERENCES response_teams(team_id)
);

CREATE TABLE admins (
    admin_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    facility_id BIGINT NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(15) NOT NULL DEFAULT 'SUB_ADMIN',
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    contact_number VARCHAR(15),[C
# Continue creating the initial schema migration
cat >> src/main/resources/db/changelog/changes/001-initial-schema.sql << 'EOF'
    FOREIGN KEY (facility_id) REFERENCES facilities(facility_id)
);

-- Indexes for better query performance
CREATE INDEX idx_visitor_facility ON visitors(facility_id);
CREATE INDEX idx_visitor_officer ON visitors(officer_id);
CREATE INDEX idx_visitor_status ON visitors(status);
CREATE INDEX idx_car_visitor ON car_details(visitor_id);
CREATE INDEX idx_parking_facility ON parking_spaces(facility_id);
CREATE INDEX idx_parking_status ON parking_spaces(status);
CREATE INDEX idx_qrscan_visitor ON qr_scan_log(visitor_id);
CREATE INDEX idx_qrscan_checkpoint ON qr_scan_log(checkpoint_id);
CREATE INDEX idx_incident_status ON incident_alerts(status);
