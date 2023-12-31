<?php

include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\connection.php";
include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\Update\\update_user_data.php";

$arr = [];

$con = dbConnection();

if ($con->connect_error) {
    $arr["connection"] = "Failed";
    die("Connection failed: " . $con->connect_error);
}

$user_email = $_GET["user_email"];

$query = "SELECT user_id FROM user WHERE user_email = '$user_email'";
$result = mysqli_query($con, $query);
if (mysqli_num_rows($result) > 0) {
    $row = mysqli_fetch_assoc($result);
    $user_id = $row["user_id"];
} else {
    $arr["success"] = false;
    $arr["error"] = "User with user_email $user_email not found";
    die(json_encode($arr));
}

$oldAttributes = $_GET['oldAttributes'];
$newAttributes = $_GET['newAttributes'];

$result = editUserData($con, $user_id, $oldAttributes, $newAttributes);

if ($result) {
    $arr["success"] = true;
    $arr["message"] = "User data updated successfully";
} else {
    $arr["success"] = false;
    $arr["error"] = "Error in query: " . mysqli_error($con);
}

// You can use the $arr array for the response
echo json_encode($arr);

?>