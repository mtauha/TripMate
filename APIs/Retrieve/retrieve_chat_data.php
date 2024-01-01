<?php

function retrieveChatData($con, $user_id)
{
    $arr = [];

    if (!$con) {
        $arr["connection"] = "Failed";
        echo json_encode($arr);
        return;
    }

    // Build the SELECT query
    $query = "SELECT * FROM chat WHERE user_id = '$user_id'";

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