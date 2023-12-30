<?php

include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\connection.php";
include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\Retrieve\\retrieve_user_data.php";

$arr = [];

$con = dbConnection();

if ($con) {
    $arr["connection"] = "Success";
    echo json_encode($arr);
} else {
    $arr["connection"] = "Failed";
    echo json_encode($arr);
    return;
}

echo retrieveUserData($con, ["user_email" => $_GET["user_email"]], ["password"]);
?>