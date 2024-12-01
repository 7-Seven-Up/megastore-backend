DELIMITER $$

CREATE PROCEDURE IF NOT EXISTS SP_FIND_ORDERS_BY_STATE(
    IN date_from_in DATETIME,
    IN date_to_in DATETIME,
    OUT in_progress_out INTEGER,
    OUT finished_out INTEGER,
    OUT in_delivery_out INTEGER,
    OUT delivered_out INTEGER,
    OUT cancelled_out INTEGER,
    OUT total_out INTEGER
)
BEGIN
    SELECT IFNULL(SUM(state = 'IN_PROGRESS'), 0),
           IFNULL(SUM(state = 'FINISHED'), 0),
           IFNULL(SUM(state = 'IN_DELIVERY'), 0),
           IFNULL(SUM(state = 'DELIVERED'), 0),
           IFNULL(SUM(state = 'CANCELLED'), 0),
           IFNULL(COUNT(*), 0)
    INTO
        in_progress_out,
        finished_out,
        in_delivery_out,
        delivered_out,
        cancelled_out,
        total_out
    FROM orders
    WHERE date BETWEEN date_from_in AND date_to_in;

END$$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE IF NOT EXISTS SP_MORE_SOLD_PRODUCTS(
    IN date_from_in DATE,
    IN date_to_in DATE
)
BEGIN
    SELECT p.name as NAME, SUM(od.quantity) AS QUANTITY, SUM(od.subtotal) AS TOTAL
    FROM products p
             INNER JOIN order_details od ON p.product_id = od.product_product_id
             INNER JOIN orders o ON od.order_order_id = o.order_id
    WHERE o.date BETWEEN date_from_in AND date_to_in
    GROUP BY p.name
    ORDER BY QUANTITY DESC;
END $$

DELIMITER ;