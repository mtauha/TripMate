<?php

include "D:\study\Projects\Kotlin\TripMate\APIs\connection.php";

$arr = [];

if ($con) {
    $arr["connection"] = "Success";
    echo json_encode($arr);
}
else{
    $arr["connection"] = "Failed";
    echo json_encode($arr);
    return;
}

if(isset($_POST["trip_name"])){
    $trip_name = $_POST["trip_name"];
} else {
    $arr["success"] = "false";
    echo json_encode($arr);
    return;
}

if(isset($_POST["source_city_id"])){
    $source_city_id = $_POST["source_city_id"];
} else {
    $arr["success"] = "false";
    echo json_encode($arr);
    return;
}

if(isset($_POST["destination_city_id"])){
    $destination_city_id = $_POST["destination_city_id"];
} else {
    $arr["success"] = "false";
    echo json_encode($arr);
    return;
}

if(isset($_POST["start_date"])){
    $start_date = $_POST["start_date"];
} else {
    $arr["success"] = "false";
    echo json_encode($arr);
    return;
}

if(isset($_POST["trip_days"])){
    $trip_days = $_POST["trip_days"];
} else {
    $arr["success"] = "false";
    echo json_encode($arr);
    return;
}

if(isset($_POST["trip_budget"])){
    $trip_budget = $_POST["trip_budget"];
} else {
    $arr["success"] = "false";
    echo json_encode($arr);
    return;
}

if(isset($_POST["no_of_persons"])){
    $no_of_persons = $_POST["no_of_persons"];
} else {
    $arr["success"] = "false";
    echo json_encode($arr);
    return;
}

$query = "CALL InsertTrip(?, ?, ?, ?, ?, ?, ?)";

$stmt = mysqli_prepare($con, $query);
mysqli_stmt_bind_param($stmt, "sssssss", $trip_name, $source_city_id, $destination_city_id, $start_date, $trip_days, $trip_budget, $no_of_persons);
mysqli_stmt_execute($stmt);

if(mysqli_stmt_affected_rows($stmt) > 0){
    $arr["success"] = "true";
    $arr["trip_id"] = mysqli_insert_id($con);
} else {
    $arr["success"] = "false";
}

echo json_encode($arr);
?>