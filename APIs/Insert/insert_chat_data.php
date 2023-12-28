<?php
            
include "D:\study\Projects\Kotlin\TripMate\APIs\connection.php";
            
$arr = [];

$con = dbConnection();


if(isset($_POST["chat_id"])){
    $chat_id = $_POST["chat_id"];
}
else{
    return;
}


if(isset($_POST["group_id"])){
    $group_id = $_POST["group_id"];
}
else{
    return;
}


if(isset($_POST["user_id"])){
    $user_id = $_POST["user_id"];
}
else{
    return;
}


if(isset($_POST["message"])){
    $message = $_POST["message"];
}
else{
    return;
}



if(isset($_POST["image_id"])){
    $image_id = $_POST["image_id"];
}
else{
    return;
}


if(isset($_POST["chat_time"])){
    $chat_time = $_POST["chat_time"];
}
else{
    return;
}


$query = "CALL InsertChat('$chat_id', '$group_id', '$user_id', '$message', '$image_id', '$chat_time')";

$res = mysqli_query($con, $query);
if($res){
    $arr["success"] = "true";
    $arr["chat_id"] = mysqli_insert_id($con);
}
else{
    $arr["success"] = "false";
}

echo json_encode($arr);    

?>