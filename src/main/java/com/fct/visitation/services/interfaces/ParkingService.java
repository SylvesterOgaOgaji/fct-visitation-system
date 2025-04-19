package com.fct.visitation.services.interfaces;

import com.fct.visitation.models.entity.Facility;
import com.fct.visitation.models.entity.ParkingSpace;
import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.models.enums.SpaceType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ParkingService {
    List<ParkingSpace> getAllParkingSpaces();
    List<ParkingSpace> getAvailableParkingSpaces();
    ParkingSpace saveParkingSpace(ParkingSpace parkingSpace);
    ParkingSpace getParkingSpaceById(Long id);
    List<ParkingSpace> getParkingSpaceByVisitor(Visitor visitor);
    List<ParkingSpace> getParkingSpacesByFacility(Facility facility);
    Map<String, Object> getParkingStatistics();
    void deleteParkingSpace(Long id);
    Optional<ParkingSpace> findAvailableParkingSpaceByType(SpaceType spaceType);
    long countParkingSpacesByType(SpaceType spaceType);
}