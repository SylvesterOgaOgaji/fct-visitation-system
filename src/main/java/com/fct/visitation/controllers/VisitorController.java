package com.fct.visitation.controllers;

import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.services.interfaces.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/visitors")
public class VisitorController {
    
    @Autowired
    private VisitorService visitorService;

    @GetMapping
    public List<Visitor> getAllVisitors() {
        return visitorService.getAllVisitors();
    }

    @GetMapping("/{id}")
    public Visitor getVisitorById(@PathVariable Long id) {
        return visitorService.getVisitorById(id)
                .orElseThrow(() -> new RuntimeException("Visitor not found"));
    }

    @PostMapping
    public Visitor createVisitor(@RequestBody Visitor visitor) {
        return visitorService.saveVisitor(visitor);
    }

    @PutMapping("/{id}")
    public Visitor updateVisitor(@PathVariable Long id, @RequestBody Visitor visitorDetails) {
        Visitor visitor = visitorService.getVisitorById(id)
                .orElseThrow(() -> new RuntimeException("Visitor not found"));
        
        visitor.setFirstName(visitorDetails.getFirstName());
        visitor.setLastName(visitorDetails.getLastName());
        visitor.setEmail(visitorDetails.getEmail());
        visitor.setPhoneNumber(visitorDetails.getPhoneNumber());
        
        return visitorService.saveVisitor(visitor);
    }

    @DeleteMapping("/{id}")
    public void deleteVisitor(@PathVariable Long id) {
        Visitor visitor = visitorService.getVisitorById(id)
                .orElseThrow(() -> new RuntimeException("Visitor not found"));
        
        visitorService.deleteVisitor(id);
    }

    @PostMapping("/checkin/{id}")
    public Visitor checkInVisitor(@PathVariable Long id) {
        return visitorService.checkInVisitor(id);
    }

    @PostMapping("/checkout/{id}")
    public Visitor checkOutVisitor(@PathVariable Long id) {
        return visitorService.checkOutVisitor(id);
    }
}