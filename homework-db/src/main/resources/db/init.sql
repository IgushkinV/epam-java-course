INSERT INTO igushkin.customer (customer_name, phone) VALUES ('Vladimir', '+7894561230');
INSERT INTO igushkin.customer (customer_name, phone)VALUES ('Vasiliy', '+7123654789');
INSERT INTO igushkin.customer (customer_name, phone)VALUES ('Veniamin', '+7147852369');
INSERT INTO igushkin.customer (customer_name, phone)VALUES ('Valdemar', '+7951753369');
INSERT INTO igushkin.customer (customer_name, phone)VALUES ('Viktor', '+7321654987');
INSERT INTO igushkin.customer (customer_name, phone)VALUES ('Vladislav', '+789456123');

INSERT INTO igushkin."order" (order_number, customer_id, order_date, total_amount)VALUES (1, 1, '2020-05-05', 123);
INSERT INTO igushkin."order" (order_number, customer_id, order_date, total_amount)VALUES (2, 3, '2021-03-04', 123);
INSERT INTO igushkin."order" (order_number, customer_id, order_date, total_amount)VALUES (3, 5, CURRENT_TIMESTAMP, 123);
INSERT INTO igushkin."order" (order_number, customer_id, order_date, total_amount)VALUES (4, 5, CURRENT_TIMESTAMP, 123);
INSERT INTO igushkin."order" (order_number, customer_id, order_date, total_amount)VALUES (5, 2, CURRENT_TIMESTAMP, 123);
INSERT INTO igushkin."order" (order_number, customer_id, order_date, total_amount)VALUES (6, 1, CURRENT_TIMESTAMP, 123);
INSERT INTO igushkin."order" (order_number, customer_id, order_date, total_amount)VALUES (7, 1, CURRENT_TIMESTAMP, 123);

INSERT INTO igushkin.supplier (company_name, phone) VALUES ('EURO CARS', '+45984512323');
INSERT INTO igushkin.supplier (company_name, phone) VALUES ('EURO BIKES', '+6969696969');
INSERT INTO igushkin.supplier (company_name, phone) VALUES ('RUS CARS', '++78482996699');
INSERT INTO igushkin.supplier (company_name, phone) VALUES ('Bel Cars', '+97456133322');
INSERT INTO igushkin.supplier (company_name, phone) VALUES ('Rus Planes', '+7635789412');
INSERT INTO igushkin.supplier (company_name, phone) VALUES ('French Tanks', '+11223366554');

INSERT INTO igushkin.product (product_name, supplier_id, unit_price, is_discontinued) VALUES ('Ferrari', 1, 100000, false);
INSERT INTO igushkin.product (product_name, supplier_id, unit_price, is_discontinued)VALUES ('Renault', 1, 10000, false);
INSERT INTO igushkin.product (product_name, supplier_id, unit_price, is_discontinued)VALUES ('Vesta', 3, 9000, false);
INSERT INTO igushkin.product (product_name, supplier_id, unit_price, is_discontinued)VALUES ('Ducatti', 2, 50000, false);
INSERT INTO igushkin.product (product_name, supplier_id, unit_price, is_discontinued)VALUES ('Belaz', 4, 30000, false);
INSERT INTO igushkin.product (product_name, supplier_id, unit_price, is_discontinued)VALUES ('Mercedes', 1, 990000, false);
INSERT INTO igushkin.product (product_name, supplier_id, unit_price, is_discontinued)VALUES ('TigerFr', 6, 550000, false);
INSERT INTO igushkin.product (product_name, supplier_id, unit_price, is_discontinued)VALUES ('Sukhoi', 5, 150000000, false);
INSERT INTO igushkin.product (product_name, supplier_id, unit_price, is_discontinued)VALUES ('Alpha Romeo', 1, 25000, false);
INSERT INTO igushkin.product (product_name, supplier_id, unit_price, is_discontinued)VALUES ('Volga', 3, 4000, false);
