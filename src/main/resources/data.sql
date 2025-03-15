INSERT INTO ASSET(customer_id, asset_name, asset_size, usable_size)
    VALUES (100, 'TRY', 100, 100);
INSERT INTO ASSET(customer_id, asset_name, asset_size, usable_size)
    VALUES (100, 'XAU', 10, 10);
INSERT INTO ASSET(customer_Id, asset_name, asset_size, usable_size)
    VALUES (101, 'TRY', 100, 100);
INSERT INTO ASSET(customer_id, asset_name, asset_size, usable_size)
    VALUES (101, 'XAU', 10, 10);
INSERT INTO ASSET(customer_Id, asset_name, asset_size, usable_size)
    VALUES (102, 'TRY', 100, 100);
INSERT INTO ASSET(customer_id, asset_name, asset_size, usable_size)
    VALUES (102, 'XAU', 10, 10);

INSERT INTO STOCK_ORDER(customer_id, asset_name, order_side, size, price, status, create_date)
    VALUES (100, 'XAU', 1, 3, 20, 2, CURRENT_DATE);