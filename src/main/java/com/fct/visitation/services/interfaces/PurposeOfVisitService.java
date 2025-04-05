package com.fct.visitation.services.interfaces;

import com.fct.visitation.models.entity.PurposeOfVisit;
import java.util.List;
import java.util.Optional;

public interface PurposeOfVisitService {
    List<PurposeOfVisit> findAll();
    Optional<PurposeOfVisit> findById(Long id);
    List<PurposeOfVisit> findByFacilityId(Long facilityId);
    PurposeOfVisit save(PurposeOfVisit purposeOfVisit);
    void deleteById(Long id);
}
