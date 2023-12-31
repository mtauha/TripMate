USE tripdb;
SET @image_location = null;

-- * Insertion Procedure for trip table
DELIMITER //
CREATE PROCEDURE InsertTrip(
    IN p_trip_name VARCHAR(50),
    IN p_source_city_id INT,
    IN p_destination_city_id INT,
    IN p_start_date DATE,
    IN p_trip_days INT,
    IN p_trip_budget INT,
    IN p_no_of_persons INT,
    IN p_admin_id INT
)
BEGIN
    -- Check if source_city_id exists in the city table, if not, insert it
    INSERT IGNORE INTO city (city_id) VALUES (p_source_city_id);

    -- Check if destination_city_id exists in the city table, if not, insert it
    INSERT IGNORE INTO city (city_id) VALUES (p_destination_city_id);

    -- Insert data into the trip table
    INSERT INTO trip (
        trip_name, source_city_id, destination_city_id, 
        start_date, trip_days, trip_budget, no_of_persons, admin_id
    ) VALUES (
        p_trip_name, p_source_city_id, p_destination_city_id,
        p_start_date, p_trip_days, p_trip_budget, p_no_of_persons, p_admin_id
    );

    -- Get the auto-generated trip_id
    SET @new_trip_id = LAST_INSERT_ID();

    -- Additional logic here using @new_trip_id
END //



-- * Chat Table:
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


-- * Country Table:
CREATE PROCEDURE InsertCountry(
    IN p_country_name VARCHAR(50)
)
BEGIN
    -- Check if country_name already exists in the country table
    IF NOT EXISTS (SELECT * FROM country WHERE country_name = p_country_name) THEN
        INSERT INTO country (country_name) VALUES (p_country_name);
    END IF;
END //



-- * City Table:
CREATE PROCEDURE InsertCity(
    IN p_city_name VARCHAR(60),
    IN p_country_id INT
)
BEGIN
    -- Check if city_name already exists in the city table
    IF NOT EXISTS (SELECT * FROM city WHERE city_name = p_city_name) THEN
        -- Check if country_id exists in the country table, if not, insert it
        INSERT IGNORE INTO country (country_id) VALUES (p_country_id);
        
        -- Insert data into the city table
        INSERT INTO city (city_name, country_id) VALUES (p_city_name, p_country_id);
    END IF;
END //


-- * Location Table:
CREATE PROCEDURE InsertLocation(
    IN p_city_id INT,
    IN p_location_name VARCHAR(75),
    IN p_location_type ENUM('TOURIST SPOT', 'HOTEL', 'RESTAURANT')
)
BEGIN
    -- Check if location_name already exists in the location table
    IF NOT EXISTS (SELECT * FROM location WHERE location_name = p_location_name) THEN
        -- Check if city_id exists in the city table, if not, insert it
        INSERT IGNORE INTO city (city_id) VALUES (p_city_id);

        -- Insert data into the location table
        INSERT INTO location (city_id, location_name, location_type)
        VALUES (p_city_id, p_location_name, p_location_type);
    END IF;
END //



-- * Image Table:
CREATE PROCEDURE InsertImage(
    IN p_image_data BLOB,
    IN p_image_type ENUM('LOCATION', 'CHAT', 'PROFILE PICTURE')
)
BEGIN
    -- Check if image_type already exists in the image table
    IF NOT EXISTS (SELECT * FROM image WHERE image_type = p_image_type) THEN
        -- Insert data into the image table
        INSERT INTO image (image_data, image_type)
        VALUES (p_image_data, p_image_type);
    END IF;
END //



-- * User Table:
CREATE PROCEDURE InsertUser(
    IN p_user_email VARCHAR(40),
    IN p_password VARCHAR(20),
    IN p_first_name VARCHAR(20),
    IN p_last_name VARCHAR(20),
    IN p_city_id INT,
    IN p_profile_picture_id INT
)
BEGIN
    -- Check if user_email already exists in the user table
    IF NOT EXISTS (SELECT * FROM user WHERE user_email = p_user_email) THEN
        -- Check if city_id exists in the city table, if not, insert it
        IF p_city_id IS NOT NULL THEN
            INSERT IGNORE INTO city (city_id) VALUES (p_city_id);
        END IF;

        -- Check if profile_picture_id exists in the image table, if not, insert it
        IF p_profile_picture_id IS NOT NULL THEN
            INSERT IGNORE INTO image (image_id) VALUES (p_profile_picture_id);
        END IF;

        -- Insert data into the user table
        INSERT INTO user (
            user_email, password, first_name, last_name, city_id, profile_picture_id
        ) VALUES (
            p_user_email, p_password, p_first_name, p_last_name, p_city_id, p_profile_picture_id
        );
    END IF;
END //




-- * Group Table:
CREATE PROCEDURE InsertGroup(
    IN p_group_name VARCHAR(40),
    IN p_trip_id INT
)
BEGIN
    -- Check if group_name already exists in the group table
    IF NOT EXISTS (SELECT * FROM `group` WHERE group_name = p_group_name) THEN
        -- Check if trip_id exists in the trip table, if not, insert it
        IF p_trip_id IS NOT NULL THEN
            INSERT IGNORE INTO trip (trip_id) VALUES (p_trip_id);
        END IF;

        -- Insert data into the group table
        INSERT INTO `group` (group_name, trip_id)
        VALUES (p_group_name, p_trip_id);
    END IF;
END //



-- * Notification Table:
CREATE PROCEDURE InsertNotification(
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

    -- Insert data into the notification table
    INSERT INTO notification (notification_text, read_status, user_id, group_id)
    VALUES (p_notification_text, p_read_status, p_user_id, p_group_id);
END //



-- * Review Table:
CREATE PROCEDURE InsertReview(
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

    -- Insert data into the review table
    INSERT INTO review (review_id, review_text, review_stars, location_id)
    VALUES (p_review_id, p_review_text, p_review_stars, p_location_id);
END //

DELIMITER ;
