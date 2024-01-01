<?php

include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\connection.php";
include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\Update\\update_user_data.php";

$arr = [];

$user_id = isset($_GET["user_id"]) ? $_GET["user_id"] : 'null';
$user_email = isset($_GET["user_email"]) ? "'".  $_GET["user_email"] . "'" : 'null';
$phone_number = isset($_GET["phone_number"]) ? "'" . $_GET["phone_number"] . "'" : 'null';
$date_of_birth = isset($_GET["date_of_birth"]) ? "'" . $_GET["date_of_birth"] . "'" : 'null';
$first_name = isset($_GET["first_name"]) ? "'" . $_GET["first_name"] . "'" : 'null';
$last_name = isset($_GET["last_name"]) ? "'" . $_GET["last_name"] . "'" : 'null';
$date_of_birth = isset($_GET["date_of_birth"]) ? "'" . $_GET["date_of_birth"] . "'" : 'null';
$phone_number = isset($_GET["phone_number"]) ? "'" . $_GET["phone_number"] . "'" : 'null';
$city_name = isset($_GET["city_name"]) ? $_GET["city_name"] : null;
$profile_pic_id = isset($_GET["profile_pic_id"]) ? $_GET["profile_pic_id"] : 'null';

if(isset($_GET["password"])){
    $password = "'" . md5($_GET["password"]) . "'";

}
else
    $password = 'null';


$con = dbConnection();

if ($con->connect_error) {
    $arr["connection"] = "Failed";
    die("Connection failed: " . $con->connect_error);
}

if($city_name){
    $query_to_retrieve_cityid = "SELECT city_id FROM city WHERE city_name = '$city_name'";
    $city_id_result = $con->query($query_to_retrieve_cityid);
    $city_id = $city_id_result->fetch_assoc()["city_id"];
} else
    $city_id = 'null';
    

// Assuming $password and $profile_pic_id are provided from somewhere

// Add single quotes for string values in the SQL query
$updateQuery = "CALL UpdateUser($user_id, $user_email, $phone_number, $date_of_birth, $password, $first_name, $last_name, $city_id, $profile_pic_id)";

// Execute the UPDATE query
$result = mysqli_query($con, $updateQuery);

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