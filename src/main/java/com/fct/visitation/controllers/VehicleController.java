package com.fct.visitation.controllers;

import com.fct.visitation.models.dto.CarDetailsRequest;
import com.fct.visitation.models.dto.RentedCarDriverRequest;
import com.fct.visitation.models.dto.VehicleResponse;
import com.fct.visitation.models.entity.CarDetails;
import com.fct.visitation.models.entity.RentedCarDriver;
import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.services.interfaces.NinVerificationService;
import com.fct.visitation.services.interfaces.VehicleService;
import com.fct.visitation.services.interfaces.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Controller for managing vehicle-related operations
 */
@Controller
@RequestMapping("/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;
    private final VisitorService visitorService;
    private final NinVerificationService ninVerificationService;

    @Autowired
    public VehicleController(
            VehicleService vehicleService,
            VisitorService visitorService,
            NinVerificationService ninVerificationService) {
        this.vehicleService = vehicleService;
        this.visitorService = visitorService;
        this.ninVerificationService = ninVerificationService;
    }

    /**
     * Verify a vehicle's details
     */
    @PutMapping("/verify/{carId}")
    @PreAuthorize("hasAnyRole('SECURITY_PERSONNEL', 'ADMIN')")
    @ResponseBody
    public ResponseEntity<VehicleResponse> verifyVehicle(
            @PathVariable Long carId,
            @RequestParam String status,
            @RequestParam(required = false) String notes,
            Principal principal) {
        
        try {
            CarDetails carDetails = vehicleService.getCarDetailsById(carId);
            
            if (carDetails == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new VehicleResponse(false, "Vehicle not found"));
            }
            
            // Update verification status
            if ("Rented".equals(carDetails.getCarType()) && carDetails.getDriver() != null) {
                RentedCarDriver driver = carDetails.getDriver();
                driver.setVerificationStatus(status);
                driver.setVerificationNotes(notes);
                
                // Set verification by security personnel
                driver.setVerifiedBy(principal.getName());
                
                vehicleService.saveRentedCarDriver(driver);
            }
            
            return ResponseEntity.ok(new VehicleResponse(true, "Vehicle verification updated successfully"));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new VehicleResponse(false, "Error updating vehicle verification: " + e.getMessage()));
        }
    }

    /**
     * Get all vehicles for a specific visitor
     */
    @GetMapping("/visitor/{visitorId}")
    @PreAuthorize("hasAnyRole('SECURITY_PERSONNEL', 'ADMIN', 'OFFICER')")
    @ResponseBody
    public ResponseEntity<List<CarDetails>> getVisitorVehicles(@PathVariable Long visitorId) {
        Visitor visitor = visitorService.getVisitorById(visitorId);
        
        if (visitor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        List<CarDetails> vehicles = vehicleService.getVehiclesByVisitor(visitor);
        return ResponseEntity.ok(vehicles);
    }

    /**
     * Get vehicle details by ID
     */
    @GetMapping("/{carId}")
    @PreAuthorize("hasAnyRole('SECURITY_PERSONNEL', 'ADMIN', 'OFFICER')")
    @ResponseBody
    public ResponseEntity<CarDetails> getVehicleDetails(@PathVariable Long carId) {
        CarDetails carDetails = vehicleService.getCarDetailsById(carId);
        
        if (carDetails == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        return ResponseEntity.ok(carDetails);
    }

    /**
     * Update vehicle details
     */
    @PutMapping("/{carId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<VehicleResponse> updateVehicleDetails(
            @PathVariable Long carId,
            @RequestBody CarDetailsRequest request) {
        
        try {
            CarDetails carDetails = vehicleService.getCarDetailsById(carId);
            
            if (carDetails == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new VehicleResponse(false, "Vehicle not found"));
            }
            
            // Update details
            carDetails.setRegistrationNumber(request.getRegistrationNumber());
            carDetails.setModel(request.getModel());
            carDetails.setColor(request.getColor());
            
            vehicleService.saveCarDetails(carDetails);
            
            return ResponseEntity.ok(new VehicleResponse(true, "Vehicle details updated successfully"));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new VehicleResponse(false, "Error updating vehicle details: " + e.getMessage()));
        }
    }

    /**
     * Delete vehicle record
     */
    @DeleteMapping("/{carId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<VehicleResponse> deleteVehicle(@PathVariable Long carId) {
        try {
            CarDetails carDetails = vehicleService.getCarDetailsById(carId);
            
            if (carDetails == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new VehicleResponse(false, "Vehicle not found"));
            }
            
            vehicleService.deleteCarDetails(carId);
            
            return ResponseEntity.ok(new VehicleResponse(true, "Vehicle deleted successfully"));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new VehicleResponse(false, "Error deleting vehicle: " + e.getMessage()));
        }
    }
}* Display vehicle registration form
     */
    @GetMapping("/register/{visitorId}")
    public String vehicleRegistrationForm(@PathVariable Long visitorId, Model model) {
        Visitor visitor = visitorService.getVisitorById(visitorId);
        
        if (visitor == null) {
            return "redirect:/visitor/registration";
        }
        
        model.addAttribute("visitor", visitor);
        model.addAttribute("carDetailsRequest", new CarDetailsRequest());
        model.addAttribute("rentedCarDriverRequest", new RentedCarDriverRequest());
        
        return "visitor/vehicle-registration";
    }

    /**
     * Register a visitor's own vehicle
     */
    @PostMapping("/register/own")
    public String registerOwnVehicle(
            @ModelAttribute CarDetailsRequest carDetailsRequest) {
        
        try {
            Visitor visitor = visitorService.getVisitorById(carDetailsRequest.getVisitorId());
            
            if (visitor == null) {
                return "redirect:/visitor/registration";
            }
            
            // Update visitor's car type
            visitor.setCarType("Own");
            visitorService.saveVisitor(visitor);
            
            // Create car details
            CarDetails carDetails = new CarDetails();
            carDetails.setVisitor(visitor);
            carDetails.setCarType("Own");
            carDetails.setRegistrationNumber(carDetailsRequest.getRegistrationNumber());
            carDetails.setModel(carDetailsRequest.getModel());
            carDetails.setColor(carDetailsRequest.getColor());
            
            vehicleService.saveCarDetails(carDetails);
            
            return "redirect:/visitor/confirmation/" + visitor.getId();
        } catch (Exception e) {
            return "redirect:/error?message=" + e.getMessage();
        }
    }

    /**
     * Register a rented vehicle with driver information
     */
    @PostMapping("/register/rented")
    public String registerRentedVehicle(
            @ModelAttribute CarDetailsRequest carDetailsRequest,
            @ModelAttribute RentedCarDriverRequest driverRequest) {
        
        try {
            Visitor visitor = visitorService.getVisitorById(carDetailsRequest.getVisitorId());
            
            if (visitor == null) {
                return "redirect:/visitor/registration";
            }
            
            // Update visitor's car type
            visitor.setCarType("Rented");
            visitorService.saveVisitor(visitor);
            
            // Create and save the driver information
            RentedCarDriver driver = new RentedCarDriver();
            driver.setVisitor(visitor);
            driver.setName(driverRequest.getName());
            driver.setNin(driverRequest.getNin());
            driver.setLicenseNumber(driverRequest.getLicenseNumber());
            driver.setContactNumber(driverRequest.getContactNumber());
            driver.setVerificationStatus("Pending");
            
            // Verify NIN if possible
            try {
                boolean ninValid = ninVerificationService.validateNinDetails(
                        driverRequest.getNin(),
                        driverRequest.getName().split(" ")[0], // First name
                        driverRequest.getName().split(" ")[1]  // Last name
                );
                
                if (ninValid) {
                    driver.setVerificationStatus("Verified");
                }
            } catch (Exception e) {
                // NIN verification failed, keep status as pending
            }
            
            RentedCarDriver savedDriver = vehicleService.saveRentedCarDriver(driver);
            
            // Create car details
            CarDetails carDetails = new CarDetails();
            carDetails.setVisitor(visitor);
            carDetails.setCarType("Rented");
            carDetails.setRegistrationNumber(carDetailsRequest.getRegistrationNumber());
            carDetails.setModel(carDetailsRequest.getModel());
            carDetails.setColor(carDetailsRequest.getColor());
            carDetails.setDriver(savedDriver);
            
            vehicleService.saveCarDetails(carDetails);
            
            return "redirect:/visitor/confirmation/" + visitor.getId();
        } catch (Exception e) {
            return "redirect:/error?message=" + e.getMessage();
        }
    }

    /**
     * Display vehicle verification page for security personnel
     */
    @GetMapping("/verify")
    @PreAuthorize("hasAnyRole('SECURITY_PERSONNEL', 'ADMIN')")
    public String vehicleVerificationPage(Model model) {
        List<CarDetails> pendingVehicles = vehicleService.getPendingVehicleVerifications();
        model.addAttribute("pendingVehicles", pendingVehicles);
        return "security/vehicle-verification";
    }

    /**