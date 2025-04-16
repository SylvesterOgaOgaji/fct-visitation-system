-- FCT Visitation System - Initial Data
-- Version: 1.0
-- Date: 2025-04-13

-- Disable foreign key checks temporarily for bulk loading
SET FOREIGN_KEY_CHECKS = 0;

-- Delete existing data for clean reinsertion
TRUNCATE TABLE facilities;
TRUNCATE TABLE officers;
TRUNCATE TABLE purpose_of_visit;
TRUNCATE TABLE checkpoints;
TRUNCATE TABLE security_personnel;
TRUNCATE TABLE response_teams;
TRUNCATE TABLE response_team_members;
TRUNCATE TABLE admins;
TRUNCATE TABLE roles;
TRUNCATE TABLE permissions;
TRUNCATE TABLE role_permissions;
TRUNCATE TABLE system_settings;
TRUNCATE TABLE notification_templates;

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

-- Insert Facilities
INSERT INTO facilities (name, code, address, description, contact_number, email, opening_time, closing_time, visitor_capacity, parking_capacity, is_active) VALUES
('FCT Administration Headquarters', 'FCT-HQ', 'Area 11, Garki, Abuja', 'Main FCT Administration Building', '+2348023456789', 'info@fct.gov.ng', '08:00:00', '17:00:00', 500, 200, true),
('FCDA Secretariat', 'FCDA-SEC', 'Area 3, Garki, Abuja', 'Federal Capital Development Authority Secretariat', '+2348023456790', 'info@fcda.gov.ng', '08:00:00', '17:00:00', 300, 150, true),
('Ministry of the Federal Capital', 'MFC-01', 'Area 10, Garki, Abuja', 'Ministry Headquarters', '+2348023456791', 'info@mfct.gov.ng', '08:00:00', '17:00:00', 250, 100, true),
('Abuja Municipal Area Council', 'AMAC-01', 'Garki, Abuja', 'AMAC Office Complex', '+2348023456792', 'info@amac.gov.ng', '08:00:00', '16:30:00', 200, 80, true),
('Abuja Geographic Information Systems', 'AGIS-01', 'Cadastral Zone, Abuja', 'AGIS Main Office', '+2348023456793', 'info@agis.gov.ng', '08:30:00', '16:30:00', 150, 50, true);

-- Insert Purposes of Visit
INSERT INTO purpose_of_visit (code, description, requires_approval, requires_id_verification, max_duration_minutes, is_active) VALUES
('MEETING', 'Official Meeting', false, true, 120, true),
('DOCUMENT', 'Document Submission/Collection', false, true, 60, true),
('INQUIRY', 'General Inquiry/Information', false, false, 30, true),
('INTERVIEW', 'Job Interview', true, true, 90, true),
('CONTRACT', 'Contract Discussion/Signing', true, true, 180, true),
('INSPECTION', 'Facility Inspection', true, true, 240, true),
('DELIVERY', 'Delivery of Items', false, true, 30, true),
('MAINTENANCE', 'Facility Maintenance', true, true, 300, true),
('CONFERENCE', 'Conference/Workshop', true, true, 480, true),
('OTHER', 'Other Purpose', true, true, 120, true);

-- Insert Departments
INSERT INTO departments (department_name, facility_id, description, is_active) VALUES
('Office of the Minister', 1, 'Office of the FCT Minister', true),
('Administration & Finance', 1, 'Administration and Finance Department', true),
('Urban Planning', 2, 'Urban Planning and Development Department', true),
('Environmental Services', 2, 'Environmental Services Department', true),
('Legal Services', 1, 'Legal Services Department', true),
('Infrastructure Development', 3, 'Infrastructure Development Department', true),
('Social Services', 3, 'Social Services Department', true),
('Transportation', 3, 'Transportation Department', true),
('Land Administration', 5, 'Land Administration Department', true),
('Public Relations', 1, 'Public Relations Department', true);

-- Insert Officers (passwords are hashed with BCrypt - default password is "Password123#")
INSERT INTO officers (first_name, last_name, email, phone_number, title, department_id, staff_id, facility_id, office_number, is_active, availability_status, max_visitors_per_day, username, password) VALUES
('Ahmed', 'Ibrahim', 'ahmed.ibrahim@fct.gov.ng', '+2348012345601', 'Director', 1, 'FCT-DIR-001', 1, 'A101', true, 'AVAILABLE', 10, 'ahmed.ibrahim', '$2a$10$xJwrX7KA.XPCqz9NeXk97OTbBe8j/o9.qlDcikXnyUGpuNeDH3gfO'),
('Blessing', 'Okonkwo', 'blessing.okonkwo@fct.gov.ng', '+2348012345602', 'Deputy Director', 2, 'FCT-DD-002', 1, 'A102', true, 'AVAILABLE', 8, 'blessing.okonkwo', '$2a$10$xJwrX7KA.XPCqz9NeXk97OTbBe8j/o9.qlDcikXnyUGpuNeDH3gfO'),
('Charles', 'Nnamdi', 'charles.nnamdi@fcda.gov.ng', '+2348012345603', 'Senior Planner', 3, 'FCDA-SP-001', 2, 'B201', true, 'AVAILABLE', 6, 'charles.nnamdi', '$2a$10$xJwrX7KA.XPCqz9NeXk97OTbBe8j/o9.qlDcikXnyUGpuNeDH3gfO'),
('Deborah', 'Adeyemi', 'deborah.adeyemi@fcda.gov.ng', '+2348012345604', 'Environmental Officer', 4, 'FCDA-EO-002', 2, 'B202', true, 'AVAILABLE', 6, 'deborah.adeyemi', '$2a$10$xJwrX7KA.XPCqz9NeXk97OTbBe8j/o9.qlDcikXnyUGpuNeDH3gfO'),
('Emmanuel', 'Babatunde', 'emmanuel.babatunde@fct.gov.ng', '+2348012345605', 'Legal Adviser', 5, 'FCT-LA-001', 1, 'A103', true, 'AVAILABLE', 5, 'emmanuel.babatunde', '$2a$10$xJwrX7KA.XPCqz9NeXk97OTbBe8j/o9.qlDcikXnyUGpuNeDH3gfO'),
('Fatima', 'Mohammed', 'fatima.mohammed@mfct.gov.ng', '+2348012345606', 'Infrastructure Director', 6, 'MFC-ID-001', 3, 'C301', true, 'AVAILABLE', 7, 'fatima.mohammed', '$2a$10$xJwrX7KA.XPCqz9NeXk97OTbBe8j/o9.qlDcikXnyUGpuNeDH3gfO'),
('George', 'Okafor', 'george.okafor@mfct.gov.ng', '+2348012345607', 'Social Services Coordinator', 7, 'MFC-SSC-002', 3, 'C302', true, 'AVAILABLE', 10, 'george.okafor', '$2a$10$xJwrX7KA.XPCqz9NeXk97OTbBe8j/o9.qlDcikXnyUGpuNeDH3gfO'),
('Hannah', 'Suleiman', 'hannah.suleiman@amac.gov.ng', '+2348012345608', 'Council Secretary', 8, 'AMAC-CS-001', 4, 'D401', true, 'AVAILABLE', 8, 'hannah.suleiman', '$2a$10$xJwrX7KA.XPCqz9NeXk97OTbBe8j/o9.qlDcikXnyUGpuNeDH3gfO'),
('Ibrahim', 'Kolade', 'ibrahim.kolade@agis.gov.ng', '+2348012345609', 'GIS Director', 9, 'AGIS-GD-001', 5, 'E501', true, 'AVAILABLE', 6, 'ibrahim.kolade', '$2a$10$xJwrX7KA.XPCqz9NeXk97OTbBe8j/o9.qlDcikXnyUGpuNeDH3gfO'),
('Janet', 'Usman', 'janet.usman@fct.gov.ng', '+2348012345610', 'Public Relations Officer', 10, 'FCT-PRO-001', 1, 'A104', true, 'AVAILABLE', 15, 'janet.usman', '$2a$10$xJwrX7KA.XPCqz9NeXk97OTbBe8j/o9.qlDcikXnyUGpuNeDH3gfO');

-- Insert Checkpoints
INSERT INTO checkpoints (name, description, location, checkpoint_type, facility_id, is_active) VALUES
('Main Entrance', 'Main entrance gate for all visitors', 'Front Gate, Ground Floor', 'ENTRY', 1, true),
('Reception Desk', 'Visitor registration desk at reception', 'Reception Area, Ground Floor', 'REGISTRATION', 1, true),
('Executive Floor', 'Access checkpoint for executive offices', '5th Floor Entrance', 'FACILITY', 1, true),
('Parking Entrance', 'Entrance to parking area', 'Basement Level 1', 'PARKING', 1, true),
('Exit Gate', 'Main exit point', 'Rear Gate, Ground Floor', 'EXIT', 1, true),
('FCDA Main Gate', 'Main entrance to FCDA complex', 'North Gate', 'ENTRY', 2, true),
('FCDA Reception', 'FCDA reception checkpoint', 'Main Building, Ground Floor', 'REGISTRATION', 2, true),
('FCDA Exit', 'FCDA exit point', 'South Gate', 'EXIT', 2, true),
('Ministry Entrance', 'Main entrance to Ministry building', 'East Gate', 'ENTRY', 3, true),
('Ministry Exit', 'Exit from Ministry building', 'West Gate', 'EXIT', 3, true),
('AMAC Main Gate', 'Main entrance to AMAC', 'Front Gate', 'ENTRY', 4, true),
('AMAC Exit', 'Exit from AMAC complex', 'Back Gate', 'EXIT', 4, true),
('AGIS Entrance', 'Main entrance to AGIS building', 'Main Gate', 'ENTRY', 5, true),
('AGIS Reception', 'AGIS reception checkpoint', 'Lobby, Ground Floor', 'REGISTRATION', 5, true),
('AGIS Exit', 'Exit from AGIS building', 'Side Gate', 'EXIT', 5, true);

-- Insert Security Personnel (passwords are hashed with BCrypt - default password is "Security123#")
INSERT INTO security_personnel (first_name, last_name, badge_number, rank, email, phone_number, nin, facility_id, position, shift, is_active, username, password) VALUES
('Abdullahi', 'Musa', 'SEC-001', 'Chief Security Officer', 'abdullahi.musa@fct-security.gov.ng', '+2348022445501', '12345678901', 1, 'CSO', 'MORNING', true, 'abdullahi.musa', '$2a$10$wOzYRD3diVIYxcjm4nfRG.Cbnl6BwVIgEBMGYBz5HWel.URmMyUPC'),
('Binta', 'Aliyu', 'SEC-002', 'Deputy CSO', 'binta.aliyu@fct-security.gov.ng', '+2348022445502', '12345678902', 1, 'Deputy CSO', 'AFTERNOON', true, 'binta.aliyu', '$2a$10$wOzYRD3diVIYxcjm4nfRG.Cbnl6BwVIgEBMGYBz5HWel.URmMyUPC'),
('Chukwudi', 'Eze', 'SEC-003', 'Senior Security Officer', 'chukwudi.eze@fct-security.gov.ng', '+2348022445503', '12345678903', 1, 'SSO', 'NIGHT', true, 'chukwudi.eze', '$2a$10$wOzYRD3diVIYxcjm4nfRG.Cbnl6BwVIgEBMGYBz5HWel.URmMyUPC'),
('Danladi', 'Yusuf', 'SEC-004', 'Security Officer', 'danladi.yusuf@fct-security.gov.ng', '+2348022445504', '12345678904', 1, 'SO', 'MORNING', true, 'danladi.yusuf', '$2a$10$wOzYRD3diVIYxcjm4nfRG.Cbnl6BwVIgEBMGYBz5HWel.URmMyUPC'),
('Esther', 'Johnson', 'SEC-005', 'Security Officer', 'esther.johnson@fct-security.gov.ng', '+2348022445505', '12345678905', 1, 'SO', 'AFTERNOON', true, 'esther.johnson', '$2a$10$wOzYRD3diVIYxcjm4nfRG.Cbnl6BwVIgEBMGYBz5HWel.URmMyUPC'),
('Femi', 'Oladele', 'SEC-006', 'CSO', 'femi.oladele@fcda-security.gov.ng', '+2348022445506', '12345678906', 2, 'CSO', 'MORNING', true, 'femi.oladele', '$2a$10$wOzYRD3diVIYxcjm4nfRG.Cbnl6BwVIgEBMGYBz5HWel.URmMyUPC'),
('Grace', 'Udoh', 'SEC-007', 'Security Officer', 'grace.udoh@fcda-security.gov.ng', '+2348022445507', '12345678907', 2, 'SO', 'AFTERNOON', true, 'grace.udoh', '$2a$10$wOzYRD3diVIYxcjm4nfRG.Cbnl6BwVIgEBMGYBz5HWel.URmMyUPC'),
('Hassan', 'Ibrahim', 'SEC-008', 'CSO', 'hassan.ibrahim@mfct-security.gov.ng', '+2348022445508', '12345678908', 3, 'CSO', 'MORNING', true, 'hassan.ibrahim', '$2a$10$wOzYRD3diVIYxcjm4nfRG.Cbnl6BwVIgEBMGYBz5HWel.URmMyUPC'),
('Iyabo', 'Adeleke', 'SEC-009', 'CSO', 'iyabo.adeleke@amac-security.gov.ng', '+2348022445509', '12345678909', 4, 'CSO', 'MORNING', true, 'iyabo.adeleke', '$2a$10$wOzYRD3diVIYxcjm4nfRG.Cbnl6BwVIgEBMGYBz5HWel.URmMyUPC'),
('Joseph', 'Abiola', 'SEC-010', 'CSO', 'joseph.abiola@agis-security.gov.ng', '+2348022445510', '12345678910', 5, 'CSO', 'MORNING', true, 'joseph.abiola', '$2a$10$wOzYRD3diVIYxcjm4nfRG.Cbnl6BwVIgEBMGYBz5HWel.URmMyUPC');

-- Insert Response Teams
INSERT INTO response_teams (name, code, description, team_leader, contact_number, email, location, availability_status, is_active) VALUES
('Alpha Team', 'ALPHA-01', 'Primary response team for headquarters', 'Abdullahi Musa', '+2348022445501', 'alpha.team@fct-security.gov.ng', 'FCT HQ', 'AVAILABLE', true),
('Bravo Team', 'BRAVO-01', 'Secondary response team for headquarters', 'Binta Aliyu', '+2348022445502', 'bravo.team@fct-security.gov.ng', 'FCT HQ', 'AVAILABLE', true),
('Charlie Team', 'CHARLIE-01', 'Response team for FCDA', 'Femi Oladele', '+2348022445506', 'charlie.team@fcda-security.gov.ng', 'FCDA', 'AVAILABLE', true),
('Delta Team', 'DELTA-01', 'Response team for Ministry', 'Hassan Ibrahim', '+2348022445508', 'delta.team@mfct-security.gov.ng', 'Ministry', 'AVAILABLE', true),
('Echo Team', 'ECHO-01', 'Response team for AMAC', 'Iyabo Adeleke', '+2348022445509', 'echo.team@amac-security.gov.ng', 'AMAC', 'AVAILABLE', true);

-- Insert Response Team Members
INSERT INTO response_team_members (team_id, security_id) VALUES
(1, 1), -- Alpha Team: Abdullahi
(1, 3), -- Alpha Team: Chukwudi
(1, 4), -- Alpha Team: Danladi
(2, 2), -- Bravo Team: Binta
(2, 5), -- Bravo Team: Esther
(3, 6), -- Charlie Team: Femi
(3, 7), -- Charlie Team: Grace
(4, 8), -- Delta Team: Hassan
(5, 9); -- Echo Team: Iyabo

-- Insert Admins (passwords are hashed with BCrypt - default password is "Admin123#")
INSERT INTO admins (first_name, last_name, email, phone_number, username, password, role, department, is_active) VALUES
('Administrator', 'System', 'admin@fct.gov.ng', '+2348033334444', 'admin', '$2a$10$PgBl8bz9iHDqOIhZV.PaOOkz1l0xZ1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z', 'SUPER_ADMIN', 'IT', true),
('Michael', 'Adegoke', 'michael.adegoke@fct.gov.ng', '+2348033334445', 'michael.adegoke', '$2a$10$PgBl8bz9iHDqOIhZV.PaOOkz1l0xZ1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z', 'ADMIN', 'IT', true),
('Ngozi', 'Ibe', 'ngozi.ibe@fct.gov.ng', '+2348033334446', 'ngozi.ibe', '$2a$10$PgBl8bz9iHDqOIhZV.PaOOkz1l0xZ1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z', 'ADMIN', 'Administration', true);

-- Insert Roles
INSERT INTO roles (role_name, description, is_system_role) VALUES
('SUPER_ADMIN', 'Super Administrator with full system access', true),
('ADMIN', 'System Administrator', true),
('SECURITY_ADMIN', 'Security Administration Role', true),
('SECURITY_OFFICER', 'Security Officer Role', true),
('RESPONSE_TEAM', 'Security Response Team Member', true),
('GOVERNMENT_OFFICER', 'FCT Government Officer', true),
('RECEPTIONIST', 'Reception Staff for Visitor Processing', true);

-- Insert Permissions
INSERT INTO permissions (permission_name, description, module) VALUES
('MANAGE_USERS', 'Create, update, and delete users', 'ADMIN'),
('VIEW_REPORTS', 'View system reports', 'REPORTS'),
('MANAGE_FACILITIES', 'Create, update, and delete facilities', 'ADMIN'),
('MANAGE_OFFICERS', 'Create, update, and delete officers', 'ADMIN'),
('MANAGE_SECURITY', 'Create, update, and delete security personnel', 'SECURITY'),
('MANAGE_CHECKPOINTS', 'Create, update, and delete checkpoints', 'SECURITY'),
('SCAN_QR_CODES', 'Scan visitor QR codes', 'SECURITY'),
('CREATE_ALERTS', 'Create security alerts', 'SECURITY'),
('RESOLVE_ALERTS', 'Resolve security alerts', 'SECURITY'),
('MANAGE_VISITORS', 'Approve or reject visitor requests', 'VISITORS'),
('VIEW_VISITORS', 'View visitor information', 'VISITORS'),
('REGISTER_VISITORS', 'Register new visitors', 'VISITORS'),
('ALLOCATE_PARKING', 'Allocate parking spaces', 'PARKING'),
('MANAGE_PARKING', 'Manage parking spaces', 'PARKING'),
('SYSTEM_SETTINGS', 'Manage system settings', 'ADMIN'),
('EXPORT_DATA', 'Export system data', 'REPORTS');

-- Insert Role Permissions
-- Super Admin gets all permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 1, permission_id FROM permissions;

-- Admin gets most permissions but not security-specific ones
INSERT INTO role_permissions (role_id, permission_id)
SELECT 2, permission_id FROM permissions
WHERE permission_name NOT IN ('CREATE_ALERTS', 'RESOLVE_ALERTS', 'SCAN_QR_CODES');

-- Security Admin gets security-related permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 3, permission_id FROM permissions
WHERE module = 'SECURITY' OR permission_name IN ('VIEW_REPORTS', 'VIEW_VISITORS', 'ALLOCATE_PARKING');

-- Security Officer gets basic security permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 4, permission_id FROM permissions
WHERE permission_name IN ('SCAN_QR_CODES', 'CREATE_ALERTS', 'VIEW_VISITORS', 'ALLOCATE_PARKING');

-- Response Team gets alert-related permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 5, permission_id FROM permissions
WHERE permission_name IN ('VIEW_ALERTS', 'RESOLVE_ALERTS', 'VIEW_VISITORS');

-- Government Officer gets visitor-related permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 6, permission_id FROM permissions
WHERE permission_name IN ('MANAGE_VISITORS', 'VIEW_VISITORS', 'VIEW_REPORTS');

-- Receptionist gets visitor registration permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 7, permission_id FROM permissions
WHERE permission_name IN ('REGISTER_VISITORS', 'VIEW_VISITORS', 'SCAN_QR_CODES');

-- Insert System Settings
INSERT INTO system_settings (setting_key, setting_value, setting_group, is_public, description) VALUES
('system.name', 'FCT Visitation System', 'GENERAL', true, 'System Name'),
('system.version', '1.0.0', 'GENERAL', true, 'System Version'),
('system.contact.email', 'support@fctvisitation.gov.ng', 'CONTACT', true, 'Support Email Address'),
('system.contact.phone', '+234 9 123 4567', 'CONTACT', true, 'Support Phone Number'),
('security.max_failed_attempts', '5', 'SECURITY', false, 'Maximum Failed Login Attempts'),
('security.lockout_duration_minutes', '30', 'SECURITY', false, 'Account Lockout Duration in Minutes'),
('security.password_expiry_days', '90', 'SECURITY', false, 'Password Expiry in Days'),
('security.session_timeout_minutes', '30', 'SECURITY', false, 'Session Timeout in Minutes'),
('visitor.max_weekly_visits', '3', 'VISITOR', false, 'Maximum Visits Per Week for Same Visitor'),
('visitor.auto_approve', 'false', 'VISITOR', false, 'Auto-approve Visitor Requests'),
('visitor.qr_code_validity_hours', '24', 'VISITOR', false, 'QR Code Validity in Hours'),
('notification.email.enabled', 'true', 'NOTIFICATION', false, 'Enable Email Notifications'),
('notification.sms.enabled', 'true', 'NOTIFICATION', false, 'Enable SMS Notifications'),
('nimc.api.url', 'https://api.nimc.gov.ng/v1', 'INTEGRATION', false, 'NIMC API URL'),
('nimc.api.timeout_seconds', '30', 'INTEGRATION', false, 'NIMC API Timeout in Seconds'),
('parking.auto_allocation', 'false', 'PARKING', false, 'Auto-allocate Parking Spaces');

-- Insert Notification Templates
INSERT INTO notification_templates (template_code, template_type, subject, body, variables, is_active) VALUES
('VISITOR_REGISTRATION', 'EMAIL', 'FCT Visit Registration Confirmation', 'Dear {{visitor_name}},\n\nThank you for registering your visit to {{facility_name}}. Your visit has been scheduled for {{appointment_date}} at {{appointment_time}}.\n\nAppointment Details:\n- Confirmation Number: {{confirmation_number}}\n- Officer: {{officer_name}}\n- Purpose: {{purpose}}\n\nPlease present the attached QR code upon arrival at the security checkpoint.\n\nRegards,\nFCT Administration', '["visitor_name", "facility_name", "appointment_date", "appointment_time", "confirmation_number", "officer_name", "purpose"]', true),
('VISITOR_APPROVAL', 'EMAIL', 'FCT Visit Request Approved', 'Dear {{visitor_name}},\n\nYour visit request to {{facility_name}} has been approved. Your visit is scheduled for {{appointment_date}} at {{appointment_time}}.\n\nAppointment Details:\n- Confirmation Number: {{confirmation_number}}\n- Officer: {{officer_name}}\n- Purpose: {{purpose}}\n\nPlease present the attached QR code upon arrival at the security checkpoint.\n\nRegards,\nFCT Administration', '["visitor_name", "facility_name", "appointment_date", "appointment_time", "confirmation_number", "officer_name", "purpose"]', true),
('VISITOR_REJECTION', 'EMAIL', 'FCT Visit Request Declined', 'Dear {{visitor_name}},\n\nWe regret to inform you that your visit request to {{facility_name}} scheduled for {{appointment_date}} at {{appointment_time}} has been declined.\n\nReason: {{rejection_reason}}\n\nIf you believe this is an error or would like to reschedule, please contact the officer directly or submit a new request.\n\nRegards,\nFCT Administration', '["visitor_name", "facility_name", "appointment_date", "appointment_time", "rejection_reason"]', true),
('VISITOR_REMINDER', 'EMAIL', 'Reminder: Upcoming FCT Visit', 'Dear {{visitor_name}},\n\nThis is a reminder of your upcoming visit to {{facility_name}} scheduled for tomorrow, {{appointment_date}} at {{appointment_time}}.\n\nAppointment Details:\n- Confirmation Number: {{confirmation_number}}\n- Officer: {{officer_name}}\n- Purpose: {{purpose}}\n\nPlease remember to bring a valid ID and present your QR code at the security checkpoint.\n\nRegards,\nFCT Administration', '["visitor_name", "facility_name", "appointment_date", "appointment_time", "confirmation_number", "officer_name", "purpose"]', true),
('OFFICER_NOTIFICATION', 'EMAIL', 'New Visitor Appointment', 'Dear {{officer_name}},\n\nA new visit has been scheduled with you.\n\nVisitor Details:\n- Name: {{visitor_name}}\n- Email: {{visitor_email}}\n- Phone: {{visitor_phone}}\n- Date/Time: {{appointment_date}} at {{appointment_time}}\n- Purpose: {{purpose}}\n\nTo approve or decline this visit, please log in to the FCT Visitation System.\n\nRegards,\nFCT Administration', '["officer_name", "visitor_name", "visitor_email", "visitor_phone", "appointment_date", "appointment_time", "purpose"]', true),
('SECURITY_ALERT', 'EMAIL', 'Security Alert: {{alert_severity}}', 'SECURITY ALERT: {{alert_severity}}\n\nLocation: {{alert_location}}\nTime: {{alert_time}}\n\nDetails: {{alert_description}}\n\nThis alert requires immediate attention. Please follow security protocols.\n\nFCT Security Team', '["alert_severity", "alert_location", "alert_time", "alert_description"]', true),
('SECURITY_ALERT', 'SMS', 'FCT Security Alert', 'ALERT: {{alert_severity}} at {{alert_location}}. {{alert_time}}. {{alert_description}}', '["alert_severity", "alert_location", "alert_time", "alert_description"]', true),
('VISITOR_CHECKIN', 'SMS', 'FCT Visit Check-in', 'You have been checked in at {{checkpoint_name}} at {{checkin_time}}. Welcome to {{facility_name}}.', '["checkpoint_name", "checkin_time", "facility_name"]', true),
('VISITOR_CHECKOUT', 'SMS', 'FCT Visit Completed', 'Your visit to {{facility_name}} has been completed at {{checkout_time}}. Thank you for visiting.', '["facility_name", "checkout_time"]', true),
('PASSWORD_RESET', 'EMAIL', 'FCT Visitation System: Password Reset', 'Dear {{user_name}},\n\nA password reset has been requested for your account. Please click the link below to reset your password:\n\n{{reset_link}}\n\nIf you did not request this change, please contact the system administrator immediately.\n\nRegards,\nFCT Administration', '["user_name", "reset_link"]', true);

-- Insert Sample Parking Spaces
INSERT INTO parking_spaces (facility_id, location_code, description, space_type, status) VALUES
(1, 'A-001', 'Main Building Front Row', 'Regular', 'Available'),
(1, 'A-002', 'Main Building Front Row', 'Regular', 'Available'),
(1, 'A-- FCT Visitation System - Initial Data
-- Version: 1.0
-- Date: 2025-04-13

-- Disable foreign key checks temporarily for bulk loading
SET FOREIGN_KEY_CHECKS = 0;

-- Delete existing data for clean reinsertion
TRUNCATE TABLE facilities;
TRUNCATE TABLE officers;
TRUNCATE TABLE purpose_of_visit;
TRUNCATE TABLE checkpoints;
TRUNCATE TABLE security_personnel;
TRUNCATE TABLE response_teams;
TRUNCATE TABLE response_team_members;
TRUNCATE TABLE admins;
TRUNCATE TABLE roles;
TRUNCATE TABLE permissions;
TRUNCATE TABLE role_permissions;
TRUNCATE TABLE system_settings;
TRUNCATE TABLE notification_templates;

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

-- Insert Facilities
INSERT INTO facilities (name, code, address, description, contact_number, email, opening_time, closing_time, visitor_capacity, parking_capacity, is_active) VALUES
('FCT Administration Headquarters', 'FCT-HQ', 'Area 11, Garki, Abuja', 'Main FCT Administration Building', '+2348023456789', 'info@fct.gov.ng', '08:00:00', '17:00:00', 500, 200, true),
('FCDA Secretariat', 'FCDA-SEC', 'Area 3, Garki, Abuja', 'Federal Capital Development Authority Secretariat', '+2348023456790', 'info@fcda.gov.ng', '08:00:00', '17:00:00', 300, 150, true),
('Ministry of the Federal Capital', 'MFC-01', 'Area 10, Garki, Abuja', 'Ministry Headquarters', '+2348023456791', 'info@mfct.gov.ng', '08:00:00', '17:00:00', 250, 100, true),
('Abuja Municipal Area Council', 'AMAC-01', 'Garki, Abuja', 'AMAC Office Complex', '+2348023456792', 'info@amac.gov.ng', '08:00:00', '16:30:00', 200, 80, true),
('Abuja Geographic Information Systems', 'AGIS-01', 'Cadastral Zone, Abuja', 'AGIS Main Office', '+2348023456793', 'info@agis.gov.ng', '08:30:00', '16:30:00', 150, 50, true);

-- Insert Purposes of Visit
INSERT INTO purpose_of_visit (code, description, requires_approval, requires_id_verification, max_duration_minutes, is_active) VALUES
('MEETING', 'Official Meeting', false, true, 120, true),
('DOCUMENT', 'Document Submission/Collection', false, true, 60, true),
('INQUIRY', 'General Inquiry/Information', false, false, 30, true),
('INTERVIEW', 'Job Interview', true, true, 90, true),
('CONTRACT', 'Contract Discussion/Signing', true, true, 180, true),
('INSPECTION', 'Facility Inspection', true, true, 240, true),
('DELIVERY', 'Delivery of Items', false, true, 30, true),
('MAINTENANCE', 'Facility Maintenance', true, true, 300, true),
('CONFERENCE', 'Conference/Workshop', true, true, 480, true),
('OTHER', 'Other Purpose', true, true, 120, true);

-- Insert Departments
INSERT INTO departments (department_name, facility_id, description, is_active) VALUES
('Office of the Minister', 1, 'Office of the FCT Minister', true),
('Administration & Finance', 1, 'Administration and Finance Department', true),
('Urban Planning', 2, 'Urban Planning and Development Department', true),
('Environmental Services', 2, 'Environmental Services Department', true),
('Legal Services', 1, 'Legal Services Department', true),
('Infrastructure Development', 3, 'Infrastructure Development Department', true),
('Social Services', 3, 'Social Services Department', true),
('Transportation', 3, 'Transportation Department', true),
('Land Administration', 5, 'Land Administration Department', true),
('Public Relations', 1, 'Public Relations Department', true);

-- Insert Officers (passwords are hashed with BCrypt - default password is "Password123#")
INSERT INTO officers (first_name, last_name, email, phone_number, title, department_id, staff_id, facility_id, office_number, is_active, availability_status, max_visitors_per_day, username, password) VALUES
('Ahmed', 'Ibrahim', 'ahmed.ibrahim@fct.gov.ng', '+2348012345601', 'Director', 1, 'FCT-DIR-001', 1, 'A101', true, 'AVAILABLE', 10, 'ahmed.ibrahim', '$2a$10$xJwrX7KA.XPCqz9NeXk97OTbBe8j/o9.qlDcikXnyUGpuNeDH3gfO'),
('Blessing', 'Okonkwo', 'blessing.okonkwo@fct.gov.ng', '+2348012345602', 'Deputy Director', 2, 'FCT-DD-002', 1, 'A102', true, 'AVAILABLE', 8, 'blessing.okonkwo', '$2a$10$xJwrX7KA.XPCqz9NeXk97OTbBe8j/o9.qlDcikXnyUGpuNeDH3gfO'),
('Charles', 'Nnamdi', 'charles.nnamdi@fcda.gov.ng', '+2348012345603', 'Senior Planner', 3, 'FCDA-SP-001', 2, 'B201', true, 'AVAILABLE', 6, 'charles.nnamdi', '$2a$10$xJwrX7KA.XPCqz9NeXk97OTbBe8j/o9.qlDcikXnyUGpuNeDH3gfO'),
('Deborah', 'Adeyemi', 'deborah.adeyemi@fcda.gov.ng', '+2348012345604', 'Environmental Officer', 4, 'FCDA-EO-002', 2, 'B202', true, 'AVAILABLE', 6, 'deborah.adeyemi', '$2a$10$xJwrX7KA.XPCqz9NeXk97OTbBe8j/o9.qlDcikXnyUGpuNeDH3gfO'),
('Emmanuel', 'Babatunde', 'emmanuel.babatunde@fct.gov.ng', '+2348012345605', 'Legal Adviser', 5, 'FCT-LA-001', 1, 'A103', true, 'AVAILABLE', 5, 'emmanuel.babatunde', '$2a$10$xJwrX7KA.XPCqz9NeXk97OTbBe8j/o9.qlDcikXnyUGpuNeDH3gfO'),
('Fatima', 'Mohammed', 'fatima.mohammed@mfct.gov.ng', '+2348012345606', 'Infrastructure Director', 6, 'MFC-ID-001', 3, 'C301', true, 'AVAILABLE', 7, 'fatima.mohammed', '$2a$10$xJwrX7KA.XPCqz9NeXk97OTbBe8j/o9.qlDcikXnyUGpuNeDH3gfO'),
('George', 'Okafor', 'george.okafor@mfct.gov.ng', '+2348012345607', 'Social Services Coordinator', 7, 'MFC-SSC-002', 3, 'C302', true, 'AVAILABLE', 10, 'george.okafor', '$2a$10$xJwrX7KA.XPCqz9NeXk97OTbBe8j/o9.qlDcikXnyUGpuNeDH3gfO'),
('Hannah', 'Suleiman', 'hannah.suleiman@amac.gov.ng', '+2348012345608', 'Council Secretary', 8, 'AMAC-CS-001', 4, 'D401', true, 'AVAILABLE', 8, 'hannah.suleiman', '$2a$10$xJwrX7KA.XPCqz9NeXk97OTbBe8j/o9.qlDcikXnyUGpuNeDH3gfO'),
('Ibrahim', 'Kolade', 'ibrahim.kolade@agis.gov.ng', '+2348012345609', 'GIS Director', 9, 'AGIS-GD-001', 5, 'E501', true, 'AVAILABLE', 6, 'ibrahim.kolade', '$2a$10$xJwrX7KA.XPCqz9NeXk97OTbBe8j/o9.qlDcikXnyUGpuNeDH3gfO'),
('Janet', 'Usman', 'janet.usman@fct.gov.ng', '+2348012345610', 'Public Relations Officer', 10, 'FCT-PRO-001', 1, 'A104', true, 'AVAILABLE', 15, 'janet.usman', '$2a$10$xJwrX7KA.XPCqz9NeXk97OTbBe8j/o9.qlDcikXnyUGpuNeDH3gfO');

-- Insert Checkpoints
INSERT INTO checkpoints (name, description, location, checkpoint_type, facility_id, is_active) VALUES
('Main Entrance', 'Main entrance gate for all visitors', 'Front Gate, Ground Floor', 'ENTRY', 1, true),
('Reception Desk', 'Visitor registration desk at reception', 'Reception Area, Ground Floor', 'REGISTRATION', 1, true),
('Executive Floor', 'Access checkpoint for executive offices', '5th Floor Entrance', 'FACILITY', 1, true),
('Parking Entrance', 'Entrance to parking area', 'Basement Level 1', 'PARKING', 1, true),
('Exit Gate', 'Main exit point', 'Rear Gate, Ground Floor', 'EXIT', 1, true),
('FCDA Main Gate', 'Main entrance to FCDA complex', 'North Gate', 'ENTRY', 2, true),
('FCDA Reception', 'FCDA reception checkpoint', 'Main Building, Ground Floor', 'REGISTRATION', 2, true),
('FCDA Exit', 'FCDA exit point', 'South Gate', 'EXIT', 2, true),
('Ministry Entrance', 'Main entrance to Ministry building', 'East Gate', 'ENTRY', 3, true),
('Ministry Exit', 'Exit from Ministry building', 'West Gate', 'EXIT', 3, true),
('AMAC Main Gate', 'Main entrance to AMAC', 'Front Gate', 'ENTRY', 4, true),
('AMAC Exit', 'Exit from AMAC complex', 'Back Gate', 'EXIT', 4, true),
('AGIS Entrance', 'Main entrance to AGIS building', 'Main Gate', 'ENTRY', 5, true),
('AGIS Reception', 'AGIS reception checkpoint', 'Lobby, Ground Floor', 'REGISTRATION', 5, true),
('AGIS Exit', 'Exit from AGIS building', 'Side Gate', 'EXIT', 5, true);

-- Insert Security Personnel (passwords are hashed with BCrypt - default password is "Security123#")
INSERT INTO security_personnel (first_name, last_name, badge_number, rank, email, phone_number, nin, facility_id, position, shift, is_active, username, password) VALUES
('Abdullahi', 'Musa', 'SEC-001', 'Chief Security Officer', 'abdullahi.musa@fct-security.gov.ng', '+2348022445501', '12345678901', 1, 'CSO', 'MORNING', true, 'abdullahi.musa', '$2a$10$wOzYRD3diVIYxcjm4nfRG.Cbnl6BwVIgEBMGYBz5HWel.URmMyUPC'),
('Binta', 'Aliyu', 'SEC-002', 'Deputy CSO', 'binta.aliyu@fct-security.gov.ng', '+2348022445502', '12345678902', 1, 'Deputy CSO', 'AFTERNOON', true, 'binta.aliyu', '$2a$10$wOzYRD3diVIYxcjm4nfRG.Cbnl6BwVIgEBMGYBz5HWel.URmMyUPC'),
('Chukwudi', 'Eze', 'SEC-003', 'Senior Security Officer', 'chukwudi.eze@fct-security.gov.ng', '+2348022445503', '12345678903', 1, 'SSO', 'NIGHT', true, 'chukwudi.eze', '$2a$10$wOzYRD3diVIYxcjm4nfRG.Cbnl6BwVIgEBMGYBz5HWel.URmMyUPC'),
('Danladi', 'Yusuf', 'SEC-004', 'Security Officer', 'danladi.yusuf@fct-security.gov.ng', '+2348022445504', '12345678904', 1, 'SO', 'MORNING', true, 'danladi.yusuf', '$2a$10$wOzYRD3diVIYxcjm4nfRG.Cbnl6BwVIgEBMGYBz5HWel.URmMyUPC'),
('Esther', 'Johnson', 'SEC-005', 'Security Officer', 'esther.johnson@fct-security.gov.ng', '+2348022445505', '12345678905', 1, 'SO', 'AFTERNOON', true, 'esther.johnson', '$2a$10$wOzYRD3diVIYxcjm4nfRG.Cbnl6BwVIgEBMGYBz5HWel.URmMyUPC'),
('Femi', 'Oladele', 'SEC-006', 'CSO', 'femi.oladele@fcda-security.gov.ng', '+2348022445506', '12345678906', 2, 'CSO', 'MORNING', true, 'femi.oladele', '$2a$10$wOzYRD3diVIYxcjm4nfRG.Cbnl6BwVIgEBMGYBz5HWel.URmMyUPC'),
('Grace', 'Udoh', 'SEC-007', 'Security Officer', 'grace.udoh@fcda-security.gov.ng', '+2348022445507', '12345678907', 2, 'SO', 'AFTERNOON', true, 'grace.udoh', '$2a$10$wOzYRD3diVIYxcjm4nfRG.Cbnl6BwVIgEBMGYBz5HWel.URmMyUPC'),
('Hassan', 'Ibrahim', 'SEC-008', 'CSO', 'hassan.ibrahim@mfct-security.gov.ng', '+2348022445508', '12345678908', 3, 'CSO', 'MORNING', true, 'hassan.ibrahim', '$2a$10$wOzYRD3diVIYxcjm4nfRG.Cbnl6BwVIgEBMGYBz5HWel.URmMyUPC'),
('Iyabo', 'Adeleke', 'SEC-009', 'CSO', 'iyabo.adeleke@amac-security.gov.ng', '+2348022445509', '12345678909', 4, 'CSO', 'MORNING', true, 'iyabo.adeleke', '$2a$10$wOzYRD3diVIYxcjm4nfRG.Cbnl6BwVIgEBMGYBz5HWel.URmMyUPC'),
('Joseph', 'Abiola', 'SEC-010', 'CSO', 'joseph.abiola@agis-security.gov.ng', '+2348022445510', '12345678910', 5, 'CSO', 'MORNING', true, 'joseph.abiola', '$2a$10$wOzYRD3diVIYxcjm4nfRG.Cbnl6BwVIgEBMGYBz5HWel.URmMyUPC');

-- Insert Response Teams
INSERT INTO response_teams (name, code, description, team_leader, contact_number, email, location, availability_status, is_active) VALUES
('Alpha Team', 'ALPHA-01', 'Primary response team for headquarters', 'Abdullahi Musa', '+2348022445501', 'alpha.team@fct-security.gov.ng', 'FCT HQ', 'AVAILABLE', true),
('Bravo Team', 'BRAVO-01', 'Secondary response team for headquarters', 'Binta Aliyu', '+2348022445502', 'bravo.team@fct-security.gov.ng', 'FCT HQ', 'AVAILABLE', true),
('Charlie Team', 'CHARLIE-01', 'Response team for FCDA', 'Femi Oladele', '+2348022445506', 'charlie.team@fcda-security.gov.ng', 'FCDA', 'AVAILABLE', true),
('Delta Team', 'DELTA-01', 'Response team for Ministry', 'Hassan Ibrahim', '+2348022445508', 'delta.team@mfct-security.gov.ng', 'Ministry', 'AVAILABLE', true),
('Echo Team', 'ECHO-01', 'Response team for AMAC', 'Iyabo Adeleke', '+2348022445509', 'echo.team@amac-security.gov.ng', 'AMAC', 'AVAILABLE', true);

-- Insert Response Team Members
INSERT INTO response_team_members (team_id, security_id) VALUES
(1, 1), -- Alpha Team: Abdullahi
(1, 3), -- Alpha Team: Chukwudi
(1, 4), -- Alpha Team: Danladi
(2, 2), -- Bravo Team: Binta
(2, 5), -- Bravo Team: Esther
(3, 6), -- Charlie Team: Femi
(3, 7), -- Charlie Team: Grace
(4, 8), -- Delta Team: Hassan
(5, 9); -- Echo Team: Iyabo

-- Insert Admins (passwords are hashed with BCrypt - default password is "Admin123#")
INSERT INTO admins (first_name, last_name, email, phone_number, username, password, role, department, is_active) VALUES
('Administrator', 'System', 'admin@fct.gov.ng', '+2348033334444', 'admin', '$2a$10$PgBl8bz9iHDqOIhZV.PaOOkz1l0xZ1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z', 'SUPER_ADMIN', 'IT', true),
('Michael', 'Adegoke', 'michael.adegoke@fct.gov.ng', '+2348033334445', 'michael.adegoke', '$2a$10$PgBl8bz9iHDqOIhZV.PaOOkz1l0xZ1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z', 'ADMIN', 'IT', true),
('Ngozi', 'Ibe', 'ngozi.ibe@fct.gov.ng', '+2348033334446', 'ngozi.ibe', '$2a$10$PgBl8bz9iHDqOIhZV.PaOOkz1l0xZ1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z1Z', 'ADMIN', 'Administration', true);

-- Insert Roles
INSERT INTO roles (role_name, description, is_system_role) VALUES
('SUPER_ADMIN', 'Super Administrator with full system access', true),
('ADMIN', 'System Administrator', true),
('SECURITY_ADMIN', 'Security Administration Role', true),
('SECURITY_OFFICER', 'Security Officer Role', true),
('RESPONSE_TEAM', 'Security Response Team Member', true),
('GOVERNMENT_OFFICER', 'FCT Government Officer', true),
('RECEPTIONIST', 'Reception Staff for Visitor Processing', true);

-- Insert Permissions
INSERT INTO permissions (permission_name, description, module) VALUES
('MANAGE_USERS', 'Create, update, and delete users', 'ADMIN'),
('VIEW_REPORTS', 'View system reports', 'REPORTS'),
('MANAGE_FACILITIES', 'Create, update, and delete facilities', 'ADMIN'),
('MANAGE_OFFICERS', 'Create, update, and delete officers', 'ADMIN'),
('MANAGE_SECURITY', 'Create, update, and delete security personnel', 'SECURITY'),
('MANAGE_CHECKPOINTS', 'Create, update, and delete checkpoints', 'SECURITY'),
('SCAN_QR_CODES', 'Scan visitor QR codes', 'SECURITY'),
('CREATE_ALERTS', 'Create security alerts', 'SECURITY'),
('RESOLVE_ALERTS', 'Resolve security alerts', 'SECURITY'),
('MANAGE_VISITORS', 'Approve or reject visitor requests', 'VISITORS'),
('VIEW_VISITORS', 'View visitor information', 'VISITORS'),
('REGISTER_VISITORS', 'Register new visitors', 'VISITORS'),
('ALLOCATE_PARKING', 'Allocate parking spaces', 'PARKING'),
('MANAGE_PARKING', 'Manage parking spaces', 'PARKING'),
('SYSTEM_SETTINGS', 'Manage system settings', 'ADMIN'),
('EXPORT_DATA', 'Export system data', 'REPORTS');

-- Insert Role Permissions
-- Super Admin gets all permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 1, permission_id FROM permissions;

-- Admin gets most permissions but not security-specific ones
INSERT INTO role_permissions (role_id, permission_id)
SELECT 2, permission_id FROM permissions
WHERE permission_name NOT IN ('CREATE_ALERTS', 'RESOLVE_ALERTS', 'SCAN_QR_CODES');

-- Security Admin gets security-related permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 3, permission_id FROM permissions
WHERE module = 'SECURITY' OR permission_name IN ('VIEW_REPORTS', 'VIEW_VISITORS', 'ALLOCATE_PARKING');

-- Security Officer gets basic security permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 4, permission_id FROM permissions
WHERE permission_name IN ('SCAN_QR_CODES', 'CREATE_ALERTS', 'VIEW_VISITORS', 'ALLOCATE_PARKING');

-- Response Team gets alert-related permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 5, permission_id FROM permissions
WHERE permission_name IN ('VIEW_ALERTS', 'RESOLVE_ALERTS', 'VIEW_VISITORS');

-- Government Officer gets visitor-related permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 6, permission_id FROM permissions
WHERE permission_name IN ('MANAGE_VISITORS', 'VIEW_VISITORS', 'VIEW_REPORTS');

-- Receptionist gets visitor registration permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 7, permission_id FROM permissions
WHERE permission_name IN ('REGISTER_VISITORS', 'VIEW_VISITORS', 'SCAN_QR_CODES');

-- Insert System Settings
INSERT INTO system_settings (setting_key, setting_value, setting_group, is_public, description) VALUES
('system.name', 'FCT Visitation System', 'GENERAL', true, 'System Name'),
('system.version', '1.0.0', 'GENERAL', true, 'System Version'),
('system.contact.email', 'support@fctvisitation.gov.ng', 'CONTACT', true, 'Support Email Address'),
('system.contact.phone', '+234 9 123 4567', 'CONTACT', true, 'Support Phone Number'),
('security.max_failed_attempts', '5', 'SECURITY', false, 'Maximum Failed Login Attempts'),
('security.lockout_duration_minutes', '30', 'SECURITY', false, 'Account Lockout Duration in Minutes'),
('security.password_expiry_days', '90', 'SECURITY', false, 'Password Expiry in Days'),
('security.session_timeout_minutes', '30', 'SECURITY', false, 'Session Timeout in Minutes'),
('visitor.max_weekly_visits', '3', 'VISITOR', false, 'Maximum Visits Per Week for Same Visitor'),
('visitor.auto_approve', 'false', 'VISITOR', false, 'Auto-approve Visitor Requests'),
('visitor.qr_code_validity_hours', '24', 'VISITOR', false, 'QR Code Validity in Hours'),
('notification.email.enabled', 'true', 'NOTIFICATION', false, 'Enable Email Notifications'),
('notification.sms.enabled', 'true', 'NOTIFICATION', false, 'Enable SMS Notifications'),
('nimc.api.url', 'https://api.nimc.gov.ng/v1', 'INTEGRATION', false, 'NIMC API URL'),
('nimc.api.timeout_seconds', '30', 'INTEGRATION', false, 'NIMC API Timeout in Seconds'),
('parking.auto_allocation', 'false', 'PARKING', false, 'Auto-allocate Parking Spaces');

-- Insert Notification Templates
INSERT INTO notification_templates (template_code, template_type, subject, body, variables, is_active) VALUES
('VISITOR_REGISTRATION', 'EMAIL', 'FCT Visit Registration Confirmation', 'Dear {{visitor_name}},\n\nThank you for registering your visit to {{facility_name}}. Your visit has been scheduled for {{appointment_date}} at {{appointment_time}}.\n\nAppointment Details:\n- Confirmation Number: {{confirmation_number}}\n- Officer: {{officer_name}}\n- Purpose: {{purpose}}\n\nPlease present the attached QR code upon arrival at the security checkpoint.\n\nRegards,\nFCT Administration', '["visitor_name", "facility_name", "appointment_date", "appointment_time", "confirmation_number", "officer_name", "purpose"]', true),
('VISITOR_APPROVAL', 'EMAIL', 'FCT Visit Request Approved', 'Dear {{visitor_name}},\n\nYour visit request to {{facility_name}} has been approved. Your visit is scheduled for {{appointment_date}} at {{appointment_time}}.\n\nAppointment Details:\n- Confirmation Number: {{confirmation_number}}\n- Officer: {{officer_name}}\n- Purpose: {{purpose}}\n\nPlease present the attached QR code upon arrival at the security checkpoint.\n\nRegards,\nFCT Administration', '["visitor_name", "facility_name", "appointment_date", "appointment_time", "confirmation_number", "officer_name", "purpose"]', true),
('VISITOR_REJECTION', 'EMAIL', 'FCT Visit Request Declined', 'Dear {{visitor_name}},\n\nWe regret to inform you that your visit request to {{facility_name}} scheduled for {{appointment_date}} at {{appointment_time}} has been declined.\n\nReason: {{rejection_reason}}\n\nIf you believe this is an error or would like to reschedule, please contact the officer directly or submit a new request.\n\nRegards,\nFCT Administration', '["visitor_name", "facility_name", "appointment_date", "appointment_time", "rejection_reason"]', true),
('VISITOR_REMINDER', 'EMAIL', 'Reminder: Upcoming FCT Visit', 'Dear {{visitor_name}},\n\nThis is a reminder of your upcoming visit to {{facility_name}} scheduled for tomorrow, {{appointment_date}} at {{appointment_time}}.\n\nAppointment Details:\n- Confirmation Number: {{confirmation_number}}\n- Officer: {{officer_name}}\n- Purpose: {{purpose}}\n\nPlease remember to bring a valid ID and present your QR code at the security checkpoint.\n\nRegards,\nFCT Administration', '["visitor_name", "facility_name", "appointment_date", "appointment_time", "confirmation_number", "officer_name", "purpose"]', true),
('OFFICER_NOTIFICATION', 'EMAIL', 'New Visitor Appointment', 'Dear {{officer_name}},\n\nA new visit has been scheduled with you.\n\nVisitor Details:\n- Name: {{visitor_name}}\n- Email: {{visitor_email}}\n- Phone: {{visitor_phone}}\n- Date/Time: {{appointment_date}} at {{appointment_time}}\n- Purpose: {{purpose}}\n\nTo approve or decline this visit, please log in to the FCT Visitation System.\n\nRegards,\nFCT Administration', '["officer_name", "visitor_name", "visitor_email", "visitor_phone", "appointment_date", "appointment_time", "purpose"]', true),
('SECURITY_ALERT', 'EMAIL', 'Security Alert: {{alert_severity}}', 'SECURITY ALERT: {{alert_severity}}\n\nLocation: {{alert_location}}\nTime: {{alert_time}}\n\nDetails: {{alert_description}}\n\nThis alert requires immediate attention. Please follow security protocols.\n\nFCT Security Team', '["alert_severity", "alert_location", "alert_time", "alert_description"]', true),
('SECURITY_ALERT', 'SMS', 'FCT Security Alert', 'ALERT: {{alert_severity}} at {{alert_location}}. {{alert_time}}. {{alert_description}}', '["alert_severity", "alert_location", "alert_time", "alert_description"]', true),
('VISITOR_CHECKIN', 'SMS', 'FCT Visit Check-in', 'You have been checked in at {{checkpoint_name}} at {{checkin_time}}. Welcome to {{facility_name}}.', '["checkpoint_name", "checkin_time", "facility_name"]', true),
('VISITOR_CHECKOUT', 'SMS', 'FCT Visit Completed', 'Your visit to {{facility_name}} has been completed at {{checkout_time}}. Thank you for visiting.', '["facility_name", "checkout_time"]', true),
('PASSWORD_RESET', 'EMAIL', 'FCT Visitation System: Password Reset', 'Dear {{user_name}},\n\nA password reset has been requested for your account. Please click the link below to reset your password:\n\n{{reset_link}}\n\nIf you did not request this change, please contact the system administrator immediately.\n\nRegards,\nFCT Administration', '["user_name", "reset_link"]', true);

-- Insert Sample Parking Spaces (continued)
INSERT INTO parking_spaces (facility_id, location_code, description, space_type, status) VALUES
(1, 'A-003', 'Main Building Front Row', 'Regular', 'Available'),
(1, 'A-004', 'Main Building Front Row', 'Regular', 'Available'),
(1, 'A-005', 'Main Building Front Row', 'Regular', 'Available'),
(1, 'A-101', 'Main Building VIP Section', 'VIP', 'Available'),
(1, 'A-102', 'Main Building VIP Section', 'VIP', 'Available'),
(1, 'A-103', 'Main Building VIP Section', 'VIP', 'Available'),
(1, 'B-001', 'Side Entrance Area', 'Regular', 'Available'),
(1, 'B-002', 'Side Entrance Area', 'Regular', 'Available'),
(2, 'C-001', 'FCDA Main Entrance', 'Regular', 'Available'),
(2, 'C-002', 'FCDA Main Entrance', 'Regular', 'Available'),
(2, 'C-003', 'FCDA Main Entrance', 'Regular', 'Available'),
(2, 'C-101', 'FCDA Director Section', 'Reserved', 'Available'),
(2, 'C-102', 'FCDA Director Section', 'Reserved', 'Available'),
(3, 'D-001', 'Ministry Front Parking', 'Regular', 'Available'),
(3, 'D-002', 'Ministry Front Parking', 'Regular', 'Available'),
(3, 'D-101', 'Ministry Executive Section', 'VIP', 'Available'),
(4, 'E-001', 'AMAC Visitor Section', 'Regular', 'Available'),
(4, 'E-002', 'AMAC Visitor Section', 'Regular', 'Available'),
(5, 'F-001', 'AGIS Visitor Parking', 'Regular', 'Available'),
(5, 'F-002', 'AGIS Visitor Parking', 'Regular', 'Available');

-- Insert Sample Incident Alerts
INSERT INTO incident_alerts (title, description, location, severity, status, reported_by_id, reported_at, assigned_team_id) VALUES
('Suspicious Package', 'Unattended package found near reception area', 'Main Building Reception', 'MEDIUM', 'ACTIVE', 1, DATE_SUB(NOW(), INTERVAL 3 HOUR), 1),
('Unauthorized Access Attempt', 'Individual attempted to bypass security checkpoint', 'FCDA North Gate', 'HIGH', 'ACTIVE', 6, DATE_SUB(NOW(), INTERVAL 2 HOUR), 3),
('Visitor Altercation', 'Verbal altercation between visitors in waiting area', 'Main Building Lobby', 'LOW', 'ACTIVE', 5, DATE_SUB(NOW(), INTERVAL 1 HOUR), 2);

-- Insert Sample Resolved Alerts
INSERT INTO incident_alerts (title, description, location, severity, status, reported_by_id, reported_at, assigned_team_id, resolved_by_id, resolved_at, resolution_notes) VALUES
('Security Badge Found', 'Security badge found in parking area', 'Main Building Parking', 'LOW', 'RESOLVED', 2, DATE_SUB(NOW(), INTERVAL 8 HOUR), 1, 3, DATE_SUB(NOW(), INTERVAL 7 HOUR), 'Badge returned to owner, verified identity'),
('Fire Alarm Test', 'Scheduled fire alarm test', 'FCDA Building', 'MEDIUM', 'RESOLVED', 7, DATE_SUB(NOW(), INTERVAL 10 HOUR), 3, 6, DATE_SUB(NOW(), INTERVAL 9 HOUR), 'Test completed successfully, all systems functioning properly'),
('Visitor Assistance', 'Elderly visitor requiring medical assistance', 'Ministry Entrance', 'MEDIUM', 'RESOLVED', 8, DATE_SUB(NOW(), INTERVAL 6 HOUR), 4, 8, DATE_SUB(NOW(), INTERVAL 5 HOUR), 'Medical assistance provided, visitor stabilized and transported to hospital');

-- Insert Scheduled Jobs
INSERT INTO scheduled_jobs (job_name, job_description, cron_expression, job_class, job_data, is_active) VALUES
('VisitorReminderJob', 'Send reminders to visitors about upcoming appointments', '0 10 * * *', 'com.fct.visitation.jobs.VisitorReminderJob', '{}', true),
('QRCodeCleanupJob', 'Clean up expired QR codes', '0 0 1 * * *', 'com.fct.visitation.jobs.QRCodeCleanupJob', '{}', true),
('VisitorStatusUpdateJob', 'Update status of visitors with missed appointments', '0 22 * * *', 'com.fct.visitation.jobs.VisitorStatusUpdateJob', '{}', true),
('SecurityReportJob', 'Generate daily security reports', '0 23 * * *', 'com.fct.visitation.jobs.SecurityReportJob', '{}', true),
('DatabaseBackupJob', 'Perform database backup', '0 2 * * *', 'com.fct.visitation.jobs.DatabaseBackupJob', '{}', true),
('SystemMetricsJob', 'Collect system performance metrics', '0 */4 * * *', 'com.fct.visitation.jobs.SystemMetricsJob', '{}', true),
('NINVerificationCleanupJob', 'Clean up expired NIN verifications', '0 3 * * 0', 'com.fct.visitation.jobs.NINVerificationCleanupJob', '{"retentionDays": 90}', true);

-- Insert Sample NIN Verifications (Valid NINs)
INSERT INTO nin_verifications (nin, first_name, last_name, middle_name, gender, date_of_birth, phone_number, address, is_valid, verification_date, expiry_date) VALUES
('12345678901', 'Adamu', 'Mohammed', 'Ibrahim', 'Male', '1980-05-15', '08012345678', 'No 5 Wuse Zone 4, Abuja', true, DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_ADD(NOW(), INTERVAL 20 DAY)),
('12345678902', 'Amina', 'Yusuf', 'Bello', 'Female', '1985-08-22', '08023456789', 'Plot 16 Garki Area 11, Abuja', true, DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_ADD(NOW(), INTERVAL 25 DAY)),
('12345678903', 'Emmanuel', 'Okoye', 'Chukwudi', 'Male', '1975-11-30', '08034567890', 'Flat 4, Block B, Gwarinpa Estate, Abuja', true, DATE_SUB(NOW(), INTERVAL 15 DAY), DATE_ADD(NOW(), INTERVAL 15 DAY)),
('12345678904', 'Blessing', 'Adebayo', 'Folake', 'Female', '1990-02-10', '08045678901', '25 Mississippi Street, Maitama, Abuja', true, DATE_SUB(NOW(), INTERVAL 7 DAY), DATE_ADD(NOW(), INTERVAL 23 DAY)),
('12345678905', 'Ibrahim', 'Musa', 'Abdullahi', 'Male', '1982-09-05', '08056789012', 'House 10, Road 5, Asokoro, Abuja', true, DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_ADD(NOW(), INTERVAL 27 DAY));

-- Insert Sample Visitors for Testing
INSERT INTO visitors (first_name, last_name, email, phone_number, nin, address, car_type, facility_id, officer_id, purpose_id, appointment_datetime, status, qr_code) VALUES
('Ahmed', 'Suleiman', 'ahmed.suleiman@example.com', '08011223344', '12345678901', 'No 10 Wuse Zone 5, Abuja', 'Own', 1, 1, 1, DATE_ADD(NOW(), INTERVAL 1 DAY), 'Pending', 'VS-001-ABC123XYZ'),
('Fatima', 'Bello', 'fatima.bello@example.com', '08022334455', '12345678902', 'Plot 20 Utako District, Abuja', 'None', 1, 2, 2, DATE_ADD(NOW(), INTERVAL 1 DAY), 'Pending', 'VS-002-DEF456UVW'),
('James', 'Okafor', 'james.okafor@example.com', '08033445566', '12345678903', '15 Danube Street, Maitama, Abuja', 'Rented', 2, 3, 3, DATE_ADD(NOW(), INTERVAL 2 DAY), 'Pending', 'VS-003-GHI789RST'),
('Grace', 'Adeleke', 'grace.adeleke@example.com', '08044556677', '12345678904', 'Block 5, Flat 3, Gwarinpa Estate, Abuja', 'None', 3, 6, 5, DATE_ADD(NOW(), INTERVAL 3 DAY), 'Pending', 'VS-004-JKL012MNO'),
('Ibrahim', 'Hassan', 'ibrahim.hassan@example.com', '08055667788', '12345678905', 'House 7, Road 2, Asokoro, Abuja', 'Own', 4, 8, 1, DATE_ADD(NOW(), INTERVAL 4 DAY), 'Pending', 'VS-005-PQR345STU');

-- Insert Sample Car Details
INSERT INTO car_details (visitor_id, car_type, registration_number, model, color) VALUES
(1, 'Own', 'ABC-123-XYZ', 'Toyota Camry', 'Silver'),
(3, 'Rented', 'DEF-456-UVW', 'Honda Accord', 'Black'),
(5, 'Own', 'GHI-789-RST', 'Kia Sportage', 'White');

-- Insert Sample Rented Car Drivers
INSERT INTO rented_car_drivers (visitor_id, name, nin, license_number, contact_number, verification_status) VALUES
(3, 'Musa Ibrahim', '12345678906', 'DL-12345678', '08099887766', 'Pending');

-- Insert Sample Activity Logs
INSERT INTO activity_logs (user_id, user_type, action, entity_type, entity_id, details, ip_address) VALUES
(1, 'ADMIN', 'LOGIN', 'USER', 1, 'Admin user logged in', '192.168.1.100'),
(1, 'ADMIN', 'CREATE', 'FACILITY', 1, 'Created new facility: FCT Administration Headquarters', '192.168.1.100'),
(2, 'ADMIN', 'UPDATE', 'SYSTEM_SETTING', NULL, 'Updated system settings', '192.168.1.101'),
(3, 'ADMIN', 'CREATE', 'OFFICER', 1, 'Created new officer: Ahmed Ibrahim', '192.168.1.102'),
(1, 'SECURITY', 'CREATE', 'ALERT', 1, 'Created security alert: Suspicious Package', '192.168.1.103'),
(6, 'SECURITY', 'CREATE', 'ALERT', 2, 'Created security alert: Unauthorized Access Attempt', '192.168.1.104'),
(8, 'SECURITY', 'UPDATE', 'ALERT', 6, 'Resolved security alert: Fire Alarm Test', '192.168.1.105'),
(1, 'ADMIN', 'CREATE', 'CHECKPOINT', 1, 'Created new checkpoint: Main Entrance', '192.168.1.100');

-- Comments

-- This script inserts sample data for the FCT Visitation System.
-- It includes:
-- - Sample parking spaces for all facilities
-- - Active and resolved security incident alerts
-- - Scheduled jobs for system maintenance and operations
-- - Valid NIN verification records
-- - Sample visitors with appointments
-- - Car details and rented car drivers
-- - System activity logs for audit trail

-- The data is designed to provide a realistic testing environment with
-- appropriate relationships between entities. All passwords are hashed
-- using BCrypt.

-- In a production environment, this script should be modified to include
-- actual data for the FCT facilities and personnel.