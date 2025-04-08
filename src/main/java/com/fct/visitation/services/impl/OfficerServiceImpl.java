package com.fct.visitation.services.impl;

import com.fct.visitation.models.entity.Officer;
import com.fct.visitation.repositories.OfficerRepository;
import com.fct.visitation.services.interfaces.OfficerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfficerServiceImpl implements OfficerService {

    private final OfficerRepository officerRepository;

    @Autowired
    public OfficerServiceImpl(OfficerRepository officerRepository) {
        this.officerRepository = officerRepository;
    }

@Override
    public List<Officer> findAll() {
        return officerRepository.findAll();
    }

    @Override
    public Optional<Officer> findById(Long id) {
        return officerRepository.findById(id);
    }

    @Override
    public List<Officer> findByFacilityId(Long facilityId) {
        return officerRepository.findByFacilityFacilityId(facilityId);
    }

    @Override
    public Officer save(Officer officer) {
        return officerRepository.save(officer);
    }

    @Override
    public void deleteById(Long id) {
        officerRepository.deleteById(id);
    }
}
