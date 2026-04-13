package api.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
public class AuctionLotResponse {
    private Long lotId;
    private Long auctionId;
    private String auctionName;
    private Long parkingSpotId;
    private String parkingSpotNumber;
    private LocalDateTime createdAt;
}
