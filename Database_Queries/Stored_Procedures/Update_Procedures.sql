DELIMITER //

--* Update Procedure for trip table
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


--* Update Procedure for chat table
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
    WHERE chat_id = p_chat_id;
END //


--* Update Procedure for country table
CREATE PROCEDURE UpdateCountry(
    IN p_country_id INT,
    IN p_country_name VARCHAR(50)
)
BEGIN
    -- Update data in the country table
    UPDATE country SET
        country_name = COALESCE(p_country_name, country_name)
    WHERE country_id = p_country_id;
END //



--* Update Procedure for city table
CREATE PROCEDURE UpdateCity(
    IN p_city_id INT,
    IN p_city_name VARCHAR(60),
    IN p_country_id INT
)
BEGIN
    -- Check if city_name already exists in the city table
    IF NOT EXISTS (SELECT * FROM city WHERE city_name = p_city_name AND city_id != p_city_id) THEN
        -- Check if country_id exists in the country table, if not, insert it
        IF p_country_id IS NOT NULL THEN
            INSERT IGNORE INTO country (country_id) VALUES (p_country_id);
        END IF;

        -- Update data in the city table
        UPDATE city SET
            city_name = COALESCE(p_city_name, city_name),
            country_id = COALESCE(p_country_id, country_id)
        WHERE city_id = p_city_id;
    END IF;
END //



--* Update Procedure for location table
CREATE PROCEDURE UpdateLocation(
    IN p_location_id INT,
    IN p_city_id INT,
    IN p_location_name VARCHAR(75),
    IN p_location_type ENUM('TOURIST SPOT', 'HOTEL', 'RESTAURANT')
)
BEGIN
    -- Check if location_name already exists in the location table
    IF NOT EXISTS (SELECT * FROM location WHERE location_name = p_location_name AND location_id != p_location_id) THEN
        -- Check if city_id exists in the city table, if not, insert it
        IF p_city_id IS NOT NULL THEN
            INSERT IGNORE INTO city (city_id) VALUES (p_city_id);
        END IF;

        -- Update data in the location table
        UPDATE location SET
            city_id = COALESCE(p_city_id, city_id),
            location_name = COALESCE(p_location_name, location_name),
            location_type = COALESCE(p_location_type, location_type)
        WHERE location_id = p_location_id;
    END IF;
END //



--* Update Procedure for image table
CREATE PROCEDURE UpdateImage(
    IN p_image_id INT,
    IN p_image_data BLOB,
    IN p_image_type ENUM('LOCATION', 'CHAT', 'PROFILE PICTURE')
)
BEGIN
    -- Check if image_type already exists in the image table
    IF NOT EXISTS (SELECT * FROM image WHERE image_type = p_image_type AND image_id != p_image_id) THEN
        -- Update data in the image table
        UPDATE image SET
            image_data = COALESCE(p_image_data, image_data),
            image_type = COALESCE(p_image_type, image_type)
        WHERE image_id = p_image_id;
    END IF;
END //



--* Update Procedure for user table
CREATE PROCEDURE UpdateUser(
    IN p_user_id INT,
    IN p_user_email VARCHAR(40),
    IN p_password VARCHAR(20),
    IN p_first_name VARCHAR(20),
    IN p_last_name VARCHAR(20),
    IN p_city_id INT,
    IN p_profile_picture_id INT
)
BEGIN
    -- Check if user_email already exists in the user table
    IF NOT EXISTS (SELECT * FROM user WHERE user_email = p_user_email AND user_id != p_user_id) THEN
        -- Check if city_id exists in the city table, if not, insert it
        IF p_city_id IS NOT NULL THEN
            INSERT IGNORE INTO city (city_id) VALUES (p_city_id);
        END IF;

        -- Check if profile_picture_id exists in the image table, if not, insert it
        IF p_profile_picture_id IS NOT NULL THEN
            INSERT IGNORE INTO image (image_id) VALUES (p_profile_picture_id);
        END IF;

        -- Update data in the user table
        UPDATE user SET
            user_email = COALESCE(p_user_email, user_email),
            password = COALESCE(p_password, password),
            first_name = COALESCE(p_first_name, first_name),
            last_name = COALESCE(p_last_name, last_name),
            city_id = COALESCE(p_city_id, city_id),
            profile_picture_id = COALESCE(p_profile_picture_id, profile_picture_id)
        WHERE user_id = p_user_id;
    END IF;
END //




--* Update Procedure for group table
CREATE PROCEDURE UpdateGroup(
    IN p_group_id INT,
    IN p_group_name VARCHAR(40),
    IN p_trip_id INT
)
BEGIN
    -- Check if group_name already exists in the group table
    IF NOT EXISTS (SELECT * FROM `group` WHERE group_name = p_group_name AND group_id != p_group_id) THEN
        -- Check if trip_id exists in the trip table, if not, insert it
        IF p_trip_id IS NOT NULL THEN
            INSERT IGNORE INTO trip (trip_id) VALUES (p_trip_id);
        END IF;

        -- Update data in the group table
        UPDATE `group` SET
            group_name = COALESCE(p_group_name, group_name),
            trip_id = COALESCE(p_trip_id, trip_id)
        WHERE group_id = p_group_id;
    END IF;
END //



--* Update Procedure for notification table
CREATE PROCEDURE UpdateNotification(
    IN p_notification_id INT,
    IN p_notification_text VARCHAR(200),
    IN p_read_status BOOLEAN,
    IN p_user_id INT,
    IN p_group_id INT
)
BEGIN
    -- Check if user_id exists in the user table, if not, insert it
    IF p_user_id IS NOT NULL THEN
        INSERT IGNORE INTO `user` (user_id) VALUES (p_user_id);
    END IF;

    -- Check if group_id exists in the group table, if not, insert it
    IF p_group_id IS NOT NULL THEN
        INSERT IGNORE INTO `group` (group_id) VALUES (p_group_id);
    END IF;

    -- Update data in the notification table
    UPDATE notification SET
        notification_text = COALESCE(p_notification_text, notification_text),
        read_status = COALESCE(p_read_status, read_status),
        user_id = COALESCE(p_user_id, user_id),
        group_id = COALESCE(p_group_id, group_id)
    WHERE notification_id = p_notification_id;
END //




--* Update Procedure for review table
CREATE PROCEDURE UpdateReview(
    IN p_review_id INT,
    IN p_review_text VARCHAR(200),
    IN p_review_stars ENUM('1', '2', '3', '4', '5'),
    IN p_location_id INT
)
BEGIN
    -- Check if location_id exists in the location table, if not, insert it
    IF p_location_id IS NOT NULL THEN
        INSERT IGNORE INTO location (location_id) VALUES (p_location_id);
    END IF;

    -- Update data in the review table
    UPDATE review SET
        review_text = COALESCE(p_review_text, review_text),
        review_stars = COALESCE(p_review_stars, review_stars),
        location_id = COALESCE(p_location_id, location_id)
    WHERE review_id = p_review_id;
END //


DELIMITER ;
