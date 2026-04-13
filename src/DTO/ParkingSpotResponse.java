package DTO;


import enums.ParkingStatus;

public record ParkingSpotResponse(Long id, String spotNumber, ParkingStatus status) {

}
