<?php

include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\connection.php";
include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\Retrieve\\retrieve_user_data.php";

$arr = [];

$con = dbConnection();

if ($con->connect_error) {
    $arr["connection"] = "Failed";
    die("Connection failed: " . $con->connect_error);
}

echo json_encode(retrieveUserData($con, array("user_id" => $_GET["user_id"])));
?>