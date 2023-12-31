<?php

include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\connection.php";
include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\Retrieve\\retrieve_location_data.php";

$arr = [];

$con = dbConnection();

if ($con->connect_error) {
    $arr["connection"] = "Failed";
    die("Connection failed: " . $con->connect_error);
}


echo json_encode(retrieveLocationData($con, array("city_name"=>$_GET["city_name"]), $_GET["limit"]), JSON_UNESCAPED_UNICODE);

?>