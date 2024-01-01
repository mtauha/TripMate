<?php
function dbConnection(){
    $con = mysqli_connect("localhost", "username", "password", "tripdb");
    return $con;
}

?>
