package api.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuctionLotRequest {

    @NotNull
    private Long parkingSpotId;
}
