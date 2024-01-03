DROP DATABASE IF EXISTS TripDB;
CREATE DATABASE `tripdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE TripDB;

CREATE TABLE `budget` (
  `budget_id` int NOT NULL AUTO_INCREMENT,
  `trip_id` int DEFAULT NULL,
  `reason` varchar(150) DEFAULT NULL,
  `amount` int DEFAULT NULL,
  PRIMARY KEY (`budget_id`),
  KEY `trip_id` (`trip_id`),
  CONSTRAINT `budget_ibfk_1` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`trip_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `chat` (
  `chat_id` int NOT NULL AUTO_INCREMENT,
  `group_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `message` varchar(500) NOT NULL,
  `image_id` int DEFAULT NULL,
  `chat_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`chat_id`),
  KEY `chat_user_id_constraint` (`user_id`),
  KEY `chat_group_id_constraint` (`group_id`),
  KEY `group_image_id_constraint` (`image_id`),
  CONSTRAINT `chat_group_id_constraint` FOREIGN KEY (`group_id`) REFERENCES `group` (`group_id`),
  CONSTRAINT `chat_user_id_constraint` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `group_image_id_constraint` FOREIGN KEY (`image_id`) REFERENCES `image` (`image_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `city` (
  `city_id` int NOT NULL AUTO_INCREMENT,
  `city_name` varchar(60) DEFAULT NULL,
  `country_id` int DEFAULT NULL,
  PRIMARY KEY (`city_id`),
  KEY `city_country_id_constraint` (`country_id`),
  CONSTRAINT `city_country_id_constraint` FOREIGN KEY (`country_id`) REFERENCES `country` (`country_id`)
) ENGINE=InnoDB AUTO_INCREMENT=43671 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `country` (
  `country_id` int NOT NULL AUTO_INCREMENT,
  `country_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`country_id`)
) ENGINE=InnoDB AUTO_INCREMENT=251 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `group` (
  `group_id` int NOT NULL AUTO_INCREMENT,
  `group_name` varchar(40) DEFAULT NULL,
  `trip_id` int DEFAULT NULL,
  PRIMARY KEY (`group_id`),
  KEY `group_trip_id_constraint` (`trip_id`),
  CONSTRAINT `group_trip_id_constraint` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`trip_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `image` (
  `image_id` int NOT NULL AUTO_INCREMENT,
  `image_data` longblob,
  `image_type` enum('LOCATION','CHAT','PROFILE PICTURE') DEFAULT NULL,
  PRIMARY KEY (`image_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `location` (
  `location_id` int NOT NULL AUTO_INCREMENT,
  `city_id` int DEFAULT NULL,
  `location_name` varchar(75) DEFAULT NULL,
  `location_type` enum('TOURIST SPOT','HOTEL','RESTURAUNT') DEFAULT NULL,
  PRIMARY KEY (`location_id`),
  KEY `location_city_id_constraint` (`city_id`),
  CONSTRAINT `location_city_id_constraint` FOREIGN KEY (`city_id`) REFERENCES `city` (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `notes` (
  `note_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `trip_id` int DEFAULT NULL,
  `body` varchar(1000) DEFAULT NULL,
  `heading` varchar(85) DEFAULT NULL,
  `note_date` date DEFAULT NULL,
  PRIMARY KEY (`note_id`),
  KEY `user_id` (`user_id`),
  KEY `trip_id` (`trip_id`),
  CONSTRAINT `notes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `notes_ibfk_2` FOREIGN KEY (`trip_id`) REFERENCES `trip` (`trip_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `notification` (
  `notification_id` int NOT NULL AUTO_INCREMENT,
  `notification_text` varchar(200) DEFAULT NULL,
  `read_status` tinyint(1) DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `group_id` int DEFAULT NULL,
  PRIMARY KEY (`notification_id`),
  KEY `notification_user_id_constraint` (`user_id`),
  KEY `notification_group_id_constraint` (`group_id`),
  CONSTRAINT `notification_group_id_constraint` FOREIGN KEY (`group_id`) REFERENCES `group` (`group_id`),
  CONSTRAINT `notification_user_id_constraint` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `picture_to_location_relation` (
  `location_id` int NOT NULL,
  `image_id` int NOT NULL,
  PRIMARY KEY (`location_id`,`image_id`),
  KEY `relation_image_id_constraint` (`image_id`),
  CONSTRAINT `relation_image_id_constraint` FOREIGN KEY (`image_id`) REFERENCES `image` (`image_id`),
  CONSTRAINT `relation_location_id_constraint` FOREIGN KEY (`location_id`) REFERENCES `location` (`location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `review` (
  `review_id` int NOT NULL,
  `review_text` varchar(200) NOT NULL,
  `review_stars` enum('1','2','3','4','5') DEFAULT NULL,
  `location_id` int DEFAULT NULL,
  PRIMARY KEY (`review_id`),
  KEY `review_location_id_constraint` (`location_id`),
  CONSTRAINT `review_location_id_constraint` FOREIGN KEY (`location_id`) REFERENCES `location` (`location_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `trip` (
  `trip_id` int NOT NULL AUTO_INCREMENT,
  `trip_name` varchar(50) DEFAULT NULL,
  `source_city_id` int DEFAULT NULL,
  `destination_city_id` int DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `trip_days` int DEFAULT NULL,
  `trip_budget` int DEFAULT NULL,
  `no_of_persons` int DEFAULT NULL,
  `admin_id` int DEFAULT NULL,
  PRIMARY KEY (`trip_id`),
  KEY `trip_source_city_id_constraint` (`source_city_id`),
  KEY `trip_destination_city_id_constraint` (`destination_city_id`),
  KEY `group_admin_id_constraint` (`admin_id`),
  CONSTRAINT `group_admin_id_constraint` FOREIGN KEY (`admin_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `trip_destination_city_id_constraint` FOREIGN KEY (`destination_city_id`) REFERENCES `city` (`city_id`),
  CONSTRAINT `trip_source_city_id_constraint` FOREIGN KEY (`source_city_id`) REFERENCES `city` (`city_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `date_of_birth` date DEFAULT NULL,
  `phone_number` varchar(11) DEFAULT NULL,
  `user_email` varchar(40) DEFAULT NULL,
  `password` varchar(35) DEFAULT NULL,
  `first_name` varchar(20) DEFAULT NULL,
  `last_name` varchar(20) DEFAULT NULL,
  `city_id` int DEFAULT NULL,
  `profile_picture_id` int DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `profile_picture_id` (`profile_picture_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`profile_picture_id`) REFERENCES `image` (`image_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `user_to_group_relation` (
  `user_id` int NOT NULL,
  `group_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`group_id`),
  KEY `relation_group_id_constraint` (`group_id`),
  CONSTRAINT `relation_group_id_constraint` FOREIGN KEY (`group_id`) REFERENCES `group` (`group_id`),
  CONSTRAINT `relation_user_id_constraint` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;