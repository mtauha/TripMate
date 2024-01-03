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

// Check if any required parameter is missing
if (!$trip_id) {
    $arr["success"] = "false";
    $arr["message"] = "Missing required parameter $trip_id";
    echo json_encode($arr);
    return;
}

// Insert budget data using a prepared statement
$query = "SELECT amount, reason FROM budget WHERE trip_id = '$trip_id'";
$result=mysqli_query($con, $query);
if ($result) {
    $arr["success"] = "true";
    $arr["budget"] = array();
    while ($row = mysqli_fetch_assoc($result)) {
        $arr["budget"][] = $row;
    }
} else {
    $arr["success"] = "false";
}

echo json_encode($arr);

?>