DELIMITER //

--* Insertion Procedure retrieve password by email
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


--* Insertion Procedure to add Image related to Location
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


--* Insertion Procedure to add user to group
CREATE PROCEDURE AddUserToGroup(
    IN p_user_id INT,
    IN p_group_id INT
)
BEGIN
    -- Check if user_id exists in the user table, if not, cancel the insertion
    IF NOT EXISTS (SELECT * FROM `user` WHERE user_id = p_user_id) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'User does not exist';
    END IF;

    -- Check if group_id exists in the group table, if not, cancel the insertion
    IF NOT EXISTS (SELECT * FROM `group` WHERE group_id = p_group_id) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Group does not exist';
    END IF;

    -- Insert data into the user_to_group_relation table
    INSERT INTO `user_to_group_relation` (user_id, group_id)
    VALUES (p_user_id, p_group_id);
END //


--* Remove Procedure to remove user from group
CREATE PROCEDURE RemoveUserFromGroup(
    IN p_user_id INT,
    IN p_group_id INT
)
BEGIN
    -- Check if user_id and group_id combination exists in the user_to_group_relation table, if not, cancel the deletion
    IF NOT EXISTS (
        SELECT * FROM `user_to_group_relation`
        WHERE user_id = p_user_id AND group_id = p_group_id
    ) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'User to Group relation does not exist';
    END IF;

    -- Delete data from the user_to_group_relation table
    DELETE FROM `user_to_group_relation` WHERE user_id = p_user_id AND group_id = p_group_id;
END //
DELIMITER ;
