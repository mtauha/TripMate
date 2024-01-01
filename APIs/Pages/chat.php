<?php

include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\connection.php";
include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\Retrieve\\retrieve_chat_data.php";

$arr = [];
$user_id = $_GET["user_id"];

$con = dbConnection();

if ($con->connect_error) {
    $arr["connection"] = "Failed";
    die("Connection failed: " . $con->connect_error);
}

// $query =
//     "SELECT chat_id FROM chat
// WHERE user_id = '$user_id' ";

// Pass the array of trip_id values to retrieveTripData function
echo json_encode(retrieveChatData($con, $user_id), JSON_PRETTY_PRINT);

?>