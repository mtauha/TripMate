<?php

include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\connection.php";

$arr = [];

$con = dbConnection();

if ($con->connect_error) {
    $arr["connection"] = "Failed";
    die("Connection failed: " . $con->connect_error);
}

// Validate and sanitize the input parameters
$trip_id = isset($_GET["trip_id"]) ? $_GET["trip_id"] : null;
$user_id = isset($_GET["user_id"]) ? $_GET["user_id"] : null;
$message = isset($_GET["message"]) ? $_GET["message"] : null;
$image_id = isset($_GET["image_id"]) ? $_GET["image_id"] : null;
$chat_time = isset($_GET["chat_time"]) ? $_GET["chat_time"] : null;

// Check if any required parameter is missing
if (!$user_id || !$trip_id || !$message || !$chat_time) {
    $arr["success"] = "false";
    $arr["message"] = "Missing required parameters";
    echo json_encode($arr);
    return;
}

// Retrieve group_id using a prepared statement
$query = "SELECT group_id FROM trip NATURAL JOIN `group` WHERE trip_id = ?";
$stmt = $con->prepare($query);
$stmt->bind_param("i", $trip_id);
$stmt->execute();
$stmt->bind_result($group_id);
$stmt->fetch();
$stmt->close();

// Insert chat data using a prepared statement
$query = "CALL InsertChat(?, ?, ?, ?, ?)";
$stmt = $con->prepare($query);
$stmt->bind_param("iissi", $group_id, $user_id, $message, $image_id, $chat_time);
$res = $stmt->execute();

if ($res) {
    $arr["success"] = "true";
    $arr["chat_id"] = $con->insert_id;
} else {
    $arr["success"] = "false";
}

$stmt->close();
echo json_encode($arr);

?>