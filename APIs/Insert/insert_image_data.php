<?php

class ImageType
{
    const LOCATION = "LOCATION";
    const CHAT = "CHAT";
    const PROFILE_PIC = "PROFILE PICTURE";
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



if (!empty($_POST["image_data"])) {
    $image_data = base64_decode($_POST["image_data"]);
} else {
    $arr["success"] = "false";
    $arr["error"] = "$image_data incorrect";
    echo json_encode($arr);
    return;
}

if (!empty($_POST["image_type"])) {
    $image_type = $_POST["image_type"];

    if (!defined("ImageType::$image_type")) {
        $arr["success"] = "false";
        $arr["error"] = "'$image_type' image type incorrect";
        echo json_encode($arr);
        return;
    }
} else {
    $arr["success"] = "false";
    $arr["error"] = "$image_type incorrect";
    echo json_encode($arr);
    return;
}

$query = "CALL InsertGroup('$image_data','$image_type')";

$res = mysqli_query($con, $query);
if ($res) {
    $arr["success"] = "true";
    $arr["image_id"] = mysqli_insert_id($con);
} else {
    $arr["success"] = "false";
    $arr["error"] = "Insertion failed";
}

echo json_encode($arr);

?>