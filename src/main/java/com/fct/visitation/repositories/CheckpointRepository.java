package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.Checkpoint;
import com.fct.visitation.models.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Checkpoint entity operations
 */
@Repository
public interface CheckpointRepository extends JpaRepository<Checkpoint, Long> {
    
    /**
     * Find checkpoints by name
     * 
     * @param name The name to search for
     * @return List of checkpoints with the specified name
     */
    List<Checkpoint> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find checkpoints by checkpoint type
     * 
     * @param checkpointType The checkpoint type to search for
     * @return List of checkpoints with the specified type
     */
    List<Checkpoint> findByCheckpointType(String checkpointType);
    
    /**
     * Find checkpoints for a specific facility
     * 
     * @param facility The facility to search for
     * @return List of checkpoints for the specified facility
     */
    List<Checkpoint> findByFacility(Facility facility);
    
    /**
     * Find active checkpoints
     * 
     * @return List of active checkpoints
     */
    List<Checkpoint> findByIsActiveTrue();
    
    /**
     * Find inactive checkpoints
     * 
     * @return List of inactive checkpoints
     */
    List<Checkpoint> findByIsActiveFalse();
    
    /**
     * Find active checkpoints for a specific facility
     * 
     * @param facility The facility to search for
     * @return List of active checkpoints for the specified facility
     */
    List<Checkpoint> findByFacilityAndIsActiveTrue(Facility facility);
    
    /**
     * Find checkpoints by facility and checkpoint type
     * 
     * @param facility The facility to search for
     * @param checkpointType The checkpoint type to search for
     * @return List of checkpoints for the specified facility and type
     */
    List<Checkpoint> findByFacilityAndCheckpointType(Facility facility, String checkpointType);
    
    /**
     * Find checkpoints by location
     * 
     * @param location The location to search for
     * @return List of checkpoints in the specified location
     */
    List<Checkpoint> findByLocationContainingIgnoreCase(String location);
    
    /**
     * Count checkpoints by facility
     * 
     * @param facility The facility to count for
     * @return Count of checkpoints for the specified facility
     */
    long countByFacility(Facility facility);
    
    /**
     * Count checkpoints by checkpoint type
     * 
     * @param checkpointType The checkpoint type to count
     * @return Count of checkpoints with the specified type
     */
    long countByCheckpointType(String checkpointType);
}