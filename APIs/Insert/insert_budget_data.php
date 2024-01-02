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
$reason = isset($_GET["reason"]) ? $_GET["reason"] : null;
$amount = isset($_GET["amount"]) ? $_GET["amount"] : null;

// Check if any required parameter is missing
if (!$reason || !$trip_id || !$amount) {
    $arr["success"] = "false";
    $arr["message"] = "Missing required parameters";
    echo json_encode($arr);
    return;
}

// Insert budget data using a prepared statement
$query = "CALL InsertBudget(?, ?, ?)";
$stmt = $con->prepare($query);
$stmt->bind_param("iss", $trip_id, $reason, $amount);
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