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
$body = isset($_GET["body"]) ? $_GET["body"] : null;
$heading = isset($_GET["heading"]) ? $_GET["heading"] : null;
$note_date = isset($_GET["note_date"]) ? $_GET["note_date"] : null;


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
if (!$body) {
    $arr["success"] = "false";
    $arr["message"] = "Missing required parameter body";
    echo json_encode($arr);
    return;

}
if (!$heading) {
    $arr["success"] = "false";
    $arr["message"] = "Missing required parameter heading";
    echo json_encode($arr);
    return;
}
if (!$note_date) {
    $arr["success"] = "false";
    $arr["message"] = "Missing required parameter note_date";
    echo json_encode($arr);
    return;
}


$query = "CALL InsertNote($trip_id, $user_id, '$heading','$body', '$note_date')";

$res = mysqli_query($con, $query);
if ($res) {
    $arr["success"] = "true";
    $arr["chat_id"] = $con->insert_id;
} else {
    $arr["success"] = "false";
}

echo json_encode($arr);

?>