<?php
            
include "D:\study\Projects\Kotlin\TripMate\APIs\connection.php";
            
$arr = [];

$con = dbConnection();


if(isset($_POST["country_name"])){
    $country_name = $_POST["country_name"];
}
else{
    return;
}


$query = "CALL InsertChat('$country_name')";

$res = mysqli_query($con, $query);
if($res){
    $arr["success"] = "true";
    $arr["country_name"] = mysqli_insert_id($con);
}
else{
    $arr["success"] = "false";
}

echo json_encode($arr);    

?>