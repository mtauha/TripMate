<?php

function insertUserData($con, $date_of_birth, $phone_number,$user_email, $password, $first_name, $last_name, $city_id, $profile_picture_id = 1)
{
    $arr = [];

    if (empty($con)) {
        $arr["success"] = false;
        $arr["error"] = "Connection variable not passed";
        return json_encode($arr);
    }

    if (!$con) {
        $arr["success"] = false;
        $arr["error"] = "Connection failed";
        return json_encode($arr);
    }

    if (empty($user_email) || empty($password) || empty($phone_number) || empty($date_of_birth) || empty($first_name) || empty($last_name) || empty($city_id)) {
        $arr["success"] = false;
        $arr["error"] = "Invalid input";
        return json_encode($arr);
    }

    // Use md5 function directly in the SQL query
    $password = md5($password);

    // Disable foreign key checks for the duration of the session
    $query = "SET foreign_key_checks = 0;";
    $result = mysqli_query($con, $query);

    if ($result) {
        // Call the stored procedure to insert user data
        $query = "CALL INSERTUSER('$user_email', '$date_of_birth', $phone_number, '$password', '$first_name', '$last_name', '$city_id','$profile_picture_id');";
        $result = mysqli_query($con, $query);

        if ($result) {
            // Fetch the result of the last query in the multi-query
            $lastResult = mysqli_store_result($con);

            if ($lastResult) {
                $arr["success"] = true;
                $arr["user_id"] = mysqli_insert_id($con);
                mysqli_free_result($lastResult);
            } else {
                // No result set, consider the last query successful
                $arr["success"] = true;
                $arr["user_id"] = $con->insert_id;
            }
        } else {
            // Error in the stored procedure call
            $arr["success"] = false;
            $arr["error"] = "Error in query: " . mysqli_error($con);
        }
    } else {
        // Error in setting foreign key checks
        $arr["success"] = false;
        $arr["error"] = "Error in query: " . mysqli_error($con);
    }

    return json_encode($arr);
}
