<?php
/**
 * Created by PhpStorm.
 * User: jozsef
 * Date: 3/14/16
 * Time: 6:07 AM
 */


    $con=mysqli_connect("localhost","my_user","my_password","my_db");
    // Check connection
    if (mysqli_connect_errno())
    {
        echo "Failed to connect to MySQL: " . mysqli_connect_error();
    }

    // Perform queries
    mysqli_query($con,"SELECT * FROM state");
    mysqli_query($con,"INSERT INTO Persons (FirstName,LastName,Age)
    VALUES ('Glenn','Quagmire',33)");

    mysqli_close($con);
?>