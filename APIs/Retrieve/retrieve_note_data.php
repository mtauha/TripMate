<?php

function retrieveNoteData($con, $user_id, $trip_id)
{
    $arr = [];

    if (!$con) {
        $arr["connection"] = "Failed";
        echo json_encode($arr);
        return;
    }

    // Build the SELECT query
    $query = "SELECT * FROM notes WHERE user_id = $user_id AND trip_id = $trip_id;";

    $result = mysqli_query($con, $query);

    if ($result) {
        $note_data = [];
        while ($row = mysqli_fetch_assoc($result)) {
            $note_data[] = $row;
        }

        $arr["query"] = "Success";
        $arr["note_data"] = $note_data;
    } else {
        $arr["query"] = "Failed";
    }

    return $arr;
}
?>