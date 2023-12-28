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

// Validate and sanitize the input parameters

if(isset($_POST["country_name"])){
    $country_name = $_POST["country_name"];
} else {
    $arr["success"] = "false";
    echo json_encode($arr);
    return;
}

$query = "CALL InsertCountryData('$country_name')";

$res = mysqli_query($con, $query);
if($res){
    $arr["success"] = "true";
    $arr["country_name"] = mysqli_insert_id($con);
}
else{
    $arr["success"] = "false";
}

// echo json_encode($arr);

?>