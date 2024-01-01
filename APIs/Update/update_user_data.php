<?php


function editUserData($con, $user_id, $user_email, $password, $first_name, $last_name, $phone_number, $city_id, $profile_pic_id = null)
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

    if (empty($user_id)) {
        $arr["success"] = false;
        $arr["error"] = "Invalid input";
        return json_encode($arr);
    }

    if (empty($user_email)) {
        $user_email = null;
    }
    if (empty($password)) {
        $password = null;
    }
    if (empty($first_name)) {
        $first_name = null;
    }
    if (empty($last_name)) {
        $last_name = null;
    }
    if (empty($city_id)) {
        $city_id = null;
    }
    if (empty($profile_pic_id)) {
        $profile_pic_id = null;
    }



    $updateQuery = "CALL UpdateUser($user_id,'$user_email','$password', '$first_name', '$last_name','$phone_number', $city_id, $profile_pic_id);";
    // Execute the UPDATE query
    $result = mysqli_query($con, $updateQuery);

    print_r($result);

    if ($result) {
        $arr["success"] = true;
        $arr["message"] = "User data updated successfully";
    } else {
        $arr["success"] = false;
        $arr["error"] = "Error in query: " . mysqli_error($con);
    }

    return json_encode($arr);
}
?>