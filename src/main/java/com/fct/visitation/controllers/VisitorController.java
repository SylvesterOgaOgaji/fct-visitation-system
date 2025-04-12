package com.fct.visitation.controllers;

import com.fct.visitation.exceptions.DuplicateNinException;
import com.fct.visitation.exceptions.NinVerificationException;
import com.fct.visitation.exceptions.WeeklyVisitLimitException;
import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.services.interfaces.FacilityService;
import com.fct.visitation.services.interfaces.OfficerService;
import com.fct.visitation.services.interfaces.PurposeOfVisitService;
import com.fct.visitation.services.interfaces.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Controller
@RequestMapping("/visitor")
public class VisitorController {
    private final VisitorService visitorService;
    private final FacilityService facilityService;
    private final OfficerService officerService;
    private final PurposeOfVisitService purposeOfVisitService;

    @Autowired
    public VisitorController(VisitorService visitorService, 
                            FacilityService facilityService,
                            OfficerService officerService,
                            PurposeOfVisitService purposeOfVisitService) {
        this.visitorService = visitorService;
        this.facilityService = facilityService;
        this.officerService = officerService;
        this.purposeOfVisitService = purposeOfVisitService;
    }
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("visitor", new Visitor());
        model.addAttribute("facilities", facilityService.findAll());
        return "visitor/registration";
    }

    @PostMapping("/register")
    public String registerVisitor(@ModelAttribute Visitor visitor, Model model, RedirectAttributes redirectAttributes) {
        try {
            // Parse the appointmentDateTime if it's in the custom format
            if (visitor.getAppointmentDateTime() != null) {
                String dateTimeStr = visitor.getAppointmentDateTime().toString();
                
                // Check if it's in our custom format M/d/yy, h:mm a
                if (dateTimeStr.matches("\\d{1,2}/\\d{1,2}/\\d{2},\\s+\\d{1,2}:\\d{2}\\s+[AP]M")) {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy, h:mm a");
                        LocalDateTime parsedDateTime = LocalDateTime.parse(dateTimeStr, formatter);
                        visitor.setAppointmentDateTime(parsedDateTime);
                    } catch (DateTimeParseException e) {
                        // If parsing fails, just use the value as is
                        System.out.println("Date parsing failed: " + e.getMessage());
                    }
                }
            }
            
            Visitor registeredVisitor = visitorService.registerVisitor(visitor);
            return "redirect:/visitor/confirmation?id=" + registeredVisitor.getVisitorId();
        } catch (WeeklyVisitLimitException e) {
            model.addAttribute("errorMessage", e.getMessage());
        } catch (DuplicateNinException e) {
            model.addAttribute("errorMessage", e.getMessage());
        } catch (NinVerificationException e) {
            model.addAttribute("errorMessage", e.getMessage());
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "An error occurred while registering the visitor. Please try again. Error: " + e.getMessage());
        }
        
        // Re-populate the form with necessary data
        model.addAttribute("visitor", visitor);
        model.addAttribute("facilities", facilityService.findAll());
        
        // If facility is selected, load related officers and purposes
        if (visitor.getFacility() != null && visitor.getFacility().getFacilityId() != null) {
            model.addAttribute("officers", officerService.findByFacilityId(visitor.getFacility().getFacilityId()));
            model.addAttribute("purposes", purposeOfVisitService.findByFacilityId(visitor.getFacility().getFacilityId()));
        }
        
        // Return to the registration form
        return "visitor/registration";
    }
    
    @GetMapping("/confirmation")
    public String showConfirmation(@RequestParam Long id, Model model) {
        model.addAttribute("visitor", visitorService.findById(id).orElseThrow());
        return "visitor/confirmation";
    }
    
    @GetMapping("/check-in/{qrCode}")
    public String checkInVisitor(@PathVariable String qrCode, Model model) {
        Visitor visitor = visitorService.findByQrCode(qrCode)
                .orElseThrow(() -> new RuntimeException("Invalid QR Code"));
        visitor = visitorService.checkInVisitor(visitor.getVisitorId());
        model.addAttribute("visitor", visitor);
        return "visitor/checked-in";
    }
}