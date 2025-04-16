package com.fct.visitation.services.impl;

import com.fct.visitation.models.entity.Checkpoint;
import com.fct.visitation.models.entity.Facility;
import com.fct.visitation.repositories.CheckpointRepository;
import com.fct.visitation.services.interfaces.CheckpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the CheckpointService interface
 */
@Service
public class CheckpointServiceImpl implements CheckpointService {

    private final CheckpointRepository checkpointRepository;

    @Autowired
    public CheckpointServiceImpl(CheckpointRepository checkpointRepository) {
        this.checkpointRepository = checkpointRepository;
    }

    @Override
    public List<Checkpoint> getAllCheckpoints() {
        return checkpointRepository.findAll();
    }

    @Override
    public Checkpoint getCheckpointById(Long id) {
        return checkpointRepository.findById(id).orElse(null);
    }

    @Override
    public List<Checkpoint> getCheckpointsByFacility(Facility facility) {
        return checkpointRepository.findByFacility(facility);
    }

    @Override
    public List<Checkpoint> getCheckpointsByType(String checkpointType) {
        return checkpointRepository.findByCheckpointType(checkpointType);
    }

    @Override
    public List<Checkpoint> getActiveCheckpoints() {
        return checkpointRepository.findByIsActiveTrue();
    }

    @Override
    @Transactional
    public Checkpoint saveCheckpoint(Checkpoint checkpoint) {
        return checkpointRepository.save(checkpoint);
    }

    @Override
    @Transactional
    public void deleteCheckpoint(Long id) {
        checkpointRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Checkpoint activateCheckpoint(Long id) {
        Checkpoint checkpoint = getCheckpointById(id);
        if (checkpoint != null) {
            checkpoint.setIsActive(true);
            return checkpointRepository.save(checkpoint);
        }
        return null;
    }

    @Override
    @Transactional
    public Checkpoint deactivateCheckpoint(Long id) {
        Checkpoint checkpoint = getCheckpointById(id);
        if (checkpoint != null) {
            checkpoint.setIsActive(false);
            return checkpointRepository.save(checkpoint);
        }
        return null;
    }

    @Override
    public List<Checkpoint> searchCheckpoints(String searchTerm) {
        List<Checkpoint> nameMatches = checkpointRepository.findByNameContainingIgnoreCase(searchTerm);
        List<Checkpoint> locationMatches = checkpointRepository.findByLocationContainingIgnoreCase(searchTerm);
        
        // Combine results and remove duplicates
        return nameMatches.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<Checkpoint> getEntryCheckpoints(Facility facility) {
        return checkpointRepository.findByFacilityAndCheckpointType(facility, "ENTRY");
    }

    @Override
    public List<Checkpoint> getExitCheckpoints(Facility facility) {
        return checkpointRepository.findByFacilityAndCheckpointType(facility, "EXIT");
    }

    @Override
    public long countCheckpointsByFacility(Facility facility) {
        return checkpointRepository.countByFacility(facility);
    }
}