package api.services;

import api.DTO.AuctionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import api.repositories.AuctionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuctionService {
    private final AuctionRepository auctionRepository;

    public List<AuctionResponse> getAllAuctions() {
        return auctionRepository.findAll().stream()
                .map(a -> new AuctionResponse(a.getId(), a.getName()))
                .toList();
    }
}
