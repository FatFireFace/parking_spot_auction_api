package Controller;

import DTO.AuctionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.AuctionService;

import java.util.List;

@RestController
@RequestMapping("/api/auctions")
@RequiredArgsConstructor
@Tag(name = "Аукционы", description = "Получение информации об аукционах")
public class AuctionController {
    private final AuctionService auctionService;

    @GetMapping
    @Operation(summary = "Получить список всех аукционов")
    public List<AuctionResponse> getAllAuctions() {
        return auctionService.getAllAuctions();
    }
}
