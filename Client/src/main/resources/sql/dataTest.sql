INSERT INTO client DEFAULT VALUES;
INSERT INTO client DEFAULT VALUES;

INSERT INTO account (client_id, account_id, last_name, name, phone_number)
VALUES
    (1, '3dc99fa5-90aa-48e4-b76d-b45bc65c6d80', 'Hansen', 'Maxim', '0499123456'),
    (2, 'b03a604f-112d-4b67-a0a0-5aa228d8877f', 'Hansen', 'Maxim', '0473389512');

INSERT INTO company_tbl (name, vatnumber)
VALUES
    ('Company X', 'BE0123456789');

INSERT INTO address (company_id, house_number, city, country, postal_code, street)
VALUES
    (1, '123', 'Brussels', 'Belgium', '1000', 'Rue de la Loi 1');

INSERT INTO company_tbl_client (client_id, company_tbl_id)
VALUES
    (1, 1),
    (2, 1);

INSERT INTO loyalty_event (points, client_id, local_date_time)
VALUES
    (500, 1, '2023-01-01 00:00:00'),
    (2000, 2, '2023-01-01 00:00:00');

INSERT INTO order_tbl (client_id, order_id, order_date, total_price)
VALUES
    (1, '8b8691e7-0287-4f1d-bbaf-1e10be917e96', '2023-01-01 00:00:00', 10.0),
    (1, 'd1f6a9d2-7df5-4a78-8e1d-d20e8125e8a2', '2023-01-01 00:00:00', 5000.0),
    (1, '90e8d6c6-3d03-46b3-b15f-6aaad2f9478a', '2023-01-01 00:00:00', 4000.0),
    (1, '550e8400-e29b-41d4-a716-446655440000', '2023-01-01 00:00:00', 5000.0),
    (2, 'd7f5c51d-ff67-4b88-a4a5-cc1627aa7469', '2023-01-01 00:00:00', 5000.0);

INSERT INTO order_line (order_id, product_number, quantity)
VALUES
    ('8b8691e7-0287-4f1d-bbaf-1e10be917e96', '6f8d5a2b-aa4e-4b71-a33d-182ccda1d3d0', 1),
    ('d1f6a9d2-7df5-4a78-8e1d-d20e8125e8a2', '6f8d5a2b-aa4e-4b71-a33d-182ccda1d3d0', 1),
    ('550e8400-e29b-41d4-a716-446655440000', '6f8d5a2b-aa4e-4b71-a33d-182ccda1d3d0', 1),
    ('90e8d6c6-3d03-46b3-b15f-6aaad2f9478a', '6f8d5a2b-aa4e-4b71-a33d-182ccda1d3d0', 1),
    ('d7f5c51d-ff67-4b88-a4a5-cc1627aa7469', '6f8d5a2b-aa4e-4b71-a33d-182ccda1d3d0', 1);

INSERT INTO order_event (order_id, order_state, date_time)
VALUES
    ('8b8691e7-0287-4f1d-bbaf-1e10be917e96', 'ORDER_PLACED', '2023-01-01 00:00:00'),
    ('d1f6a9d2-7df5-4a78-8e1d-d20e8125e8a2', 'ORDER_PLACED', '2023-01-01 00:00:00'),
    ('90e8d6c6-3d03-46b3-b15f-6aaad2f9478a', 'ORDER_CANCELLED', '2023-01-01 00:00:00'),
    ('550e8400-e29b-41d4-a716-446655440000', 'ORDER_CONFIRMED', '2023-01-01 00:00:00'),
    ('d7f5c51d-ff67-4b88-a4a5-cc1627aa7469', 'ORDER_CONFIRMED', '2023-01-01 00:00:00');