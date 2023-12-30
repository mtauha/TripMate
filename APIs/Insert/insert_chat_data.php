<?php

include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\connection.php";

$arr = [];

$con = dbConnection();


if ($con->connect_error) {
    $arr["connection"] = "Failed";
    die("Connection failed: " . $con->connect_error);
}


// Validate and sanitize the input parameters
$group_id = isset($_GET["group_id"]) ? $_GET["group_id"] : null;
$user_id = isset($_GET["user_id"]) ? $_GET["user_id"] : null;
$message = isset($_GET["message"]) ? $_GET["message"] : null;
$image_id = isset($_GET["image_id"]) ? $_GET["image_id"] : null;
$chat_time = isset($_GET["chat_time"]) ? $_GET["chat_time"] : null;

// Check if any required parameter is missing
if (!$group_id || !$user_id || !$message || !$image_id || !$chat_time) {
    $arr["success"] = "false";
    echo json_encode($arr);
    return;
}

$query = "CALL InsertChat('$group_id', '$user_id', '$message', '$image_id', '$chat_time')";

$res = mysqli_query($con, $query);
if ($res) {
    $arr["success"] = "true";
    $arr["chat_id"] = $con->insert_id;
} else {
    $arr["success"] = "false";
}

echo json_encode($arr);

?>