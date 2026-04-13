package repositories;

import entities.AuctionLot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionLotRepository extends JpaRepository<AuctionLot, Long> {

    public Boolean existsByAuctionIdAndParkingSpotId(Long auctionId, Long spotId);

    public void deleteByAuctionIdAndParkingSpotId(Long auctionId, Long spotId);

    public List<AuctionLot> findByAuctionId(Long auctionId);
}
