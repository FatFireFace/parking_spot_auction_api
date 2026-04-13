-- Заполнение тестовых данных


INSERT INTO parking_spot (spot_number, status) VALUES
('A-01', 'AVAILABLE'),
('A-02', 'AVAILABLE'),
('A-03', 'BOOKED'),
('B-01', 'AVAILABLE'),
('B-02', 'SOLD'),
('C-01', 'AVAILABLE');

INSERT INTO auction (name, start_date, end_date) VALUES
('Весенний аукцион 2026', '2026-03-01 10:00:00', '2026-03-10 18:00:00'),
('Летний аукцион 2026', '2026-06-01 10:00:00', '2026-06-15 18:00:00');

INSERT INTO auction_lot (auction_id, parking_spot_id) VALUES (1, 1);
INSERT INTO auction_lot (auction_id, parking_spot_id) VALUES (1, 2);
INSERT INTO auction_lot (auction_id, parking_spot_id) VALUES (2, 4);
INSERT INTO auction_lot (auction_id, parking_spot_id) VALUES (2, 1);