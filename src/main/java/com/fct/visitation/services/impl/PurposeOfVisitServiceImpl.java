package com.fct.visitation.services.impl;

import com.fct.visitation.models.entity.PurposeOfVisit;
import com.fct.visitation.repositories.PurposeOfVisitRepository;
import com.fct.visitation.services.interfaces.PurposeOfVisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurposeOfVisitServiceImpl implements PurposeOfVisitService {
    private final PurposeOfVisitRepository purposeOfVisitRepository;

    @Autowired
    public PurposeOfVisitServiceImpl(PurposeOfVisitRepository purposeOfVisitRepository) {
        this.purposeOfVisitRepository = purposeOfVisitRepository;
    }

    @Override
    public List<PurposeOfVisit> findAll() {
        return purposeOfVisitRepository.findAll();
    }

    @Override
    public Optional<PurposeOfVisit> findById(Long id) {
        return purposeOfVisitRepository.findById(id);
    }

    @Override
    public List<PurposeOfVisit> findByFacilityId(Long facilityId) {
        // This method might need to be implemented differently 
        // depending on your exact data model
        return purposeOfVisitRepository.findByIsActiveTrue();
    }

    @Override
    public PurposeOfVisit save(PurposeOfVisit purposeOfVisit) {
        return purposeOfVisitRepository.save(purposeOfVisit);
    }

    @Override
    public void deleteById(Long id) {
        purposeOfVisitRepository.deleteById(id);
    }
}