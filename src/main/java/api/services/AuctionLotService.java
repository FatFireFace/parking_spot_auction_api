package api.services;

import api.DTO.AuctionLotRequest;
import api.DTO.AuctionLotResponse;
import api.Exceptions.SpotAlreadyInAuctionException;
import api.Exceptions.SpotNotAvailableException;
import api.Mappers.AuctionLotMapper;
import api.entities.Auction;
import api.entities.AuctionLot;
import api.entities.ParkingSpot;
import api.enums.ParkingStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import api.repositories.AuctionLotRepository;
import api.repositories.AuctionRepository;
import api.repositories.ParkingSpotRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuctionLotService {

    private final ParkingSpotRepository spotRepository;
    private final AuctionRepository auctionRepository;
    private final AuctionLotRepository lotRepository;
    private final AuctionLotMapper mapper;

    @Transactional
    public AuctionLotResponse addParkingSpotToAuction(Long auctionId, AuctionLotRequest request) throws SpotNotAvailableException, SpotAlreadyInAuctionException {
        log.info("Добавление парковочного места {} в аукцион {}", request.getParkingSpotId(), auctionId);
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new EntityNotFoundException("Аукцион с id = " + auctionId + "не найден." ));

        ParkingSpot spot = spotRepository.findById(request.getParkingSpotId())
                .orElseThrow(() -> new EntityNotFoundException("Парковочное место с id = " + request.getParkingSpotId() + "не найдено."));

        if (spot.getStatus() != ParkingStatus.AVAILABLE){
            log.warn("Поытка добавить место {} со статусом {}", spot.getId(), spot.getStatus());
                throw new SpotNotAvailableException(
                    String.format("Место '%s' не доступно для аукциона (статус '%s')", spot.getSpotNumber(), spot.getStatus())
            );
        }

        //проверка со стороны сервера на дублирование
        if (lotRepository.existsByAuctionIdAndParkingSpotId(auctionId, request.getParkingSpotId())){
             log.warn("Место {} уже добавлено в аукцион {}.", spot.getSpotNumber(), auctionId);
             throw new SpotAlreadyInAuctionException(
                     String.format("Место '%s' уже участвует в этом аукционе", spot.getSpotNumber())
             );
        }

        AuctionLot lot = new AuctionLot(auction, spot);
        AuctionLot savedLot = lotRepository.save(lot);

        //можно добавить publisher для регистрации ивентов и дальнейшей аналитики

        return mapper.mapToResponse(savedLot);
    }

    @Transactional
    public void removeParkingSpotFromAuction(Long auctionId, Long spotId) {
        log.info("Удаление места {} из аукциона {}", spotId, auctionId);

        if (!lotRepository.existsByAuctionIdAndParkingSpotId(auctionId, spotId)) {
            throw new EntityNotFoundException("Лот не найден в указанном аукционе");
        }

        lotRepository.deleteByAuctionIdAndParkingSpotId(auctionId, spotId);

        log.info("Место {} удалено из аукциона {}", spotId, auctionId);
    }

    public List<AuctionLotResponse> getLotsByAuction(Long auctionId) {
        log.debug("Запрос списка лотов для аукциона id={}", auctionId);
        List<AuctionLot> lots = lotRepository.findByAuctionId(auctionId);
        return mapper.mapToResponseList(lots);
    }
}
