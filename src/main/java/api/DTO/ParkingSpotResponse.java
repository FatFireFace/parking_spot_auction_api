package api.DTO;


import api.enums.ParkingStatus;

public record ParkingSpotResponse(Long id, String spotNumber, ParkingStatus status) {

}
