<?php

function insertUserData($con, $user_email, $password, $first_name, $last_name, $city_id, $profile_picture_id)
{
    $arr = [];

    if (!$con) {
        $arr["success"] = false;
        $arr["error"] = "Connection failed";
        return json_encode($arr);
    }

    if (empty($user_email) || empty($password) || empty($first_name) || empty($last_name) || empty($city_id) || empty($profile_picture_id)) {
        $arr["success"] = false;
        $arr["error"] = "Invalid input";
        return json_encode($arr);
    }

    $query = "CALL InsertTrip(?, ?, ?, ?, ?, ?, ?)";
    $stmt = mysqli_prepare($con, $query);
    $password = md5($password);
    mysqli_stmt_bind_param($stmt, "sssssss", $user_email, $password, $first_name, $last_name, $city_id, $profile_picture_id);
    mysqli_stmt_execute($stmt);

    if (mysqli_stmt_affected_rows($stmt) > 0) {
        $arr["success"] = true;
        $arr["user_id"] = mysqli_insert_id($con);
    } else {
        $arr["success"] = false;
        $arr["error"] = "Insertion failed";
    }

    return json_encode($arr);
}
