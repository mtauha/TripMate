<?php

function retrieveLocationData($con, $conditions = array(), $limit, $selectColumns = null)
{
    $arr = [];

    if (!$con) {
        $arr["connection"] = "Failed";
        return $arr;
    }

    // Build the SELECT query
    $query = "SELECT ";

    // Check if specific columns are requested
    if ($selectColumns) {
        $query .= implode(", ", $selectColumns);
    } else {
        $query .= "c.city_id, c.city_name, c.country_id, co.country_name";
    }

    // Specify the table and alias
    $query .= " FROM city c";

    // Join with the country table
    $query .= " INNER JOIN country co ON c.country_id = co.country_id";


    // Check if conditions are provided
    if (!empty($conditions)) {
        $query .= " WHERE ";

        // Build the WHERE clause based on conditions
        $conditionsArr = [];
        foreach ($conditions as $key => $value) {
            $conditionsArr[] = "$key LIKE '$value'";
        }
        $query .= implode(" AND ", $conditionsArr);
    }

    $query .= " LIMIT $limit";

    $result = mysqli_query($con, $query);

    if ($result) {
        $locations = [];
        while ($row = mysqli_fetch_assoc($result)) {
            $locations[] = $row;
        }

        $arr["query"] = "Success";
        $arr["locations"] = $locations;
    } else {
        $arr["query"] = "Failed: " . mysqli_error($con);
    }

    return $arr;
}

?>
