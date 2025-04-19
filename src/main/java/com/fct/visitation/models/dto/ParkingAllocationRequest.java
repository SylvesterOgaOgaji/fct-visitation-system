package com.fct.visitation.models.dto;

public class ParkingAllocationRequest {
    private Long visitorId;
    private Long parkingSpaceId;
    private String carId;

    // Constructors
    public ParkingAllocationRequest() {}

    public ParkingAllocationRequest(Long visitorId, Long parkingSpaceId, String carId) {
        this.visitorId = visitorId;
        this.parkingSpaceId = parkingSpaceId;
        this.carId = carId;
    }

    // Getters and Setters
    public Long getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(Long visitorId) {
        this.visitorId = visitorId;
    }

    public Long getParkingSpaceId() {
        return parkingSpaceId;
    }

    public void setParkingSpaceId(Long parkingSpaceId) {
        this.parkingSpaceId = parkingSpaceId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }
}