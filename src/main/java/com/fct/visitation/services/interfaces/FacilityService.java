package com.fct.visitation.services.interfaces;

import com.fct.visitation.models.entity.Facility;
import java.util.List;
import java.util.Optional;

public interface FacilityService {
    List<Facility> findAll();
    Optional<Facility> findById(Long id);
    Facility save(Facility facility);
    void deleteById(Long id);
}
