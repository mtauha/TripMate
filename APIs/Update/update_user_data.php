<?php
function editUserData($con, $user_id, $oldAttributes, $newAttributes)
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

    if (empty($user_id) || empty($oldAttributes) || empty($newAttributes)) {
        $arr["success"] = false;
        $arr["error"] = "Invalid input";
        return json_encode($arr);
    }

    // Check if user_id exists in the user table
    $checkQuery = "SELECT * FROM user WHERE user_id = $user_id";
    $checkResult = mysqli_query($con, $checkQuery);

    if (!$checkResult || mysqli_num_rows($checkResult) == 0) {
        $arr["success"] = false;
        $arr["error"] = "User with user_id $user_id not found";
        return json_encode($arr);
    }

    // Construct the UPDATE query dynamically based on the provided attributes
    $updateQuery = "UPDATE user SET ";
    foreach ($newAttributes as $attribute => $value) {
        $updateQuery .= "$attribute = '$value', ";
    }
    $updateQuery = rtrim($updateQuery, ", ") . " WHERE user_id = $user_id";

    // Execute the UPDATE query
    $result = mysqli_query($con, $updateQuery);

    if ($result) {
        $arr["success"] = true;
        $arr["message"] = "User data updated successfully";
    } else {
        $arr["success"] = false;
        $arr["error"] = "Error in query: " . mysqli_error($con);
    }

    return json_encode($arr);
}
