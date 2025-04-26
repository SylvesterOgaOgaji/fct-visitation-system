package com.fct.visitation.services.impl;

import com.fct.visitation.models.entity.Facility;
import com.fct.visitation.models.entity.ParkingSpace;
import com.fct.visitation.models.entity.Visitor;
import com.fct.visitation.models.enums.SpaceType;
import com.fct.visitation.repositories.ParkingSpaceRepository;
import com.fct.visitation.services.interfaces.ParkingService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ParkingServiceImpl implements ParkingService {

    private final ParkingSpaceRepository parkingSpaceRepository;

    public ParkingServiceImpl(ParkingSpaceRepository parkingSpaceRepository) {
        this.parkingSpaceRepository = parkingSpaceRepository;
    }

    @Override
    public List<ParkingSpace> getAllParkingSpaces() {
        return parkingSpaceRepository.findAll();
    }

    @Override
    public List<ParkingSpace> getAvailableParkingSpaces() {
        return parkingSpaceRepository.findByStatus(ParkingSpace.Status.AVAILABLE);
    }

    @Override
    public ParkingSpace saveParkingSpace(ParkingSpace parkingSpace) {
        return parkingSpaceRepository.save(parkingSpace);
    }

    @Override
    public ParkingSpace getParkingSpaceById(Long id) {
        return parkingSpaceRepository.findById(id).orElse(null);
    }

    @Override
    public List<ParkingSpace> getParkingSpaceByVisitor(Visitor visitor) {
        return parkingSpaceRepository.findByVisitor_Id(visitor.getId());
    }

    @Override
    public List<ParkingSpace> getParkingSpacesByFacility(Facility facility) {
        return parkingSpaceRepository.findByParkingArea_Facility_Id(facility.getId());
    }

    @Override
    public Map<String, Object> getParkingStatistics() {
        Map<String, Object> stats = new HashMap<>();
        // Add your statistic calculations here
        return stats;
    }

    @Override
    public void deleteParkingSpace(Long id) {
        parkingSpaceRepository.deleteById(id);
    }

    @Override
    public Optional<ParkingSpace> findAvailableParkingSpaceByType(SpaceType spaceType) {
        return parkingSpaceRepository.findTopBySpaceTypeAndStatus(spaceType, ParkingSpace.Status.AVAILABLE);
    }

    @Override
    public long countParkingSpacesByType(SpaceType spaceType) {
        return parkingSpaceRepository.countBySpaceType(spaceType);
    }
}