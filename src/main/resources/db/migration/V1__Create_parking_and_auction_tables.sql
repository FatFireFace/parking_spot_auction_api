CREATE TABLE parking_spot (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    spot_number VARCHAR(20) NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL CHECK (status IN ('AVAILABLE', 'BOOKED', 'SOLD'))
);

CREATE TABLE auction (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    start_date TIMESTAMP,
    end_date TIMESTAMP
);

CREATE TABLE auction_lot (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    auction_id BIGINT NOT NULL,
    parking_spot_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_auction_lot_auction FOREIGN KEY (auction_id) REFERENCES auction(id) ON DELETE CASCADE,
    CONSTRAINT fk_auction_lot_parking_spot FOREIGN KEY (parking_spot_id) REFERENCES parking_spot(id) ON DELETE CASCADE,

    -- Уникальность со стороны бд: одно место не может быть добавлено в один и тот же аукцион дважды
    CONSTRAINT uk_auction_spot UNIQUE (auction_id, parking_spot_id)

);

CREATE INDEX idx_auction_lot_auction_id ON auction_lot(auction_id);
CREATE INDEX idx_auction_lot_parking_spot_id ON auction_lot(parking_spot_id);