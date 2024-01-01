<?php

include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\connection.php";
include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\Retrieve\\retrieve_user_data.php";
include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\Retrieve\\retrieve_image_data.php";

function replaceKeyAndValue($array, $oldKey, $newKey, $newValue)
{
    if (array_key_exists($oldKey, $array)) {
        unset($array[$oldKey]);
        $array[$newKey] = $newValue;
    }

    return $array;
}

function getCityName($con, $cityId)
{
    $query = "SELECT city_name FROM city WHERE city_id = $cityId";
    $result = mysqli_query($con, $query);

    if ($result && mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        return $row['city_name'];
    }

    return null;
}


$arr = [];
$user_email = $_GET["user_email"];

$con = dbConnection();

if ($con->connect_error) {
    $arr["connection"] = "Failed";
    die("Connection failed: " . $con->connect_error);
}

$query = "SELECT user_id FROM user WHERE user_email = '$user_email'";
$result = mysqli_query($con, $query);
if ($result && mysqli_num_rows($result) > 0) {
    $row = mysqli_fetch_assoc($result);
    $user_id = $row['user_id'];
} else {
    $arr["error"] = "User not found";
    echo json_encode($arr);
    return;
}

// Retrieve user data
$userData = json_decode(retrieveUserData($con, $_GET),true);


if ($userData) {
    $cityId = $userData["user_data"][0]["city_id"];
    $cityName = getCityName($con, $cityId);

    if ($cityName) {
        $userData["user_data"] = replaceKeyAndValue($userData["user_data"][0], "city_id", "city_name", $cityName);

        // Retrieve image data using the profile_picture_id from user data
        $imageId = $userData["user_data"]["profile_picture_id"];

        if ($imageId) {
            $imageData = json_decode(retrieveImageData($con, ["image_id" => $imageId], ["image_data"]), true);

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
        // Handle the case when city name is not found
        $arr["error"] = "City name not found";
        echo json_encode($arr);
    }
} else {
    // Handle the case when user data is not found
    $arr["error"] = "User not found";
    echo json_encode($arr);
}

mysqli_close($con);
?>