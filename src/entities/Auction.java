package entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "auction")
public class Auction {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private LocalDateTime startDate;
    @Column
    private LocalDateTime endDate;
}
