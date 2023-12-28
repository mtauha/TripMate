DELIMITER //

--* Delete Procedure for trip table
CREATE PROCEDURE DeleteTrip(
    IN p_trip_id INT
)
BEGIN
    -- Delete data from the trip table
    DELETE FROM trip WHERE trip_id = p_trip_id;
END //



--* Delete Procedure for chat table
CREATE PROCEDURE DeleteChat(
    IN p_chat_id INT
)
BEGIN
    -- Delete data from the chat table
    DELETE FROM chat WHERE chat_id = p_chat_id;
END //



--* Delete Procedure for country table
CREATE PROCEDURE DeleteCountry(
    IN p_country_id INT
)
BEGIN
    -- Delete data from the country table
    DELETE FROM country WHERE country_id = p_country_id;
END //


--* Delete Procedure for city table
CREATE PROCEDURE DeleteCity(
    IN p_city_id INT
)
BEGIN
    -- Delete data from the city table
    DELETE FROM city WHERE city_id = p_city_id;
END //



--* Delete Procedure for location table
CREATE PROCEDURE DeleteLocation(
    IN p_location_id INT
)
BEGIN
    -- Delete data from the location table
    DELETE FROM location WHERE location_id = p_location_id;
END //




--* Delete Procedure for image table
CREATE PROCEDURE DeleteImage(
    IN p_image_id INT
)
BEGIN
    -- Delete data from the image table
    DELETE FROM image WHERE image_id = p_image_id;
END //




--* Delete Procedure for user table
CREATE PROCEDURE DeleteUser(
    IN p_user_id INT
)
BEGIN
    -- Delete data from the user table
    DELETE FROM user WHERE user_id = p_user_id;
END //



--* Delete Procedure for group table
CREATE PROCEDURE DeleteGroup(
    IN p_group_id INT
)
BEGIN
    -- Delete data from the group table
    DELETE FROM `group` WHERE group_id = p_group_id;
END //




--* Delete Procedure for notification table
CREATE PROCEDURE DeleteNotification(
    IN p_notification_id INT
)
BEGIN
    -- Delete data from the notification table
    DELETE FROM notification WHERE notification_id = p_notification_id;
END //



--* Delete Procedure for review table
CREATE PROCEDURE DeleteReview(
    IN p_review_id INT
)
BEGIN
    -- Delete data from the review table
    DELETE FROM review WHERE review_id = p_review_id;
END //


DELIMITER ;
