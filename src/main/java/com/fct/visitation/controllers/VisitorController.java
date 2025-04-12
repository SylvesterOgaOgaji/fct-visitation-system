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
            Visitor registeredVisitor = visitorService.registerVisitor(visitor);
            return "redirect:/visitor/confirmation?id=" + registeredVisitor.getVisitorId();
        } catch (WeeklyVisitLimitException e) {
            model.addAttribute("errorMessage", e.getMessage());
        } catch (DuplicateNinException e) {
            model.addAttribute("errorMessage", e.getMessage());
        } catch (NinVerificationException e) {
            model.addAttribute("errorMessage", e.getMessage());
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "An error occurred while registering the visitor. Please try again.");
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
