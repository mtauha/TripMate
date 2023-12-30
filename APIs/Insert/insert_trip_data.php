<?php

function insertTripData($con, $trip_name, $source_city_id, $destination_city_id, $start_date, $trip_days, $trip_budget, $no_of_persons)
{

    $arr = [];

    if (empty($trip_name)) {
        $arr["success"] = false;
        $arr["error"] = "Trip name is incorrect";
        return json_encode($arr);
    }

    if (empty($source_city_id)) {
        $arr["success"] = false;
        $arr["error"] = "Source city name is incorrect";
        return json_encode($arr);
    }

    if (empty($destination_city_id)) {
        $arr["success"] = false;
        $arr["error"] = "Destination city ID is incorrect";
        return json_encode($arr);
    }

    if (empty($start_date)) {
        $arr["success"] = false;
        $arr["error"] = "Start date is incorrect";
        return json_encode($arr);
    }

    if (empty($trip_days)) {
        $arr["success"] = false;
        $arr["error"] = "Trip days is incorrect";
        return json_encode($arr);
    }

    if (empty($trip_budget)) {
        $arr["success"] = false;
        $arr["error"] = "Trip budget is incorrect";
        return json_encode($arr);
    }

    if (empty($no_of_persons)) {
        $arr["success"] = false;
        $arr["error"] = "Number of persons is incorrect";
        return json_encode($arr);
    }


    $query = "CALL InsertTrip('$trip_name', $source_city_id, $destination_city_id, '$start_date', $trip_days, $trip_budget, $no_of_persons);";

    $result = mysqli_query($con, $query);    

    if ($result) {
        $arr["success"] = true;
        $arr["trip_id"] = mysqli_insert_id($con);
    } else {
        $arr["success"] = false;
        $arr["error"] = "Insertion failed";
    }

    return json_encode($arr);
}

?>