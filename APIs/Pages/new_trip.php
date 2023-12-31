<?php

include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\connection.php";
include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\Insert\\insert_trip_data.php";

$arr = [];

$con = dbConnection();

if ($con->connect_error) {
    $arr["connection"] = "Failed";
    die("Connection failed: " . $con->connect_error);
}


$trip_name = $_GET["trip_name"] ?? "";
$source_city = $_GET["source_city"] ?? "";
$destination_city = $_GET["destination_city"] ?? "";
$start_date = $_GET["start_date"] ?? "";
$trip_days = $_GET["trip_days"] ?? "";
$trip_budget = $_GET["trip_budget"] ?? "";
$no_of_persons = $_GET["no_of_persons"] ?? "";
$admin_id = $_GET["admin_id"] ?? "";

$query_for_sourcecityid_retrieval = "SELECT city_id FROM city WHERE city_name = '$source_city';";
$result = mysqli_query($con, $query_for_sourcecityid_retrieval);

if ($result) {
    $row = mysqli_fetch_assoc($result);
    $source_city_id = (int)$row['city_id'];
} else {
    echo "Error: " . mysqli_error($con);
}

mysqli_free_result($result);


$query_for_destcityid_retrieval = "SELECT city_id FROM city WHERE city_name = '$destination_city';";
$result = mysqli_query($con, $query_for_destcityid_retrieval);

if ($result) {
    $row = mysqli_fetch_assoc($result);
    $destination_city_id = (int)$row['city_id'];

} else {
    echo "Error: " . mysqli_error($con);
}


echo insertTripData($con, $trip_name, $source_city_id, $destination_city_id, (string)$start_date, $trip_days, $trip_budget, $no_of_persons, $admin_id);


?>