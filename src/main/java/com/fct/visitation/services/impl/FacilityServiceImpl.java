package com.fct.visitation.services.impl;

import com.fct.visitation.models.entity.Facility;
import com.fct.visitation.repositories.FacilityRepository;
import com.fct.visitation.services.interfaces.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacilityServiceImpl implements FacilityService {

    private final FacilityRepository facilityRepository;

    @Autowired
    public FacilityServiceImpl(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    @Override
    public List<Facility> findAll() {
        return facilityRepository.findAll();
    }

    @Override
    public Optional<Facility> findById(Long id) {
        return facilityRepository.findById(id);
    }
    @Override
    public Facility save(Facility facility) {
        return facilityRepository.save(facility);
    }

    @Override
    public void deleteById(Long id) {
        facilityRepository.deleteById(id);
    }
}
