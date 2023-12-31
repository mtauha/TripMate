DELIMITER //
CREATE TRIGGER before_image_insert
BEFORE INSERT ON `image`
FOR EACH ROW
BEGIN
    IF NEW.image_type = 'LOCATION' THEN
        INSERT INTO picture_to_location_relation (location_id, image_id)
        VALUES (@image_location, NEW.image_id);
    END IF;
	
    IF NEW.image_type = 'PROFILE PICTURE' THEN
        UPDATE `user`
        SET
        profile_picture = NEW.image_id;
    END IF;
    
	IF NEW.image_type = 'CHAT' THEN
        UPDATE `chat`
        SET
        image_id = NEW.image_id;
    END IF;
END;
//

CREATE TRIGGER add_notification
AFTER INSERT ON chat
FOR EACH ROW
BEGIN
    INSERT INTO notification
    (notification_text, read_status, user_id, group_id)
    VALUES
    (NEW.message, false, NEW.user_id, NEW.group_id);
END //



CREATE TRIGGER before_trip_insert
AFTER INSERT ON trip
FOR EACH ROW
BEGIN
    DECLARE new_group_id INT;

    -- Create a new group for the trip
    INSERT INTO `group` (group_name, trip_id)
    VALUES (CONCAT('Group for Trip ', NEW.trip_id), NEW.trip_id);

    -- Get the group_id of the newly created group
    SET new_group_id = LAST_INSERT_ID();

    -- Add the user who created the trip to the group
    INSERT INTO user_to_group_relation (user_id, group_id)
    VALUES (NEW.admin_id, new_group_id);
END;
//


DELIMITER ;