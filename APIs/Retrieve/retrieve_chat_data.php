<?php

function retrieveChatData($con, $conditions = array(), $selectColumns = null)
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
    $query .= " FROM chat";

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
        $chat_data = [];
        while ($row = mysqli_fetch_assoc($result)) {
            $chat_data[] = $row;
        }

        $arr["query"] = "Success";
        $arr["chat_data"] = $chat_data;
    } else {
        $arr["query"] = "Failed";
    }

    mysqli_close($con);
    return json_encode($arr);
}


?>