package Service;

import api.DTO.AuctionLotRequest;
import api.DTO.AuctionLotResponse;
import api.Exceptions.SpotAlreadyInAuctionException;
import api.Exceptions.SpotNotAvailableException;
import api.Mappers.AuctionLotMapper;
import api.entities.Auction;
import api.entities.AuctionLot;
import api.entities.ParkingSpot;
import api.enums.ParkingStatus;
import api.repositories.AuctionLotRepository;
import api.repositories.AuctionRepository;
import api.repositories.ParkingSpotRepository;
import api.services.AuctionLotService;
import jakarta.persistence.EntityNotFoundException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuctionLotServiceTest {

    @Mock private AuctionLotRepository lotRepository;
    @Mock private AuctionRepository auctionRepository;
    @Mock private ParkingSpotRepository spotRepository;
    private AuctionLotService auctionLotService;


    private Auction auction;
    private ParkingSpot availableSpot;
    private ParkingSpot bookedSpot;
    private AuctionLot existingLot;

    @BeforeEach
    void setUp() {
        AuctionLotMapper mapper = new AuctionLotMapper();
        auctionLotService = new AuctionLotService(
                spotRepository, auctionRepository, lotRepository, mapper
        );

        auction = new Auction();
        auction.setId(1L);
        auction.setName("Весенний аукцион");

        availableSpot = new ParkingSpot();
        availableSpot.setId(1L);
        availableSpot.setSpotNumber("A-01");
        availableSpot.setStatus(ParkingStatus.AVAILABLE);

        bookedSpot = new ParkingSpot();
        bookedSpot.setId(2L);
        bookedSpot.setSpotNumber("B-02");
        bookedSpot.setStatus(ParkingStatus.BOOKED);

        existingLot = new AuctionLot(auction, availableSpot);
        ReflectionTestUtils.setField(existingLot, "id", 10L);
    }

    @Test
    void getLotsByAuction_ShouldReturnListOfResponses() {
        when(lotRepository.findByAuctionId(1L)).thenReturn(List.of(existingLot));

        List<AuctionLotResponse> responses = auctionLotService.getLotsByAuction(1L);

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).getAuctionId()).isEqualTo(1L);
        assertThat(responses.get(0).getParkingSpotNumber()).isEqualTo("A-01");
        verify(lotRepository).findByAuctionId(1L);
    }

    @SneakyThrows
    @Test
    void addParkingSpotToAuction_ShouldSucceed_WhenSpotAvailableAndNotInAuction() {
        AuctionLotRequest request = new AuctionLotRequest(1L);
        when(auctionRepository.findById(1L)).thenReturn(Optional.of(auction));
        when(spotRepository.findById(1L)).thenReturn(Optional.of(availableSpot));
        when(lotRepository.existsByAuctionIdAndParkingSpotId(1L, 1L)).thenReturn(false);
        when(lotRepository.save(any(AuctionLot.class))).thenReturn(existingLot);

        AuctionLotResponse response = auctionLotService.addParkingSpotToAuction(1L, request);

        assertThat(response.getParkingSpotId()).isEqualTo(1L);
        verify(lotRepository).save(any(AuctionLot.class));
    }

    @Test
    void addParkingSpotToAuction_ShouldThrow_WhenAuctionNotFound() {
        AuctionLotRequest request = new AuctionLotRequest(1L);
        when(auctionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> auctionLotService.addParkingSpotToAuction(99L, request))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Аукцион с id = 99 не найден.");
    }

    @Test
    void addParkingSpotToAuction_ShouldThrow_WhenSpotNotAvailable() {
        AuctionLotRequest request = new AuctionLotRequest(2L);
        when(auctionRepository.findById(1L)).thenReturn(Optional.of(auction));
        when(spotRepository.findById(2L)).thenReturn(Optional.of(bookedSpot));

        assertThatThrownBy(() -> auctionLotService.addParkingSpotToAuction(1L, request))
                .isInstanceOf(SpotNotAvailableException.class)
                .hasMessageContaining("не доступно для аукциона");
    }

    @Test
    void addParkingSpotToAuction_ShouldThrow_WhenSpotAlreadyInAuction() {
        AuctionLotRequest request = new AuctionLotRequest(1L);
        when(auctionRepository.findById(1L)).thenReturn(Optional.of(auction));
        when(spotRepository.findById(1L)).thenReturn(Optional.of(availableSpot));
        when(lotRepository.existsByAuctionIdAndParkingSpotId(1L, 1L)).thenReturn(true);

        assertThatThrownBy(() -> auctionLotService.addParkingSpotToAuction(1L, request))
                .isInstanceOf(SpotAlreadyInAuctionException.class)
                .hasMessageContaining("уже участвует в этом аукционе");
    }

    @Test
    void removeParkingSpotFromAuction_ShouldSucceed_WhenLotExists() {
        when(lotRepository.existsByAuctionIdAndParkingSpotId(1L, 1L)).thenReturn(true);

        auctionLotService.removeParkingSpotFromAuction(1L, 1L);

        verify(lotRepository).deleteByAuctionIdAndParkingSpotId(1L, 1L);
    }

    @Test
    void removeParkingSpotFromAuction_ShouldThrow_WhenLotNotFound() {
        when(lotRepository.existsByAuctionIdAndParkingSpotId(1L, 1L)).thenReturn(false);

        assertThatThrownBy(() -> auctionLotService.removeParkingSpotFromAuction(1L, 1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Лот не найден");
    }
}

