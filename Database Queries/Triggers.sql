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

DELIMITER ;