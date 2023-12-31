DROP DATABASE IF EXISTS TripDB;
CREATE DATABASE TripDB;
USE TripDB;

CREATE TABLE `country`(
	country_id INT AUTO_INCREMENT,
    country_name VARCHAR(50),
    
    CONSTRAINT country_country_id_constraint
    PRIMARY KEY(country_id)
);

CREATE TABLE `city`(
	city_id INT AUTO_INCREMENT,
    city_name VARCHAR(60),
    country_id INT,
    
    CONSTRAINT city_city_id_constraint
    PRIMARY KEY(city_id),
    
    CONSTRAINT city_country_id_constraint
    FOREIGN KEY (country_id) REFERENCES country (country_id)
);

CREATE TABLE `location`(
	location_id INT AUTO_INCREMENT,
    city_id INT,
    location_name VARCHAR(75),
    location_type ENUM('TOURIST SPOT', 'HOTEL', 'RESTURAUNT'),
    
    CONSTRAINT location_location_id_constraint
    PRIMARY KEY (location_id),
    
    CONSTRAINT location_city_id_constraint
    FOREIGN KEY (city_id) REFERENCES city (city_id)
);

CREATE TABLE `trip`(
	trip_id INT AUTO_INCREMENT,
    trip_name VARCHAR(50),
    source_city_id INT,
    destination_city_id INT,
    start_date DATE,
    trip_days INT,
    trip_budget INT,
    no_of_persons INT,
    
    CONSTRAINT trip_trip_id_constraint 
    PRIMARY KEY(trip_id),
    
    CONSTRAINT trip_source_city_id_constraint
    FOREIGN KEY (source_city_id) REFERENCES city (city_id),
    
	CONSTRAINT trip_destination_city_id_constraint
    FOREIGN KEY (destination_city_id) REFERENCES city (city_id)
);

CREATE TABLE `image`(
	image_id INT AUTO_INCREMENT,
    image_data BLOB,
    image_type ENUM('LOCATION', 'CHAT', 'PROFILE PICTURE'),
    
	CONSTRAINT image_image_id_constraint
    PRIMARY KEY (image_id)
);

CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `date_of_birth` date DEFAULT NULL,
  `phone_number` int DEFAULT NULL,
  `user_email` varchar(40) DEFAULT NULL,
  `password` varchar(35) DEFAULT NULL,
  `first_name` varchar(20) DEFAULT NULL,
  `last_name` varchar(20) DEFAULT NULL,
  `city_id` int DEFAULT NULL,
  `profile_picture_id` int DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `profile_picture_id` (`profile_picture_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`profile_picture_id`) REFERENCES `image` (`image_id`)
);

CREATE TABLE `group`(
	group_id INT AUTO_INCREMENT,
    group_name VARCHAR(40),
    trip_id INT,
    
    CONSTRAINT group_group_id_constraint
    PRIMARY KEY (group_id),
    
    CONSTRAINT group_trip_id_constraint
    FOREIGN KEY (trip_id) REFERENCES trip (trip_id)
);

CREATE TABLE `user_to_group_relation`(
	user_id INT,
    group_id INT,
    
    CONSTRAINT user_group_composite_constraint
    PRIMARY KEY (user_id, group_id),
    
    CONSTRAINT relation_user_id_constraint
    FOREIGN KEY (user_id) REFERENCES `user` (user_id),
    
	CONSTRAINT relation_group_id_constraint
    FOREIGN KEY (group_id) REFERENCES `group` (group_id)
);

CREATE TABLE `picture_to_location_relation`(
	location_id INT,
    image_id INT,

	CONSTRAINT picture_location_composite_constraint
    PRIMARY KEY (location_id, image_id),
    
    CONSTRAINT relation_location_id_constraint
    FOREIGN KEY (location_id) REFERENCES location (location_id),
    
    CONSTRAINT relation_image_id_constraint
    FOREIGN KEY (image_id) REFERENCES image (image_id)
);

CREATE TABLE `chat`(
	chat_id INT AUTO_INCREMENT,
    group_id INT,
    user_id INT,
    message VARCHAR(500) NOT NULL,
    image_id INT,
    chat_time TIME,
    
    CONSTRAINT chat_chat_id_constraint
    PRIMARY KEY (chat_id),

	CONSTRAINT chat_user_id_constraint
    FOREIGN KEY (`user_id`) REFERENCES `user` (user_id), 
    
    CONSTRAINT chat_group_id_constraint
    FOREIGN KEY (`group_id`) REFERENCES `group` (group_id),
    
	CONSTRAINT group_image_id_constraint
    FOREIGN KEY (image_id) REFERENCES image (image_id)
);

CREATE TABLE `notification`(
	notification_id INT AUTO_INCREMENT,
    notification_text VARCHAR(200),
    read_status BOOLEAN,
    user_id INT,
    group_id INT,
    
    CONSTRAINT notification_notification_id_constraint 
    PRIMARY KEY(notification_id),
    
    CONSTRAINT notification_user_id_constraint
    FOREIGN KEY (user_id) REFERENCES `user` (user_id),
    
	CONSTRAINT notification_group_id_constraint
    FOREIGN KEY (group_id) REFERENCES `group` (group_id)
);

CREATE TABLE review(
	review_id INT,
    review_text VARCHAR(200) NOT NULL,
    review_stars ENUM('1', '2', '3', '4', '5'),
    location_id INT,
    
    CONSTRAINT review_review_id_constraint
    PRIMARY KEY (review_id),
    
    CONSTRAINT review_location_id_constraint
    FOREIGN KEY (location_id) REFERENCES location (location_id)
);
