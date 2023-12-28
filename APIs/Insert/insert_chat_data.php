<?php

include "D:\study\Projects\Kotlin\TripMate\APIs\connection.php";

$arr = [];

$con = dbConnection();

if (!$con) {
    $arr["success"] = "false";
    $arr["message"] = "Connection Error";
    echo json_encode($arr);
    return;
}

// Validate and sanitize the input parameters
$chat_id = isset($_POST["chat_id"]) ? $_POST["chat_id"] : null;
$group_id = isset($_POST["group_id"]) ? $_POST["group_id"] : null;
$user_id = isset($_POST["user_id"]) ? $_POST["user_id"] : null;
$message = isset($_POST["message"]) ? $_POST["message"] : null;
$image_id = isset($_POST["image_id"]) ? $_POST["image_id"] : null;
$chat_time = isset($_POST["chat_time"]) ? $_POST["chat_time"] : null;

// Check if any required parameter is missing
if (!$chat_id || !$group_id || !$user_id || !$message || !$image_id || !$chat_time) {
    $arr["success"] = "false";
    echo json_encode($arr);
    return;
}

$query = "CALL InsertChat('$chat_id', '$group_id', '$user_id', '$message', '$image_id', '$chat_time')";

$res = mysqli_query($con, $query);
if ($res) {
    $arr["success"] = "true";
    $arr["chat_id"] = mysqli_insert_id($con);
} else {
    $arr["success"] = "false";
}

echo json_encode($arr);

?>