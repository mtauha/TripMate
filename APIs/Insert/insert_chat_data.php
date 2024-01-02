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
if (!$user_id) {
    $arr["success"] = "false";
    $arr["message"] = "Missing required parameter user_id";
    echo json_encode($arr);
    return;

}
if (!$trip_id) {
    $arr["success"] = "false";
    $arr["message"] = "Missing required parameter trip_id";
    echo json_encode($arr);
    return;

}
if (!$message) {
    $arr["success"] = "false";
    $arr["message"] = "Missing required parameter message";
    echo json_encode($arr);
    return;

}
if (!$chat_time) {
    $arr["success"] = "false";
    $arr["message"] = "Missing required parameter chat_time";
    echo json_encode($arr);
    return;
}


$query =
    "SELECT group_id FROM trip
 NATURAL JOIN `group`
WHERE trip_id = $trip_id ;";

$result = $con->query($query);
$row = $result->fetch_assoc();
$group_id = $row["group_id"];



$query = "CALL InsertChat($group_id, $user_id, '$message', $image_id, '$chat_time')";

$res = mysqli_query($con, $query);
if ($res) {
    $arr["success"] = "true";
    $arr["chat_id"] = $con->insert_id;
} else {
    $arr["success"] = "false";
}

echo json_encode($arr);

?>