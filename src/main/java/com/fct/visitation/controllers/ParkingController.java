package com.fct.visitation.controllers;

import com.fct.visitation.models.dto.ParkingAllocationRequest;
import com.fct.visitation.models.dto.ParkingResponse;
import com.fct.visitation.models.entity.CarDetails;
import com.fct.visitation.models.entity.Facility;
import com.fct.visitation.models.entity.ParkingSpace;
import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.services.interfaces.FacilityService;
import com.fct.visitation.services.interfaces.ParkingService;
import com.fct.visitation.services.interfaces.VehicleService;
import com.fct.visitation.services.interfaces.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Controller for managing parking spaces and allocations
 */
@Controller
@RequestMapping("/parking")
public class ParkingController {

    private final ParkingService parkingService;
    private final FacilityService facilityService;
    private final VisitorService visitorService;
    private final VehicleService vehicleService;

    @Autowired
    public ParkingController(
            ParkingService parkingService,
            FacilityService facilityService,
            VisitorService visitorService,
            VehicleService vehicleService) {
        this.parkingService = parkingService;
        this.facilityService = facilityService;
        this.visitorService = visitorService;
        this.vehicleService = vehicleService;
    }

    /**
     * Display parking management dashboard
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('SECURITY_PERSONNEL', 'ADMIN')")
    public String parkingDashboard(Model model) {
        List<ParkingSpace> allParkingSpaces = parkingService.getAllParkingSpaces();
        List<Facility> facilities = facilityService.getAllFacilities();
        
        // Get statistics
        int totalSpaces = allParkingSpaces.size();
        long availableSpaces = allParkingSpaces.stream()
                .filter(space -> "Available".equals(space.getStatus()))
                .count();
        long occupiedSpaces = allParkingSpaces.stream()
                .filter(space -> "Occupied".equals(space.getStatus()))
                .count();
        
        model.addAttribute("parkingSpaces", allParkingSpaces);
        model.addAttribute("facilities", facilities);
        model.addAttribute("totalSpaces", totalSpaces);
        model.addAttribute("availableSpaces", availableSpaces);
        model.addAttribute("occupiedSpaces", occupiedSpaces);
        model.addAttribute("newParkingSpace", new ParkingSpace());
        
        return "admin/parking-management";
    }

    /**
     * Display the parking allocation page for security personnel
     */
    @GetMapping("/allocate")
    @PreAuthorize("hasAnyRole('SECURITY_PERSONNEL', 'ADMIN')")
    public String parkingAllocationPage(Model model) {
        List<ParkingSpace> availableSpaces = parkingService.getAvailableParkingSpaces();
        List<Visitor> visitorsWithVehicles = visitorService.getVisitorsWithVehicles();
        
        model.addAttribute("availableSpaces", availableSpaces);
        model.addAttribute("visitors", visitorsWithVehicles);
        model.addAttribute("allocationRequest", new ParkingAllocationRequest());
        
        return "security/parking-allocation";
    }

    /**
     * Allocate a parking space to a visitor
     */
    @PostMapping("/allocate")
    @PreAuthorize("hasAnyRole('SECURITY_PERSONNEL', 'ADMIN')")
    public String allocateParkingSpace(@ModelAttribute ParkingAllocationRequest request) {
        try {
            Visitor visitor = visitorService.getVisitorById(request.getVisitorId());
            ParkingSpace parkingSpace = parkingService.getParkingSpaceById(request.getParkingSpaceId());
            CarDetails carDetails = vehicleService.getCarDetailsById(request.getCarId());
            
            if (visitor == null || parkingSpace == null || carDetails == null) {
                return "redirect:/parking/allocate?error=Invalid visitor, parking space, or vehicle";
            }
            
            // Check if parking space is available
            if (!"Available".equals(parkingSpace.getStatus())) {
                return "redirect:/parking/allocate?error=Parking space is not available";
            }
            
            // Allocate the parking space
            parkingSpace.setStatus("Occupied");
            parkingSpace.setVisitor(visitor);
            parkingSpace.setCarDetails(carDetails);
            parkingSpace.setAllocatedAt(LocalDateTime.now());
            
            parkingService.saveParkingSpace(parkingSpace);
            
            return "redirect:/parking/allocate?success=Parking space allocated successfully";
            
        } catch (Exception e) {
            return "redirect:/parking/allocate?error=" + e.getMessage();
        }
    }

    /**
     * Release a parking space
     */
    @PostMapping("/release/{parkingSpaceId}")
    @PreAuthorize("hasAnyRole('SECURITY_PERSONNEL', 'ADMIN')")
    @ResponseBody
    public ResponseEntity<ParkingResponse> releaseParkingSpace(@PathVariable Long parkingSpaceId) {
        try {
            ParkingSpace parkingSpace = parkingService.getParkingSpaceById(parkingSpaceId);
            
            if (parkingSpace == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ParkingResponse(false, "Parking space not found"));
            }
            
            // Release the parking space
            parkingSpace.setStatus("Available");
            parkingSpace.setVisitor(null);
            parkingSpace.setCarDetails(null);
            parkingSpace.setReleasedAt(LocalDateTime.now());
            
            parkingService.saveParkingSpace(parkingSpace);
            
            return ResponseEntity.ok(new ParkingResponse(true, "Parking space released successfully"));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ParkingResponse(false, "Error releasing parking space: " + e.getMessage()));
        }
    }

    /**
     * Create a new parking space
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String createParkingSpace(@ModelAttribute ParkingSpace parkingSpace) {
        try {
            parkingSpace.setStatus("Available");
            parkingService.saveParkingSpace(parkingSpace);
            return "redirect:/parking?success=Parking space created successfully";
        } catch (Exception e) {
            return "redirect:/parking?error=" + e.getMessage();
        }
    }

    /**
     * Delete a parking space
     */
    @DeleteMapping("/{parkingSpaceId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<ParkingResponse> deleteParkingSpace(@PathVariable Long parkingSpaceId) {
        try {
            ParkingSpace parkingSpace = parkingService.getParkingSpaceById(parkingSpaceId);
            
            if (parkingSpace == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ParkingResponse(false, "Parking space not found"));
            }
            
            // Check if the parking space is occupied
            if ("Occupied".equals(parkingSpace.getStatus())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ParkingResponse(false, "Cannot delete an occupied parking space"));
            }
            
            parkingService.deleteParkingSpace(parkingSpaceId);
            
            return ResponseEntity.ok(new ParkingResponse(true, "Parking space deleted successfully"));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ParkingResponse(false, "Error deleting parking space: " + e.getMessage()));
        }
    }

    /**
     * Get parking allocation for a specific visitor
     */
    @GetMapping("/visitor/{visitorId}")
    @PreAuthorize("hasAnyRole('SECURITY_PERSONNEL', 'ADMIN', 'OFFICER')")
    @ResponseBody
    public ResponseEntity<ParkingSpace> getVisitorParking(@PathVariable Long visitorId) {
        Visitor visitor = visitorService.getVisitorById(visitorId);
        
        if (visitor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        ParkingSpace parkingSpace = parkingService.getParkingSpaceByVisitor(visitor);
        
        if (parkingSpace == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        return ResponseEntity.ok(parkingSpace);
    }

    /**
     * Get parking statistics
     */
    @GetMapping("/stats")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getParkingStats() {
        Map<String, Object> stats = parkingService.getParkingStatistics();
        return ResponseEntity.ok(stats);
    }

    /**
     * Get parking spaces by facility
     */
    @GetMapping("/facility/{facilityId}")
    @PreAuthorize("hasAnyRole('SECURITY_PERSONNEL', 'ADMIN')")
    @ResponseBody
    public ResponseEntity<List<ParkingSpace>> getParkingSpacesByFacility(@PathVariable Long facilityId) {
        Facility facility = facilityService.getFacilityById(facilityId);
        
        if (facility == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        List<ParkingSpace> parkingSpaces = parkingService.getParkingSpacesByFacility(facility);
        return ResponseEntity.ok(parkingSpaces);
    }
}