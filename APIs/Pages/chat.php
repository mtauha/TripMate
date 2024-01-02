<?php

include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\connection.php";
include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\Retrieve\\retrieve_chat_data.php";

$arr = [];
$trip_id = $_GET["trip_id"];

$con = dbConnection();

if ($con->connect_error) {
    $arr["connection"] = "Failed";
    die("Connection failed: " . $con->connect_error);
}

echo json_encode(retrieveChatData($con, $trip_id), JSON_PRETTY_PRINT);

?>