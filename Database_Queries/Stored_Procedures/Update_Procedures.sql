DELIMITER //

-- Update Procedure for trip table
CREATE PROCEDURE UpdateTrip(
    IN p_trip_id INT,
    IN p_trip_name VARCHAR(50),
    IN p_source_city_id INT,
    IN p_destination_city_id INT,
    IN p_start_date DATE,
    IN p_trip_days INT,
    IN p_trip_budget INT,
    IN p_no_of_persons INT
)
BEGIN
    -- Check if source_city_id exists in the city table, if not, insert it
    IF p_source_city_id IS NOT NULL THEN
        INSERT IGNORE INTO city (city_id) VALUES (p_source_city_id);
    END IF;

    -- Check if destination_city_id exists in the city table, if not, insert it
    IF p_destination_city_id IS NOT NULL THEN
        INSERT IGNORE INTO city (city_id) VALUES (p_destination_city_id);
    END IF;

    -- Update data in the trip table
    UPDATE trip SET
        trip_name = COALESCE(p_trip_name, trip_name),
        source_city_id = COALESCE(p_source_city_id, source_city_id),
        destination_city_id = COALESCE(p_destination_city_id, destination_city_id),
        start_date = COALESCE(p_start_date, start_date),
        trip_days = COALESCE(p_trip_days, trip_days),
        trip_budget = COALESCE(p_trip_budget, trip_budget),
        no_of_persons = COALESCE(p_no_of_persons, no_of_persons)
    WHERE trip_id = p_trip_id;
END //


-- Update Procedure for chat table
CREATE PROCEDURE UpdateChat(
    IN p_chat_id INT,
    IN p_group_id INT,
    IN p_user_id INT,
    IN p_message VARCHAR(500) NOT NULL,
    IN p_image_id INT,
    IN p_chat_time TIME
)
BEGIN
    -- Check if user_id exists in the user table, if not, insert it
    IF p_user_id IS NOT NULL THEN
        INSERT IGNORE INTO user (user_id) VALUES (p_user_id);
    END IF;

    -- Check if group_id exists in the group table, if not, insert it
    IF p_group_id IS NOT NULL THEN
        INSERT IGNORE INTO group (group_id) VALUES (p_group_id);
    END IF;

    -- Check if image_id exists in the image table, if not, insert it
    IF p_image_id IS NOT NULL THEN
        INSERT IGNORE INTO image (image_id) VALUES (p_image_id);
    END IF;

    -- Update data in the chat table
    UPDATE chat SET
        group_id = COALESCE(p_group_id, group_id),
        user_id = COALESCE(p_user_id, user_id),
        message = COALESCE(p_message, message),
        image_id = COALESCE(p_image_id, image_id),
        chat_time = COALESCE(p_chat_time, chat_time)
    WHERE ch

DELIMITER ;
