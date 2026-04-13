package api.services;

import api.DTO.ParkingSpotResponse;
import api.entities.ParkingSpot;
import api.enums.ParkingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import api.repositories.ParkingSpotRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParkingSpotService {
    private final ParkingSpotRepository spotRepository;

    public List<ParkingSpotResponse> getAllSpots(ParkingStatus status) {
        List<ParkingSpot> spots = (status != null)
                ? spotRepository.findByStatus(status)
                : spotRepository.findAll();
        return spots.stream()
                .map(s -> new ParkingSpotResponse(s.getId(), s.getSpotNumber(), s.getStatus()))
                .toList();
    }
}
