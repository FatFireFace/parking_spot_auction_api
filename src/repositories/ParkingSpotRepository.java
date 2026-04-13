package repositories;

import entities.ParkingSpot;
import enums.ParkingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {

    public List<ParkingSpot> findByStatus(ParkingStatus status);
}
