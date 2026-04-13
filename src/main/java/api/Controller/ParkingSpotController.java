package api.Controller;

import api.DTO.ParkingSpotResponse;
import api.enums.ParkingStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import api.services.ParkingSpotService;

import java.util.List;

@RestController
@RequestMapping("/api/parking-spots")
@RequiredArgsConstructor
@Tag(name = "Парковочные места", description = "Просмотр информации о парковочных местах")
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    @GetMapping
    @Operation(summary = "Получить все парковочные места с фильтрацией по статусу")
    public List<ParkingSpotResponse> getAllParkingSpots(
            @Parameter(description = "Фильтр по статусу (AVAILABLE, BOOKED, SOLD)")
            @RequestParam(required = false) ParkingStatus status) {
        return parkingSpotService.getAllSpots(status);
    }
}
