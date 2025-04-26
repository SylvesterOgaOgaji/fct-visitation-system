package com.fct.visitation.repositories;

import com.fct.visitation.models.entity.ParkingSpace;
import com.fct.visitation.models.enums.SpaceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {
    
    // Method now correctly maps to the new field
    Optional<ParkingSpace> findByLocationCode(String locationCode);
    
    // Other methods remain unchanged...
    List<ParkingSpace> findByParkingArea_Facility_Id(Long facilityId);
    List<ParkingSpace> findByStatus(ParkingSpace.Status status);
    List<ParkingSpace> findByParkingArea_Facility_IdAndStatus(Long facilityId, ParkingSpace.Status status);
    List<ParkingSpace> findByParkingArea_Facility_IdAndSpaceTypeAndStatus(
            Long facilityId, SpaceType spaceType, ParkingSpace.Status status);
    Optional<ParkingSpace> findTopByParkingArea_Facility_IdAndStatus(Long facilityId, ParkingSpace.Status status);
    Optional<ParkingSpace> findTopByParkingArea_Facility_IdAndSpaceTypeAndStatus(
            Long facilityId, SpaceType spaceType, ParkingSpace.Status status);
    Optional<ParkingSpace> findTopBySpaceTypeAndStatus(SpaceType spaceType, ParkingSpace.Status status);
    List<ParkingSpace> findByVisitor_Id(Long visitorId);
    long countByParkingArea_Facility_IdAndStatus(Long facilityId, ParkingSpace.Status status);
    long countByParkingArea_Facility_IdAndSpaceType(Long facilityId, SpaceType spaceType);
    long countBySpaceType(SpaceType spaceType);
}