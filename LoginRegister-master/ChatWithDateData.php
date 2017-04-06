<?php
    $con= mysqli_connect("db588171695.db.1and1.com", "dbo588171695", "", "db588171695");
    

    $username = $_POST["username"];
    $chatcontext = $_POST["chatcontext"];
	$chattime = $_POST["chattime"];
    
    $statement = mysqli_prepare($con, "INSERT INTO ChatWithDate (username,chatcontext,chattime) VALUES (?, ?, ?)");
    mysqli_stmt_bind_param($statement, "sss", $username, $chatcontext,$chattime);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_close($statement);
    
    mysqli_close($con);
?>
