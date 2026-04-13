package api.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AuctionLotRequest {

    @NotNull
    private Long parkingSpotId;
}
