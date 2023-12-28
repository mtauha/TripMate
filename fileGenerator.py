import os
import sys

filename = sys.argv[1]

filename = os.path.join(os.getcwd(), filename)

with open(filename, "w") as f:
    f.write(
    """<?php
            
    include "D:\study\Projects\Kotlin\TripMate\APIs\connection.php";
            
            

?>"""
    )
