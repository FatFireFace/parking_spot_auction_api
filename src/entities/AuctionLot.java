package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "auction_lot", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"auction_id", "parking_spot_id"})
})
public class AuctionLot {
    // выделение лота в отдельную сущность для возможности одному и тому же месту участвовать в нескольких аукционах
    //генерируем без сеттеров, чтобы не сломать связь после создания

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_spot_id", nullable = false)
    private ParkingSpot parkingSpot;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public AuctionLot(Auction auction, ParkingSpot parkingSpot){
        this.auction = auction;
        this.parkingSpot = parkingSpot;
    }
}
