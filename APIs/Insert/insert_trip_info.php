<?php

include "D:\study\Projects\Kotlin\TripMate\APIs\connection.php";

$arr = [];

$con = dbConnection();


if(isset($_POST["trip_name"])){
    $trip_name = $_POST["trip_name"];
}
else{
    return;
}


if(isset($_POST["source_city_id"])){
    $source_city_id = $_POST["source_city_id"];
}
else{
    return;
}


if(isset($_POST["destination_city_id"])){
    $destination_city_id = $_POST["destination_city_id"];
}
else{
    return;
}


if(isset($_POST["start_date"])){
    $start_date = $_POST["start_date"];
}
else{
    return;
}



if(isset($_POST["trip_days"])){
    $trip_days = $_POST["trip_days"];
}
else{
    return;
}


if(isset($_POST["trip_budget"])){
    $trip_budget = $_POST["trip_budget"];
}
else{
    return;
}


if(isset($_POST["no_of_persons"])){
    $no_of_persons = $_POST["no_of_persons"];
}
else{
    return;
}


$query = "CALL InsertTrip('$trip_name', '$source_city_id', '$destination_city_id', '$start_date', '$trip_days', '$trip_budget', '$no_of_persons')";

$res = mysqli_query($con, $query);
if($res){
    $arr["success"] = "true";
    $arr["trip_id"] = mysqli_insert_id($con);
}
else{
    $arr["success"] = "false";
}

echo json_encode($arr);
?>