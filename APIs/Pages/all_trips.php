<?php

include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\connection.php";
include "D:\\study\\Projects\\Kotlin\\TripMate\\APIs\\Retrieve\\retrieve_trip_data.php";

$arr = [];
$user_id = $_GET["user_id"];

$con = dbConnection();

if ($con->connect_error) {
    $arr["connection"] = "Failed";
    die("Connection failed: " . $con->connect_error);
}

$script_to_retrieve_trip_id =
"SELECT trip_id FROM trip
NATURAL JOIN `group`
NATURAL JOIN user_to_group_relation
WHERE user_id = '$user_id'
";


$trip_id_result = $con->query($script_to_retrieve_trip_id);
if ($trip_id_result->num_rows > 0) {
    while ($row = $trip_id_result->fetch_assoc()) {
        $trip_id = $row["trip_id"];
    }
} else {
    $arr["error"] = "Trip_id retrieval Failed";
    die("Failed to retrieve trip_id: " . $con->connect_error);
}

echo json_encode(retrieveTripData($con, array("trip_id"=>$trip_id)), JSON_PRETTY_PRINT);

mysqli_close($con);
?>