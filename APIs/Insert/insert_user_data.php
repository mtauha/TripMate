<?php

include "D:\study\Projects\Kotlin\TripMate\APIs\connection.php";

$arr = [];

if ($con) {
    $arr["connection"] = "Success";
    echo json_encode($arr);
} else {
    $arr["connection"] = "Failed";
    echo json_encode($arr);
    return;
}

if (isset($_POST["user_email"])) {
    $user_email = $_POST["user_email"];
} else {
    $arr["success"] = "false";
    $arr["error"] = "$user_email incorrect";
    echo json_encode($arr);
    return;
}

if (isset($_POST["password"])) {
    $password = md5($_POST["password"]);
} else {
    $arr["success"] = "false";
    $arr["error"] = "$password incorrect";
    echo json_encode($arr);
    return;
}

if (isset($_POST["first_name"])) {
    $first_name = $_POST["first_name"];
} else {
    $arr["success"] = "false";
    $arr["error"] = "$first_name incorrect";
    echo json_encode($arr);
    return;
}

if (isset($_POST["last_name"])) {
    $last_name = $_POST["last_name"];
} else {
    $arr["success"] = "false";
    $arr["error"] = "$last_name incorrect";
    echo json_encode($arr);
    return;
}

if (isset($_POST["city_id"])) {
    $city_id = $_POST["city_id"];
} else {
    $arr["success"] = "false";
    $arr["error"] = "$city_id incorrect";
    echo json_encode($arr);
    return;
}

if (isset($_POST["profile_picture_id"])) {
    $profile_picture_id = $_POST["profile_picture_id"];
} else {
    $arr["success"] = "false";
    $arr["error"] = "$profile_picture_id incorrect";
    echo json_encode($arr);
    return;
}


$query = "CALL InsertTrip(?, ?, ?, ?, ?, ?, ?)";

$stmt = mysqli_prepare($con, $query);
mysqli_stmt_bind_param($stmt, "sssssss", $user_email, $password, $first_name, $last_name, $city_id, $profile_picture_id);
mysqli_stmt_execute($stmt);

if (mysqli_stmt_affected_rows($stmt) > 0) {
    $arr["success"] = "true";
    $arr["user_id"] = mysqli_insert_id($con);
} else {
    $arr["success"] = "false";
    $arr["error"] = "Insertion failed";
}

echo json_encode($arr);
?>