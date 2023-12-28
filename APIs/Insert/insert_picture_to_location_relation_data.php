<?php

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



if (!empty($_POST["location_id"])) {
    $location_id = $_POST["location_id"];
} 
else {
    $arr["success"] = "false";
    $arr["error"] = "$location_id incorrect";
    echo json_encode($arr);
    return;
}

if (!empty($_POST["image_data"])) {
    $image_data = base64_decode($_POST["image_data"]);
} else {
    $arr["success"] = "false";
    $arr["error"] = "$image_data incorrect";
    echo json_encode($arr);
    return;
}

$query = "CALL InsertGroup('$location_id','$image_data')";

$res = mysqli_query($con, $query);
if ($res) {
    $arr["success"] = "true";
} else {
    $arr["success"] = "false";
    $arr["error"] = "Insertion failed";
}

echo json_encode($arr);

?>