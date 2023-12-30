<?php

include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\connection.php";
include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\Retrieve\\retrieve_user_data.php";
include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\Retrieve\\retrieve_image_data.php";

$arr = [];
$con = dbConnection();

if ($con->connect_error) {
    $arr["connection"] = "Failed";
    die("Connection failed: " . $con->connect_error);
}

// Retrieve user data
$userData = retrieveUserData($con, ["user_id" => $_GET["user_id"]]);
$userData = json_decode($userData, true);  // Added true to decode as associative array

// Check if user data exists
if ($userData) {
    // Retrieve image data using the profile_picture_id from user data
    $im_id = $userData["user_data"][0]["profile_picture_id"];

    if ($im_id) {
        $imageData["image_data"] = json_decode(retrieveImageData($con,
            [
                "image_id" => $im_id
            ],
            [
                "image_data"
            ]
        ), true);  // Added true to decode as associative array

        $user = [
            "data" => $userData["user_data"],
            "profile_pic" => $imageData["image_data"]
        ];

        echo json_encode($user, JSON_PRETTY_PRINT);
    } else {
        // Handle the case when profile picture data is not found
        $user = [
            "data" => $userData["user_data"],
            "profile_pic" => null
        ];

        echo json_encode($user, JSON_PRETTY_PRINT);
    }
} else {
    // Handle the case when user data is not found
    $arr["error"] = "User not found";
    echo json_encode($arr);
}
?>