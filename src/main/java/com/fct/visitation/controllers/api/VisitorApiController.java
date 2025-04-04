package com.fct.visitation.controllers.api;

import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.services.interfaces.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/visitors")
public class VisitorApiController {

    @Autowired
    private VisitorService visitorService;

    @GetMapping
    public ResponseEntity<List<Visitor>> getAllVisitors() {
        return ResponseEntity.ok(visitorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Visitor> getVisitorById(@PathVariable Long id) {
        return visitorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/qr/{qrCode}")
    public ResponseEntity<Visitor> getVisitorByQrCode(@PathVariable String qrCode) {
        return visitorService.findByQrCode(qrCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Visitor> createVisitor(@RequestBody Visitor visitor) {
        return ResponseEntity.ok(visitorService.registerVisitor(visitor));
    }

    @PutMapping("/{id}/check-in")
    public ResponseEntity<Visitor> checkInVisitor(@PathVariable Long id) {
        return ResponseEntity.ok(visitorService.checkInVisitor(id));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<Visitor> completeVisit(@PathVariable Long id) {
        return ResponseEntity.ok(visitorService.completeVisit(id));
    }
}
