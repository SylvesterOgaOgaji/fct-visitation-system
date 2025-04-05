package com.fct.visitation.services.interfaces;

import com.fct.visitation.models.entity.Officer;
import java.util.List;
import java.util.Optional;

public interface OfficerService {
    List<Officer> findAll();
    Optional<Officer> findById(Long id);
    List<Officer> findByFacilityId(Long facilityId);
    Officer save(Officer officer);
    void deleteById(Long id);
}
