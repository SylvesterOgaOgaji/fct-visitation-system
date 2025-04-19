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
import java.util.Optional;

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

    // Other methods remain the same...

    @PostMapping("/allocate")
    @PreAuthorize("hasAnyRole('SECURITY_PERSONNEL', 'ADMIN')")
    public String allocateParkingSpace(@ModelAttribute ParkingAllocationRequest request) {
        try {
            // Ensure visitorId is converted to Long
            Long visitorId = Long.valueOf(request.getVisitorId());
            Optional<Visitor> visitorOpt = visitorService.getVisitorById(visitorId);
            
            if (!visitorOpt.isPresent()) {
                return "redirect:/parking/allocate?error=Invalid visitor";
            }
            Visitor visitor = visitorOpt.get();
            
            ParkingSpace parkingSpace = parkingService.getParkingSpaceById(request.getParkingSpaceId());
            
            // Ensure carId is converted to Long
            Long carId = Long.valueOf(request.getCarId());
            Optional<CarDetails> carDetailsOpt = vehicleService.findById(carId);
            
            if (!carDetailsOpt.isPresent()) {
                return "redirect:/parking/allocate?error=Invalid vehicle";
            }
            CarDetails carDetails = carDetailsOpt.get();
            
            if (parkingSpace == null) {
                return "redirect:/parking/allocate?error=Invalid parking space";
            }
            
            // Check if parking space is available
            if (parkingSpace.getStatus() != ParkingSpace.Status.AVAILABLE) {
                return "redirect:/parking/allocate?error=Parking space is not available";
            }
            
            // Allocate the parking space
            parkingSpace.setStatus(ParkingSpace.Status.OCCUPIED);
            parkingSpace.setVisitor(visitor);
            parkingSpace.setCarDetails(carDetails);
            parkingSpace.setAllocatedAt(LocalDateTime.now());
            
            parkingService.saveParkingSpace(parkingSpace);
            
            return "redirect:/parking/allocate?success=Parking space allocated successfully";
            
        } catch (NumberFormatException e) {
            return "redirect:/parking/allocate?error=Invalid ID format";
        } catch (Exception e) {
            return "redirect:/parking/allocate?error=" + e.getMessage();
        }
    }

    @GetMapping("/visitor/{visitorId}")
    @PreAuthorize("hasAnyRole('SECURITY_PERSONNEL', 'ADMIN', 'OFFICER')")
    @ResponseBody
    public ResponseEntity<List<ParkingSpace>> getVisitorParking(@PathVariable Long visitorId) {
        Optional<Visitor> visitorOpt = visitorService.getVisitorById(visitorId);
        
        if (!visitorOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        Visitor visitor = visitorOpt.get();
        List<ParkingSpace> parkingSpaces = parkingService.getParkingSpaceByVisitor(visitor);
        
        if (parkingSpaces.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        return ResponseEntity.ok(parkingSpaces);
    }

    // Other methods remain the same...
}