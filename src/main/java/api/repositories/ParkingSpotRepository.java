package api.repositories;

import api.entities.ParkingSpot;
import api.enums.ParkingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {

    public List<ParkingSpot> findByStatus(ParkingStatus status);
}
