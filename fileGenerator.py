import os
import sys

filename = sys.argv[1]

filename = os.path.join(os.getcwd(), filename)

with open(filename, "w") as f:
    f.write(
        """<?php

include "D:\study\Projects\Kotlin\TripMate\APIs\connection.php";

$arr = [];

$con = dbConnection();

if ($con) {
    $arr["connection"] = "Success";
    echo json_encode($arr);
}
else{
    $arr["connection"] = "Failed";
    echo json_encode($arr);
    return;
}

?>"""
    )
