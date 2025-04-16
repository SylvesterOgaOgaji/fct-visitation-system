package com.fct.visitation.controllers;

import com.fct.visitation.models.dto.CheckpointScanRequest;
import com.fct.visitation.models.dto.CheckpointScanResponse;
import com.fct.visitation.models.entity.Checkpoint;
import com.fct.visitation.models.entity.QRScanLog;
import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.services.interfaces.CheckpointService;
import com.fct.visitation.services.interfaces.QRScanLogService;
import com.fct.visitation.services.interfaces.SecurityService;
import com.fct.visitation.services.interfaces.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller for managing checkpoints and QR code scanning operations
 */
@Controller
@RequestMapping("/checkpoint")
public class CheckpointController {

    private final CheckpointService checkpointService;
    private final QRScanLogService qrScanLogService;
    private final VisitorService visitorService;
    private final SecurityService securityService;

    @Autowired
    public CheckpointController(
            CheckpointService checkpointService,
            QRScanLogService qrScanLogService,
            VisitorService visitorService,
            SecurityService securityService) {
        this.checkpointService = checkpointService;
        this.qrScanLogService = qrScanLogService;
        this.visitorService = visitorService;
        this.securityService = securityService;
    }

    /**
     * Display the checkpoint scanner page
     */
    @GetMapping("/scan")
    @PreAuthorize("hasAnyRole('SECURITY_PERSONNEL', 'ADMIN')")
    public String scanPage(Model model, Principal principal) {
        List<Checkpoint> checkpoints = checkpointService.getAllCheckpoints();
        model.addAttribute("checkpoints", checkpoints);
        model.addAttribute("securityOfficer", principal.getName());
        return "security/scan";
    }

    /**
     * Process a QR code scan
     */
    @PostMapping("/scan")
    @PreAuthorize("hasAnyRole('SECURITY_PERSONNEL', 'ADMIN')")
    @ResponseBody
    public ResponseEntity<CheckpointScanResponse> processScan(@RequestBody CheckpointScanRequest scanRequest, Principal principal) {
        CheckpointScanResponse response = new CheckpointScanResponse();
        
        try {
            // Validate the QR code
            Visitor visitor = visitorService.findVisitorByQRCode(scanRequest.getQrCode());
            
            if (visitor == null) {
                response.setSuccess(false);
                response.setMessage("Invalid QR code.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            // Check if the visitor's appointment is today
            if (!visitor.getAppointmentDatetime().toLocalDate().equals(LocalDateTime.now().toLocalDate())) {
                response.setSuccess(false);
                response.setMessage("This QR code is not valid for today.");
                
                // Log the invalid scan attempt
                securityService.logSecurityEvent(
                    "Invalid QR scan attempt", 
                    "QR code for " + visitor.getFirstName() + " " + visitor.getLastName() + 
                    " was scanned at checkpoint " + scanRequest.getCheckpointId() + 
                    " but is not valid for today.", 
                    "MEDIUM"
                );
                
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            // Get the checkpoint
            Checkpoint checkpoint = checkpointService.getCheckpointById(scanRequest.getCheckpointId());
            
            if (checkpoint == null) {
                response.setSuccess(false);
                response.setMessage("Invalid checkpoint.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            
            // Record the scan
            QRScanLog scanLog = new QRScanLog();
            scanLog.setVisitor(visitor);
            scanLog.setCheckpoint(checkpoint);
            scanLog.setScannedAt(LocalDateTime.now());
            scanLog.setScannedBy(principal.getName());
            qrScanLogService.saveQRScanLog(scanLog);
            
            // Update visitor status based on checkpoint
            switch (checkpoint.getCheckpointType()) {
                case "ENTRY":
                    visitor.setStatus("Checked-in");
                    break;
                case "FACILITY":
                    visitor.setStatus("In-Meeting");
                    break;
                case "EXIT":
                    visitor.setStatus("Completed");
                    break;
                default:
                    // No status change for other checkpoint types
                    break;
            }
            
            visitorService.saveVisitor(visitor);
            
            // Build the response
            response.setSuccess(true);
            response.setMessage("QR code scanned successfully");
            response.setVisitorName(visitor.getFirstName() + " " + visitor.getLastName());
            response.setVisitorPurpose(visitor.getPurposeOfVisit().getDescription());
            response.setVisitorStatus(visitor.getStatus());
            response.setDestinationFacility(visitor.getFacility().getName());
            response.setOfficerToMeet(visitor.getOfficer().getFirstName() + " " + visitor.getOfficer().getLastName());
            
            // Check if visitor has a vehicle
            if (visitor.getCarType() != null && !visitor.getCarType().equals("None")) {
                response.setHasVehicle(true);
                response.setVehicleInfo("Vehicle details available");
            } else {
                response.setHasVehicle(false);
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Error processing scan: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Display checkpoint management page
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String checkpointManagementPage(Model model) {
        List<Checkpoint> checkpoints = checkpointService.getAllCheckpoints();
        model.addAttribute("checkpoints", checkpoints);
        model.addAttribute("newCheckpoint", new Checkpoint());
        return "admin/checkpoints";
    }

    /**
     * Create a new checkpoint
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String createCheckpoint(@ModelAttribute Checkpoint checkpoint) {
        checkpointService.saveCheckpoint(checkpoint);
        return "redirect:/checkpoint";
    }

    /**
     * Delete a checkpoint
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCheckpoint(@PathVariable Long id) {
        try {
            checkpointService.deleteCheckpoint(id);
            return ResponseEntity.ok("Checkpoint deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting checkpoint: " + e.getMessage());
        }
    }

    /**
     * Get scan history for a specific checkpoint
     */
    @GetMapping("/{id}/history")
    @PreAuthorize("hasAnyRole('SECURITY_PERSONNEL', 'ADMIN')")
    public String checkpointHistory(@PathVariable Long id, Model model) {
        Checkpoint checkpoint = checkpointService.getCheckpointById(id);
        if (checkpoint == null) {
            return "redirect:/checkpoint";
        }
        
        List<QRScanLog> scanLogs = qrScanLogService.getScanLogsByCheckpoint(checkpoint);
        model.addAttribute("checkpoint", checkpoint);
        model.addAttribute("scanLogs", scanLogs);
        return "security/checkpoint-history";
    }
}