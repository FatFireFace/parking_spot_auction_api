package api.Mappers;

import api.DTO.AuctionLotResponse;
import api.entities.AuctionLot;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuctionLotMapper {
    // для настолько маленькой задачи выделять маппер нет большой необходимости, но он здесь для поддержания принципа
    // единственной ответственности и возможной масштабируемости

    public AuctionLotResponse mapToResponse(AuctionLot lot){
        return new AuctionLotResponse(
                lot.getId(),
                lot.getAuction().getId(),
                lot.getAuction().getName(),
                lot.getParkingSpot().getId(),
                lot.getParkingSpot().getSpotNumber(),
                lot.getCreatedAt()
        );
    }

    public List<AuctionLotResponse> mapToResponseList(List<AuctionLot> lots){
        return
                lots.stream().map(this::mapToResponse).toList();
    }
}
