package com.fct.visitation.controllers.api;

import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.models.enums.VisitorStatus;
import com.fct.visitation.services.interfaces.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/visitors")
public class VisitorApiController {

    @Autowired
    private VisitorService visitorService;

    @PutMapping("/{id}/check-in")
    public ResponseEntity<Visitor> checkInVisitor(@PathVariable Long id) {
        Visitor visitor = visitorService.checkInVisitor(id);
        return visitor != null ? 
            ResponseEntity.ok(visitor) : 
            ResponseEntity.notFound().build();
    }
}