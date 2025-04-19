package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.ParkingSpace;
import com.fct.visitation.models.enums.SpaceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {
    
    /**
     * Find parking spaces by facility
     * 
     * @param facilityId The facility ID
     * @return List of parking spaces for the facility
     */
    List<ParkingSpace> findByFacilityFacilityId(Long facilityId);
    
    /**
     * Find parking spaces by facility and status
     * 
     * @param facilityId The facility ID
     * @param status The parking space status
     * @return List of parking spaces for the facility with the specified status
     */
    List<ParkingSpace> findByFacilityFacilityIdAndStatus(Long facilityId, ParkingSpace.Status status);
    
    /**
     * Find parking spaces by facility, space type, and status
     * 
     * @param facilityId The facility ID
     * @param spaceType The parking space type
     * @param status The parking space status
     * @return List of parking spaces for the facility with the specified type and status
     */
    List<ParkingSpace> findByFacilityFacilityIdAndSpaceTypeAndStatus(
            Long facilityId, SpaceType spaceType, ParkingSpace.Status status);
    
    /**
     * Find a parking space by location code
     * 
     * @param locationCode The location code
     * @return Optional containing the parking space if found
     */
    Optional<ParkingSpace> findByLocationCode(String locationCode);
    
    /**
     * Find the first available parking space for a facility
     * 
     * @param facilityId The facility ID
     * @param status The parking space status (typically AVAILABLE)
     * @return Optional containing the first available parking space
     */
    Optional<ParkingSpace> findTopByFacilityFacilityIdAndStatus(Long facilityId, ParkingSpace.Status status);
    
    /**
     * Find the first available parking space of a specific type for a facility
     * 
     * @param facilityId The facility ID
     * @param spaceType The parking space type
     * @param status The parking space status (typically AVAILABLE)
     * @return Optional containing the first available parking space of the specified type
     */
    Optional<ParkingSpace> findTopByFacilityFacilityIdAndSpaceTypeAndStatus(
            Long facilityId, SpaceType spaceType, ParkingSpace.Status status);
    
    /**
     * Find parking spaces by visitor
     * 
     * @param visitorId The visitor ID
     * @return List of parking spaces allocated to the visitor
     */
    List<ParkingSpace> findByVisitorVisitorId(Long visitorId);
    
    /**
     * Count available parking spaces by facility
     * 
     * @param facilityId The facility ID
     * @param status The parking space status (typically AVAILABLE)
     * @return Count of available parking spaces
     */
    long countByFacilityFacilityIdAndStatus(Long facilityId, ParkingSpace.Status status);
    
    /**
     * Count parking spaces by facility and type
     * 
     * @param facilityId The facility ID
     * @param spaceType The parking space type
     * @return Count of parking spaces of the specified type
     */
    long countByFacilityFacilityIdAndSpaceType(Long facilityId, SpaceType spaceType);
}