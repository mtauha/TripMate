<?php

include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\connection.php";
include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\Insert\\insert_user_data.php";

$arr = [];

$con = dbConnection();


if ($con->connect_error) {
    $arr["connection"] = "Failed";
    die("Connection failed: " . $con->connect_error);
}


$user_email = $_GET["user_email"] ?? "";
$password = $_GET["password"] ?? "";
$first_name = $_GET["first_name"] ?? "";
$last_name = $_GET["last_name"] ?? "";
$city_name = $_GET["city_name"] ?? "";


$query_for_cityid_retrieval = "SELECT city_id FROM city WHERE city_name = '$city_name';";
$result = mysqli_query($con, $query_for_cityid_retrieval);

if ($result) {
    $row = mysqli_fetch_assoc($result);
    $city_id = $row['city_id'];
} else {
    echo "Error: " . mysqli_error($con);
}

mysqli_free_result($result);

echo insertUserData($con, $user_email, $password, $first_name, $last_name, $city_id, 0);

?>