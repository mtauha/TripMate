DELIMITER //
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
DELIMITER ;

SET @image_location = null;

DELIMITER //
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
