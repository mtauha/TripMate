<?php

include "D:\study\Projects\Kotlin\TripMate\APIs\connection.php";

$arr = [];

$con = dbConnection();

if ($con) {
    $arr["connection"] = "Success";
} else {
    $arr["connection"] = "Failed";
    echo json_encode($arr);
    return;
}



if (!empty($_GET["user_id"])) {
    $user_id = $_GET["user_id"];
} else {
    $arr["success"] = "false";
    $arr["error"] = "$user_id incorrect";
    echo json_encode($arr);
    return;
}

if (!empty($_GET["trip_id"])) {
    $trip_id = $_GET["trip_id"];
} else {
    $arr["success"] = "false";
    $arr["error"] = "$trip_id incorrect";
    echo json_encode($arr);
    return;
}

$query = "SELECT group_id FROM `group` WHERE trip_id = $trip_id";
$res = mysqli_query($con, $query);

$group_id = mysqli_fetch_assoc($res)["group_id"];
$group_id = (int) $group_id;


$query = "CALL AddUserToGroup($user_id, $group_id);";

$res = mysqli_query($con, $query);
if ($res) {
    $arr["success"] = "true";
} else {
    $arr["success"] = "false";
    $arr["error"] = "Insertion failed";
}

echo json_encode($arr);

?>