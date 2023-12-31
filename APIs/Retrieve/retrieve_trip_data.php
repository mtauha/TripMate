<?php

function retrieveTripData($con, $conditions = array(), $selectColumns = null)
{
    $arr = [];

    if (!$con) {
        $arr["connection"] = "Failed";
        echo json_encode($arr);
        return;
    }

    // Build the SELECT query
    $query = "SELECT ";

    // Check if specific columns are requested
    if ($selectColumns) {
        $query .= implode(", ", $selectColumns);
    } else {
        $query .= "*";
    }

    // Specify the table
    $query .= " FROM trip";

    // Check if conditions are provided
    if (!empty($conditions)) {
        $query .= " WHERE ";

        // Build the WHERE clause based on conditions
        $conditionsArr = [];
        foreach ($conditions as $key => $value) {
            if (is_array($value)) {
                // If the value is an array, use the IN clause
                $conditionsArr[] = "$key IN (" . implode(", ", array_map('intval', $value)) . ")";
            } else {
                $conditionsArr[] = "$key = '$value'";
            }
        }
        $query .= implode(" AND ", $conditionsArr);
    }

    $result = mysqli_query($con, $query);

    if ($result) {
        $trip_data = [];
        while ($row = mysqli_fetch_assoc($result)) {
            $trip_data[] = $row;
        }

        $arr["query"] = "Success";
        $arr["trip_data"] = $trip_data;
    } else {
        $arr["query"] = "Failed";
    }

    return $arr;
}


?>