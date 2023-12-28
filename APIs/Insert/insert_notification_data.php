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




if (!empty($_POST["notification_text"])) {
    $notification_text = $_POST["notification_text"];
} else {
    $arr["success"] = "false";
    $arr["error"] = "$notification_text error";
    echo json_encode($arr);
    return;
}

if (!empty($_POST["read_status"])) {
    $read_status = $_POST["read_status"];
} else {
    $arr["success"] = "false";
    $arr["error"] = "$read_status incorrect";
    echo json_encode($arr);
    return;
}

if (!empty($_POST["user_id"])) {
    $user_id = $_POST["user_id"];
} else {
    $arr["success"] = "false";
    $arr["error"] = "$user_id incorrect";
    echo json_encode($arr);
    return;
}

if (!empty($_POST["group_id"])) {
    $group_id = $_POST["group_id"];
} else {
    $arr["success"] = "false";
    $arr["error"] = "$group_id incorrect";
    echo json_encode($arr);
    return;
}
$query = "CALL InsertGroup('$notification_text','$read_status','$user_id','$group_id')";

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