<?php

function retrieveChatData($con, $trip_id)
{
    $arr = [];

    if (!$con) {
        $arr["connection"] = "Failed";
        echo json_encode($arr);
        return;
    }

    // Build the SELECT query
    $query =
        "SELECT user_id, message, chat_time FROM chat
NATURAL JOIN `group`
NATURAL JOIN trip
WHERE trip_id = 1;";

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

    return $arr;
}
?>