SET @image_location = null;

-- Insertion Procedure for trip table
DELIMITER //
CREATE PROCEDURE InsertTrip(
    IN p_trip_name VARCHAR(50),
    IN p_source_city_id INT,
    IN p_destination_city_id INT,
    IN p_start_date DATE,
    IN p_trip_days INT,
    IN p_trip_budget INT,
    IN p_no_of_persons INT
)
BEGIN
    INSERT INTO trip (
        trip_name, source_city_id, destination_city_id, 
        start_date, trip_days, trip_budget, no_of_persons
    ) VALUES (
        p_trip_name, p_source_city_id, p_destination_city_id,
        p_start_date, p_trip_days, p_trip_budget, p_no_of_persons
    );
END //

CREATE PROCEDURE GetPasswordByEmail
(IN p_email VARCHAR(40), 
OUT p_password VARCHAR(20))
BEGIN
    DECLARE v_password VARCHAR(20);

    SELECT password INTO v_password
    FROM `user`
    WHERE user_email = p_email;

    SET p_password = v_password;
END //


CREATE PROCEDURE addLocationImage
(IN lct_id INT,
IN image BLOB)
BEGIN
    SET @image_location = lct_id;
    INSERT INTO image
    (image_data, image_type)
    VALUES
    (image, 'LOCATION');
END //


DELIMITER ;
