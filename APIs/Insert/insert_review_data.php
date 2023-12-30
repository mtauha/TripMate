<?php

class ENUM{
const TOURIST_SPOT = 'TOURIST SPOT';
const HOTEL = 'HOTEL';
    const RESTURANT = 'RESTAURANT';
}


include "D:\study\Projects\Kotlin\TripMate\APIs\connection.php";

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



if (!empty($_POST["city_id"])) {
    $city_id = base64_decode($_POST["city_id"]);
} else {
    $arr["success"] = "false";
    $arr["error"] = "$city_id incorrect";
    echo json_encode($arr);
    return;
}

if (!empty($_POST["location_name"])) {
    $location_name = base64_decode($_POST["location_name"]);
} else {
    $arr["success"] = "false";
    $arr["error"] = "$location_name incorrect";
    echo json_encode($arr);
    return;
}

if (!empty($_POST["location_type"])) {
    $location_type = $_POST["location_type"];

    if (!defined("ENUM::$location_type")) {
        $arr["success"] = "false";
        $arr["error"] = "'$location_type' location type incorrect";
        echo json_encode($arr);
        return;
    }
} else {
    $arr["success"] = "false";
    $arr["error"] = "location_type incorrect";
    echo json_encode($arr);
    return;
}

$query = "CALL InsertGroup('$city_id', '$location_name', '$location_type')";

$res = mysqli_query($con, $query);
if ($res) {
    $arr["success"] = "true";
} else {
    $arr["success"] = "false";
    $arr["error"] = "Insertion failed";
}

echo json_encode($arr);

?>