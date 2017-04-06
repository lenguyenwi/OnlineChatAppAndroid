<?php
    $con= mysqli_connect("db588171695.db.1and1.com", "dbo588171695", "", "db588171695");
    

    $username = $_POST["username"];
    $chatcontext = $_POST["chatcontext"];
	$chattime = $_POST["chattime"];
	$partner_username = $_POST["partner_username"];
    
    $statement = mysqli_prepare($con, "INSERT INTO ChatWithDate (username,chatcontext,chattime,partner_username) VALUES (?, ?, ?,?)");
    mysqli_stmt_bind_param($statement, "ssss", $username, $chatcontext,$chattime,$partner_username);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_close($statement);
    
    mysqli_close($con);
?>
