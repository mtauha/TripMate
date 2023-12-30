<?php

function retrieveNotificationData($con, $conditions = array(), $selectColumns = null)
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
    $query .= " FROM notification";

    // Check if conditions are provided
    if (!empty($conditions)) {
        $query .= " WHERE ";

        // Build the WHERE clause based on conditions
        $conditionsArr = [];
        foreach ($conditions as $key => $value) {
            $conditionsArr[] = "$key = '$value'";
        }
        $query .= implode(" AND ", $conditionsArr);
    }

    $result = mysqli_query($con, $query);

    if ($result) {
        $notification_data = [];
        while ($row = mysqli_fetch_assoc($result)) {
            $notification_data[] = $row;
        }

        $arr["query"] = "Success";
        $arr["notification_data"] = $notification_data;
    } else {
        $arr["query"] = "Failed";
    }

    echo json_encode($arr);
    mysqli_close($con);
}


?>