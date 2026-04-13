package api.DTO;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
public class AuctionLotResponse {
    private Long lotId;
    private Long auctionId;
    private String auctionName;
    private Long parkingSpotId;
    private String parkingSpotNumber;
    private LocalDateTime createdAt;
}
