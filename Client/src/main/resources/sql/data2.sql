-- Insert data into Client table
INSERT INTO client DEFAULT VALUES;
INSERT INTO client DEFAULT VALUES;
INSERT INTO client DEFAULT VALUES;
INSERT INTO client DEFAULT VALUES;

-- Insert data into Account table
INSERT INTO account (client_id, account_id, last_name, name, phone_number)
VALUES
    (1, '04e023a3-66a2-474e-bf5e-b33811f070b2', 'Janssens', 'Peter', '0499123456'),
    (null,'123e4567-e89b-12d3-a456-426614174002', 'Peeters', 'Marie', '0478123456'),
    (null,'123e4567-e89b-12d3-a456-426614174003', 'Maes', 'Luc', '0487123456'),
    (2, '123e4567-e89b-12d3-a456-426614174004', 'Claes', 'Sophie', '0476123456'),
    (null, '123e4567-e89b-12d3-a456-426614174005', 'Martens', 'Jan', '0495123456'),
    (null, '123e4567-e89b-12d3-a456-426614174006', 'Jacobs', 'Karen', '0474123456'),
    (null, '123e4567-e89b-12d3-a456-426614174007', 'Bogaerts', 'David', '0488123456'),
    (3, '123e4567-e89b-12d3-a456-426614174008', 'Vandenberghe', 'Sofie', '0466123456'),
    (4, '123e4567-e89b-12d3-a456-426614174009', 'Lambrechts', 'Tom', '0492123456'),
    (null, '123e4567-e89b-12d3-a456-426614174010', 'Verschueren', 'Anna', '0470123456');

-- Insert data into Company_DB table
INSERT INTO company_tbl (name, vatnumber)
VALUES
    ('Company X', 'BE0123456789'),
    ('Company Y', 'BE9876543210'),
    ('Company Z', 'BE1122334455'),
    ('Company A', 'BE9988776655'),
    ('Company B', 'BE6655443322');

-- Insert data into Address table
INSERT INTO address (company_id, house_number, city, country, postal_code, street)
VALUES
    (1, '123', 'Brussels', 'Belgium', '1000', 'Rue de la Loi 1'),
    (2, '456', 'Antwerp', 'Belgium', '2000', 'Meir 25'),
    (3, '789', 'Ghent', 'Belgium', '9000', 'Gravensteenstraat 10'),
    (4, '101', 'Bruges', 'Belgium', '8000', 'Belfortstraat 5'),
    (5, '202', 'Liège', 'Belgium', '4000', 'Rue Saint-Gilles 15');

-- Insert data into Company_DB_Client table
INSERT INTO company_tbl_client (client_id, company_tbl_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 4),
    (4, 5);

INSERT INTO loyalty_event (points, client_id, local_date_time)
VALUES
    (500, 1, '2023-01-01 00:00:00'),
    (500, 2, '2023-01-01 00:00:00'),
    (1500, 2, '2023-01-01 00:00:00'),
    (150, 1, '2023-01-01 00:00:00'),
    (1200, 4, '2023-01-01 00:00:00'),
    (230, 3, '2023-01-01 00:00:00');