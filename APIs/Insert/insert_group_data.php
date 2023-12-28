<?php

include "D:\study\Projects\Kotlin\TripMate\APIs\connection.php";

$arr = [];

$con = dbConnection();

if ($con) {
    $arr["connection"] = "Success";
    echo json_encode($arr);
}
else{
    $arr["connection"] = "Failed";
    echo json_encode($arr);
    return;
}

if (!empty($_POST["group_name"])) {
    $group_name = $_POST["group_name"];
} else {
    $arr["success"] = "false";
    $arr["error"] = "$group_name incorrect";
    echo json_encode($arr);
    return;
}

if (!empty($_POST["trip_id"])) {
    $trip_id = $_POST["trip_id"];
} else {
    $arr["success"] = "false";
    $arr["error"] = "$trip_id incorrect";
    echo json_encode($arr);
    return;
}

$query = "CALL InsertGroup('$group_name','$trip_id')";

$res = mysqli_query($con, $query);
if ($res) {
    $arr["success"] = "true";
    $arr["group_id"] = mysqli_insert_id($con);
} else {
    $arr["success"] = "false";
    $arr["error"] = "Insertion failed";
}

echo json_encode($arr);

?>