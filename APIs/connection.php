<?php
function dbConnection(){
    $con = mysqli_connect("localhost", "root", "root", "tripdb");
    return $con;
}

?>