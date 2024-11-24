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
    SELECT SUM(state = 'IN_PROGRESS'),
           SUM(state = 'FINISHED'),
           SUM(state = 'IN_DELIVERY'),
           SUM(state = 'DELIVERED'),
           SUM(state = 'CANCELLED'),
           COUNT(*)
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