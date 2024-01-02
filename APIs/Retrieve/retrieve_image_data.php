<?php
function retrieveImageData($con, $conditions = array(), $selectColumns = null)
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
    $query .= " FROM image";

    // Check if conditions are provided
    if (!empty($conditions)) {
        $query .= " WHERE ";

        // Build the WHERE clause based on conditions
        $conditionsArr = [];
        foreach ($conditions as $key => $value) {
            if (is_int($value) || is_float($value))
                $conditionsArr[] = "$key = $value";
            else
                $conditionsArr[] = "$key = '$value'";
        }
        $query .= implode(" AND ", $conditionsArr);
        $query .= ";";
    }
    $result = $con->query($query);

    if ($result) {
        $image = [];
        while ($row = mysqli_fetch_assoc($result)) {
            $image[] = $row;
        }

        $arr["query"] = "Success";
        $arr["image"] = $image;
    } else {
        $arr["query"] = "Failed";
    }

    mysqli_close($con);
    return json_encode($arr);
}


?>