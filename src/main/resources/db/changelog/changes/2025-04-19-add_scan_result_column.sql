-- Add scan_result column to qr_scan_log table
ALTER TABLE qr_scan_log
ADD COLUMN scan_result VARCHAR(100);

-- Update existing records with a default value
UPDATE qr_scan_log 
SET scan_result = 'SUCCESS' 
WHERE scan_result IS NULL;