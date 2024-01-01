<?php

include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\connection.php";
include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\Retrieve\\retrieve_user_data.php";
include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\Retrieve\\retrieve_image_data.php";

$arr = [];
$user_email = $_GET["user_email"];

$con = dbConnection();

if ($con->connect_error) {
    $arr["connection"] = "Failed";
    die("Connection failed: " . $con->connect_error);
}

$user_id_query = "SELECT user_id FROM user WHERE user_email = '$user_email'";
$result = mysqli_query($con, $user_id_query);
if (mysqli_num_rows($result) > 0) {
    $row = mysqli_fetch_assoc($result);
    $user_id = $row["user_id"];
} else {
    $arr["success"] = false;
    $arr["error"] = "User with user_email $user_email not found";
    die(json_encode($arr));
}


// Retrieve user data
$userData = retrieveUserData($con, ["user_id" => $user_id]);
$userData = json_decode($userData, true);  // Added true to decode as associative array


$city_id = $userData["user_data"][0]["city_id"];
$query_to_retrieve_cityname = "SELECT city_name FROM city WHERE city_id = $city_id";
$result = mysqli_query($con, $query_to_retrieve_cityname);

if ($result) {
    $row = mysqli_fetch_assoc($result);
    $city_name = $row['city_name'];
} else {
    echo "Error: " . mysqli_error($con);
}


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