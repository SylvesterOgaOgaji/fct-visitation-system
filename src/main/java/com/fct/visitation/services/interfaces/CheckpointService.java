package com.fct.visitation.services.interfaces;

import com.fct.visitation.models.entity.Checkpoint;
import com.fct.visitation.models.entity.Facility;
import java.util.List;
import java.util.Optional;

public interface CheckpointService {
    List<Checkpoint> getAllCheckpoints();
    Checkpoint getCheckpointById(Long id);
    List<Checkpoint> getCheckpointsByFacility(Facility facility);
    List<Checkpoint> getCheckpointsByType(String checkpointType);
    List<Checkpoint> getActiveCheckpoints();
    Checkpoint saveCheckpoint(Checkpoint checkpoint);
    void deleteCheckpoint(Long id);
    Checkpoint activateCheckpoint(Long id);
    Checkpoint deactivateCheckpoint(Long id);
    List<Checkpoint> searchCheckpoints(String searchTerm);
    List<Checkpoint> getEntryCheckpoints(Facility facility);
    List<Checkpoint> getExitCheckpoints(Facility facility);
    long countCheckpointsByFacility(Facility facility);
}