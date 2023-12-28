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



-- Chat Table:
CREATE PROCEDURE InsertChat(
    IN p_group_id INT,
    IN p_user_id INT,
    IN p_message VARCHAR(500) NOT NULL,
    IN p_image_id INT,
    IN p_chat_time TIME
)
BEGIN
    -- Check if user_id exists in the user table, if not, insert it
    INSERT IGNORE INTO user (user_id) VALUES (p_user_id);

    -- Check if group_id exists in the group table, if not, insert it
    INSERT IGNORE INTO group (group_id) VALUES (p_group_id);

    -- Check if image_id exists in the image table, if not, insert it
    INSERT IGNORE INTO image (image_id) VALUES (p_image_id);

    -- Insert data into the chat table
    INSERT INTO chat (
        group_id, user_id, message, image_id, chat_time
    ) VALUES (
        p_group_id, p_user_id, p_message, p_image_id, p_chat_time
    );
END //


DELIMITER ;
