DELIMITER //

-- Delete Procedure for trip table
CREATE PROCEDURE DeleteTrip(
    IN p_trip_id INT
)
BEGIN
    -- Delete data from the trip table
    DELETE FROM trip WHERE trip_id = p_trip_id;
END //



-- Delete Procedure for chat table
CREATE PROCEDURE DeleteChat(
    IN p_chat_id INT
)
BEGIN
    -- Delete data from the chat table
    DELETE FROM chat WHERE chat_id = p_chat_id;
END //
DELIMITER ;
