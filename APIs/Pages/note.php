<?php

include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\connection.php";
include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\Retrieve\\retrieve_note_data.php";

$arr = [];

$trip_id = isset($_GET["trip_id"]) ? $_GET["trip_id"] : null;
$user_id = isset($_GET["user_id"]) ? $_GET["user_id"] : null;

$con = dbConnection();

if ($con->connect_error) {
    $arr["connection"] = "Failed";
    die("Connection failed: " . $con->connect_error);
}

// $query =
//     "SELECT chat_id FROM chat
// WHERE user_id = '$user_id' ";

// Pass the array of trip_id values to retrieveTripData function
echo json_encode(retrieveNoteData($con, $user_id, $trip_id), JSON_PRETTY_PRINT);

?>