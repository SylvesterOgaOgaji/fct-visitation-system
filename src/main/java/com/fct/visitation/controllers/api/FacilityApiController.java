package com.fct.visitation.controllers.api;

import com.fct.visitation.models.entity.Officer;
import com.fct.visitation.models.entity.PurposeOfVisit;
import com.fct.visitation.services.interfaces.OfficerService;
import com.fct.visitation.services.interfaces.PurposeOfVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/facilities")
public class FacilityApiController {

    private final OfficerService officerService;
    private final PurposeOfVisitService purposeOfVisitService;

    @Autowired
    public FacilityApiController(OfficerService officerService, 
                                 PurposeOfVisitService purposeOfVisitService) {
        this.officerService = officerService;
        this.purposeOfVisitService = purposeOfVisitService;
    }

 @GetMapping("/{facilityId}/officers")
    public ResponseEntity<List<Officer>> getOfficersByFacility(@PathVariable Long facilityId) {
        List<Officer> officers = officerService.findByFacilityId(facilityId);
        return ResponseEntity.ok(officers);
    }

    @GetMapping("/{facilityId}/purposes")
    public ResponseEntity<List<PurposeOfVisit>> getPurposesByFacility(@PathVariable Long facilityId) {
        List<PurposeOfVisit> purposes = purposeOfVisitService.findByFacilityId(facilityId);
        return ResponseEntity.ok(purposes);
    }
}
