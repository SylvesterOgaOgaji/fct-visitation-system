-- Facilities
INSERT INTO facilities (facility_name, address, contact_number, email) VALUES
('Ministry of Finance', 'Plot 123, Central Business District, Abuja', '08012345678', 'info@finance.fct.gov.ng'),
('Ministry of Health', 'Plot 456, Garki Area 11, Abuja', '08023456789', 'info@health.fct.gov.ng'),
('Office of the FCT Minister', 'FCT Secretariat Complex, Area 11, Garki', '08034567890', 'minister@fct.gov.ng'),
('Department of Development Control', 'Wuse Zone 6, Abuja', '08045678901', 'info@development.fct.gov.ng');

-- Officers
INSERT INTO officers (facility_id, name, position, department, duty_status, email, phone_number) VALUES
(1, 'Ahmed Ibrahim', 'Director', 'Budget Office', 'ON_DUTY', 'a.ibrahim@finance.fct.gov.ng', '08012345600'),
(1, 'Sarah Okonkwo', 'Deputy Director', 'Accounts', 'ON_DUTY', 's.okonkwo@finance.fct.gov.ng', '08012345601'),
(2, 'Dr. Emmanuel Adewale', 'Director', 'Public Health', 'ON_DUTY', 'e.adewale@health.fct.gov.ng', '08023456700'),
(2, 'Dr. Fatima Suleiman', 'Medical Officer', 'Primary Healthcare', 'ON_DUTY', 'f.suleiman@health.fct.gov.ng', '08023456701'),
(3, 'John Okafor', 'Chief of Staff', 'Minister''s Office', 'ON_DUTY', 'j.okafor@fct.gov.ng', '08034567800'),
(4, 'Blessing Nnamdi', 'Director', 'Approvals', 'ON_DUTY', 'b.nnamdi@development.fct.gov.ng', '08045678900');

-- Purpose of Visit
INSERT INTO purpose_of_visit (facility_id, description) VALUES
(1, 'Budget Inquiry'),
(1, 'Payment Processing'),
(1, 'Financial Consultation'),
(2, 'Medical Consultation'),
(2, 'Health Policy Discussion'),
(3, 'Official Meeting'),
(3, 'Press Briefing'),
(4, 'Building Approval'),
(4, 'Zoning Information');

-- Security Personnel
INSERT INTO security_personnel (name, username, password_hash, role, contact_number) VALUES
('Adekunle Ogunleye', 'aogunleye', '$2a$10$EqKCjGBKQHSQwi8X7v/5p.Tl9.CZpSjf4eP4ILrVr0gMUBDW2v87W', 'SUPERVISOR', '08056789012'), -- password: security123
('Grace Johnson', 'gjohnson', '$2a$10$8A/R8kg1cTuJpG7oWV20JOX7Kx8e9KrRGN1M19vZKyudQiloUG2vW', 'GUARD', '08056789013'), -- password: guard123
('Michael Taiwo', 'mtaiwo', '$2a$10$HF87t1cO2WM1q0NUFDs.ReOEPAC8XpVZa8/NZ/Md6WpG84tJSTxny', 'GUARD', '08056789014'); -- password: guard123

-- Response Teams
INSERT INTO response_teams (team_name, contact_number, email) VALUES
('Alpha Team', '08067890123', 'alpha@security.fct.gov.ng'),
('Bravo Team', '08067890124', 'bravo@security.fct.gov.ng'),
('Charlie Team', '08067890125', 'charlie@security.fct.gov.ng');

-- Admin Users
INSERT INTO admins (facility_id, username, password_hash, role, name, email, contact_number) VALUES
(1, 'admin', '$2a$10$h.dl5J86rGH7I8bD9bZeZeci0pDt0.VwvxhvgE4Lm4DA.ZIn0UZHK', 'ADMIN', 'System Administrator', 'admin@fct.gov.ng', '08078901234'), -- password: admin123
(2, 'healthadmin', '$2a$10$m.Od.ImZYfLoq0Z5vBzieegGmeaWMHG33LeIooKh.WFEWVoQXJLFC', 'SUB_ADMIN', 'Health Admin', 'healthadmin@fct.gov.ng', '08078901235'), -- password: health123
(3, 'ministeradmin', '$2a$10$XIqYjY8a/FWCYeJF/PVJseXZQzQk/7uqXxE2fAiVjpKrJcY9xAUfW', 'SUB_ADMIN', 'Minister Office Admin', 'ministeradmin@fct.gov.ng', '08078901236'); -- password: minister123

-- Parking Spaces
INSERT INTO parking_spaces (facility_id, location_code, description, space_type, status) VALUES
(1, 'F1-A01', 'Ground Floor, Section A, Spot 01', 'REGULAR', 'AVAILABLE'),
(1, 'F1-A02', 'Ground Floor, Section A, Spot 02', 'REGULAR', 'AVAILABLE'),
(1, 'F1-V01', 'Ground Floor, VIP Section, Spot 01', 'VIP', 'AVAILABLE'),
(2, 'F2-A01', 'Main Entrance, Spot 01', 'REGULAR', 'AVAILABLE'),
(2, 'F2-A02', 'Main Entrance, Spot 02', 'REGULAR', 'AVAILABLE'),
(3, 'F3-V01', 'Minister Entrance, VIP Spot 01', 'VIP', 'AVAILABLE'),
(3, 'F3-V02', 'Minister Entrance, VIP Spot 02', 'VIP', 'AVAILABLE'),
(4, 'F4-A01', 'Visitor Entrance, Spot 01', 'REGULAR', 'AVAILABLE'),
(4, 'F4-A02', 'Visitor Entrance, Spot 02', 'REGULAR', 'AVAILABLE');

-- Checkpoints
INSERT INTO checkpoints (location_name, description) VALUES
('Main Gate', 'Facility entrance security checkpoint'),
('Reception', 'Main building reception desk'),
('Admin Block', 'Administrative building entrance'),
('Executive Wing', 'Executive offices area entrance'),
('Parking Entrance', 'Entrance to the parking area'),
('Exit Gate', 'Main exit point');
