CREATE TABLE IF NOT EXISTS facilities (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    facility_name VARCHAR(255) NOT NULL,
    code VARCHAR(20) UNIQUE,
    address VARCHAR(255),
    description TEXT,
    contact_person VARCHAR(100),
    contact_phone VARCHAR(20),
    contact_number VARCHAR(20),
    contact_email VARCHAR(100),
    email VARCHAR(100),
    active BOOLEAN DEFAULT TRUE,
    requires_approval BOOLEAN DEFAULT FALSE,
    latitude DECIMAL(10,8),
    longitude DECIMAL(11,8),
    parking_capacity INT,
    visitor_capacity INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- FCT Visitation System - Initial Database Schema
-- Version: 1.0
-- Date: 2025-04-13

-- Drop existing tables if they exist (for clean reinstallation)
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS visitors;
DROP TABLE IF EXISTS car_details;
DROP TABLE IF EXISTS rented_car_drivers;
DROP TABLE IF EXISTS parking_spaces;
DROP TABLE IF EXISTS qr_scan_logs;
DROP TABLE IF EXISTS checkpoints;
DROP TABLE IF EXISTS incident_alerts;
DROP TABLE IF EXISTS officers;
DROP TABLE IF EXISTS facilities;
DROP TABLE IF EXISTS purpose_of_visit;
DROP TABLE IF EXISTS response_teams;
DROP TABLE IF EXISTS security_personnel;
DROP TABLE IF EXISTS response_team_members;
DROP TABLE IF EXISTS admins;
DROP TABLE IF EXISTS nin_verifications;

SET FOREIGN_KEY_CHECKS = 1;

-- Create tables
CREATE TABLE facilities (
    facility_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(20) NOT NULL UNIQUE,
    address VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    contact_number VARCHAR(20),
    email VARCHAR(100),
    opening_time TIME,
    closing_time TIME,
    visitor_capacity INT,
    parking_capacity INT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    requires_approval BOOLEAN NOT NULL DEFAULT FALSE,
    latitude DECIMAL(10, 7),
    longitude DECIMAL(10, 7),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE officers (
    officer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(20),
    title VARCHAR(100),
    department VARCHAR(100),
    staff_id VARCHAR(50) UNIQUE,
    facility_id BIGINT NOT NULL,
    office_number VARCHAR(20),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    availability_status VARCHAR(20),
    max_visitors_per_day INT,
    requires_approval BOOLEAN NOT NULL DEFAULT FALSE,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(255),
    last_login TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (facility_id) REFERENCES facilities(facility_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE purpose_of_visit (
    purpose_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE,
    description VARCHAR(100) NOT NULL,
    requires_approval BOOLEAN NOT NULL DEFAULT FALSE,
    requires_id_verification BOOLEAN NOT NULL DEFAULT FALSE,
    max_duration_minutes INT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE visitors (
    visitor_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    nin VARCHAR(20) NOT NULL,
    address VARCHAR(255),
    car_type ENUM('Own', 'Rented', 'None'),
    facility_id BIGINT NOT NULL,
    officer_id BIGINT NOT NULL,
    purpose_id BIGINT NOT NULL,
    appointment_datetime DATETIME NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'Pending',
    qr_code VARCHAR(255) UNIQUE,
    additional_notes VARCHAR(500),
    approval_status VARCHAR(20),
    approved_by VARCHAR(100),
    approval_datetime DATETIME,
    checked_in_at DATETIME,
    checked_out_at DATETIME,
    cancelled_at DATETIME,
    cancellation_reason VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (facility_id) REFERENCES facilities(facility_id),
    FOREIGN KEY (officer_id) REFERENCES officers(officer_id),
    FOREIGN KEY (purpose_id) REFERENCES purpose_of_visit(purpose_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE car_details (
    car_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    visitor_id BIGINT NOT NULL,
    car_type ENUM('Own', 'Rented') NOT NULL,
    registration_number VARCHAR(20) NOT NULL,
    model VARCHAR(50) NOT NULL,
    color VARCHAR(30) NOT NULL,
    driver_id BIGINT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (visitor_id) REFERENCES visitors(visitor_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE rented_car_drivers (
    driver_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    visitor_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    nin VARCHAR(20) NOT NULL,
    license_number VARCHAR(20) NOT NULL,
    contact_number VARCHAR(15) NOT NULL,
    verification_status ENUM('Pending', 'Verified', 'Rejected') DEFAULT 'Pending',
    verified_by VARCHAR(100),
    verification_timestamp TIMESTAMP NULL,
    verification_notes VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (visitor_id) REFERENCES visitors(visitor_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Add foreign key reference after both tables are created
ALTER TABLE car_details
ADD CONSTRAINT fk_car_driver
FOREIGN KEY (driver_id) REFERENCES rented_car_drivers(driver_id) ON DELETE SET NULL;

CREATE TABLE parking_spaces (
    space_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    facility_id BIGINT NOT NULL,
    location_code VARCHAR(10) NOT NULL,
    description VARCHAR(100),
    space_type ENUM('Regular', 'VIP', 'Reserved') DEFAULT 'Regular',
    status ENUM('Available', 'Occupied', 'Maintenance') DEFAULT 'Available',
    visitor_id BIGINT NULL,
    car_id BIGINT NULL,
    allocated_at TIMESTAMP NULL,
    released_at TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (facility_id) REFERENCES facilities(facility_id),
    FOREIGN KEY (visitor_id) REFERENCES visitors(visitor_id) ON DELETE SET NULL,
    FOREIGN KEY (car_id) REFERENCES car_details(car_id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE checkpoints (
    checkpoint_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    location VARCHAR(100) NOT NULL,
    checkpoint_type VARCHAR(20) NOT NULL,
    facility_id BIGINT,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (facility_id) REFERENCES facilities(facility_id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE qr_scan_logs (
    scan_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    visitor_id BIGINT NOT NULL,
    checkpoint_id BIGINT NOT NULL,
    scanned_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    scanned_by VARCHAR(100),
    scan_result VARCHAR(20),
    notes VARCHAR(255),
    visitor_status_before VARCHAR(20),
    visitor_status_after VARCHAR(20),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (visitor_id) REFERENCES visitors(visitor_id) ON DELETE CASCADE,
    FOREIGN KEY (checkpoint_id) REFERENCES checkpoints(checkpoint_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE security_personnel (
    security_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    badge_number VARCHAR(50) NOT NULL UNIQUE,
    rank VARCHAR(50),
    email VARCHAR(100) UNIQUE,
    phone_number VARCHAR(20),
    nin VARCHAR(20) UNIQUE,
    facility_id BIGINT,
    position VARCHAR(50),
    shift VARCHAR(20),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(255),
    last_login TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (facility_id) REFERENCES facilities(facility_id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE response_teams (
    team_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(20) NOT NULL UNIQUE,
    description VARCHAR(255),
    team_leader VARCHAR(100),
    contact_number VARCHAR(20),
    email VARCHAR(100),
    location VARCHAR(100),
    availability_status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE response_team_members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    team_id BIGINT NOT NULL,
    security_id BIGINT NOT NULL,
    joined_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (team_id) REFERENCES response_teams(team_id) ON DELETE CASCADE,
    FOREIGN KEY (security_id) REFERENCES security_personnel(security_id) ON DELETE CASCADE,
    UNIQUE KEY (team_id, security_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE incident_alerts (
    alert_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    location VARCHAR(100) NOT NULL,
    severity VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    reported_by_id BIGINT,
    reported_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    assigned_team_id BIGINT,
    assigned_at TIMESTAMP NULL,
    resolved_by_id BIGINT,
    resolved_at TIMESTAMP NULL,
    resolution_notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (reported_by_id) REFERENCES security_personnel(security_id) ON DELETE SET NULL,
    FOREIGN KEY (assigned_team_id) REFERENCES response_teams(team_id) ON DELETE SET NULL,
    FOREIGN KEY (resolved_by_id) REFERENCES security_personnel(security_id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE admins (
    admin_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(20),
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'ADMIN',
    department VARCHAR(100),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    last_login TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE nin_verifications (
    verification_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nin VARCHAR(20) NOT NULL UNIQUE,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    middle_name VARCHAR(50),
    gender VARCHAR(10),
    date_of_birth VARCHAR(20),
    phone_number VARCHAR(20),
    address VARCHAR(255),
    photo TEXT,
    signature TEXT,
    is_valid BOOLEAN NOT NULL DEFAULT FALSE,
    verification_date TIMESTAMP NULL,
    expiry_date TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create indexes for better performance
CREATE INDEX idx_visitors_email ON visitors(email);
CREATE INDEX idx_visitors_nin ON visitors(nin);
CREATE INDEX idx_visitors_status ON visitors(status);
CREATE INDEX idx_visitors_appointment ON visitors(appointment_datetime);
CREATE INDEX idx_officers_facility ON officers(facility_id);
CREATE INDEX idx_officers_department ON officers(department);
CREATE INDEX idx_car_details_visitor ON car_details(visitor_id);
CREATE INDEX idx_rented_drivers_visitor ON rented_car_drivers(visitor_id);
CREATE INDEX idx_rented_drivers_nin ON rented_car_drivers(nin);
CREATE INDEX idx_parking_facility ON parking_spaces(facility_id);
CREATE INDEX idx_parking_status ON parking_spaces(status);
CREATE INDEX idx_checkpoints_facility ON checkpoints(facility_id);
CREATE INDEX idx_checkpoints_type ON checkpoints(checkpoint_type);
CREATE INDEX idx_qr_scans_visitor ON qr_scan_logs(visitor_id);
CREATE INDEX idx_qr_scans_checkpoint ON qr_scan_logs(checkpoint_id);
CREATE INDEX idx_qr_scans_timestamp ON qr_scan_logs(scanned_at);
CREATE INDEX idx_security_facility ON security_personnel(facility_id);
CREATE INDEX idx_incidents_status ON incident_alerts(status);
CREATE INDEX idx_incidents_severity ON incident_alerts(severity);
CREATE INDEX idx_incidents_team ON incident_alerts(assigned_team_id);
CREATE INDEX idx_incidents_reporter ON incident_alerts(reported_by_id);
CREATE INDEX idx_nin_verification_valid ON nin_verifications(is_valid);
CREATE INDEX idx_nin_verification_expiry ON nin_verifications(expiry_date);

-- Create full-text indexes for searching
ALTER TABLE facilities ADD FULLTEXT idx_ft_facilities (name, description, address);
ALTER TABLE incident_alerts ADD FULLTEXT idx_ft_alerts (title, description, location);

-- Audit trail table for system activities
CREATE TABLE activity_logs (
    log_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    user_type VARCHAR(20), -- ADMIN, OFFICER, SECURITY
    action VARCHAR(100) NOT NULL,
    entity_type VARCHAR(50) NOT NULL,
    entity_id BIGINT,
    details TEXT,
    ip_address VARCHAR(45),
    user_agent VARCHAR(255),
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- System settings table
CREATE TABLE system_settings (
    setting_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    setting_key VARCHAR(100) NOT NULL UNIQUE,
    setting_value TEXT,
    setting_group VARCHAR(50),
    is_public BOOLEAN DEFAULT FALSE,
    description VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Notification templates
CREATE TABLE notification_templates (
    template_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    template_code VARCHAR(50) NOT NULL UNIQUE,
    template_type VARCHAR(20) NOT NULL, -- EMAIL, SMS, IN_APP
    subject VARCHAR(100),
    body TEXT NOT NULL,
    variables JSON,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Notification queue
CREATE TABLE notification_queue (
    notification_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    recipient VARCHAR(100) NOT NULL,
    notification_type VARCHAR(20) NOT NULL, -- EMAIL, SMS, IN_APP
    subject VARCHAR(100),
    content TEXT NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING', -- PENDING, SENT, FAILED
    error_message VARCHAR(255),
    retry_count INT DEFAULT 0,
    template_id BIGINT,
    scheduled_at TIMESTAMP,
    sent_at TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (template_id) REFERENCES notification_templates(template_id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Job scheduler for recurring tasks
CREATE TABLE scheduled_jobs (
    job_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    job_name VARCHAR(100) NOT NULL UNIQUE,
    job_description VARCHAR(255),
    cron_expression VARCHAR(50) NOT NULL,
    job_class VARCHAR(100) NOT NULL,
    job_data JSON,
    is_active BOOLEAN DEFAULT TRUE,
    last_run TIMESTAMP NULL,
    next_run TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create role-based access control tables
CREATE TABLE roles (
    role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    is_system_role BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE permissions (
    permission_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    permission_name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255),
    module VARCHAR(50),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE role_permissions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions(permission_id) ON DELETE CASCADE,
    UNIQUE KEY (role_id, permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Comments: This script creates the database schema for the FCT Visitation System.
-- All tables use InnoDB for transaction support and utf8mb4 for full Unicode support.
-- Foreign keys are used to maintain data integrity between related tables.
-- Indexes are created for frequently queried columns to improve performance.
-- Timestamp columns are added for audit and tracking purposes.
-- The schema includes support for NIN verification, QR code scanning, security alerts,
-- parking management, and other key features of the system.title VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    location VARCHAR(100) NOT NULL,
    severity VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    reported_by_id BIGINT,
    reported_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    assigned_team_id BIGINT,
    assigned_at TIMESTAMP NULL,
    resolved_by_id BIGINT,
    resolved_at TIMESTAMP NULL,
    resolution_notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (reported_by_id) REFERENCES security_personnel(security_id) ON DELETE SET NULL,
    FOREIGN KEY (assigned_team_id) REFERENCES response_teams(team_id) ON DELETE SET NULL,
    FOREIGN KEY (resolved_by_id) REFERENCES security_personnel(security_id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE admins (
    admin_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(20),
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'ADMIN',
    department VARCHAR(100),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    last_login TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE nin_verifications (
    verification_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nin VARCHAR(20) NOT NULL UNIQUE,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    middle_name VARCHAR(50),
    gender VARCHAR(10),
    date_of_birth VARCHAR(20),
    phone_number VARCHAR(20),
    address VARCHAR(255),
    photo TEXT,
    signature TEXT,
    is_valid BOOLEAN NOT NULL DEFAULT FALSE,
    verification_date TIMESTAMP NULL,
    expiry_date TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create indexes for better performance
CREATE INDEX idx_visitors