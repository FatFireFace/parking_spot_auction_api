package Controller;

import DTO.AuctionLotRequest;
import DTO.AuctionLotResponse;
import Exceptions.SpotAlreadyInAuctionException;
import Exceptions.SpotNotAvailableException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import services.AuctionLotService;

import java.util.List;

@RestController
@RequestMapping("/api/auctions/{auctionId}/lots")
@RequiredArgsConstructor
@Tag(name = "Лоты аукциона", description = "Управление парковочными местами в аукционе")
public class AuctionLotController {
    @Autowired
    private final AuctionLotService auctionLotService;

    @GetMapping
    @Operation(summary = "Получить список лотов аукциона")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список успешно получен"),
            @ApiResponse(responseCode = "404", description = "Аукцион не найден")
    })
    public List<AuctionLotResponse> getAuctionLots(
            @Parameter(description = "ID аукциона", example = "1")
            @PathVariable Long auctionId) {
        return auctionLotService.getLotsByAuction(auctionId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить парковочное место в аукцион")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Лот успешно создан"),
            @ApiResponse(responseCode = "400", description = "Место не доступно"),
            @ApiResponse(responseCode = "404", description = "Аукцион или место не найдены"),
            @ApiResponse(responseCode = "409", description = "Место уже добавлено в этот аукцион")
    })
    public AuctionLotResponse addParkingSpotToAuction(
            @Parameter(description = "ID аукциона", example = "1")
            @PathVariable Long auctionId,
            @Valid @RequestBody AuctionLotRequest request) throws SpotAlreadyInAuctionException, SpotNotAvailableException {
        return auctionLotService.addParkingSpotToAuction(auctionId, request);
    }

    @DeleteMapping("/{spotId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить парковочное место из аукциона")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Лот успешно удалён"),
            @ApiResponse(responseCode = "404", description = "Лот не найден")
    })
    public void removeParkingSpotFromAuction(
            @Parameter(description = "ID аукциона", example = "1")
            @PathVariable Long auctionId,
            @Parameter(description = "ID парковочного места", example = "1")
            @PathVariable Long spotId) {
        auctionLotService.removeParkingSpotFromAuction(auctionId, spotId);
    }
}
