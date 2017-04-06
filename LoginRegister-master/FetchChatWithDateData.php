<?php
    $con= mysqli_connect("db588171695.db.1and1.com", "dbo588171695", "", "db588171695");
      
    $username = $_POST["username"];
    
    
    $statement = mysqli_prepare($con, "SELECT * FROM ChatWithDate WHERE username = ?");
    mysqli_stmt_bind_param($statement, "s", $username);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $username, $chatcontext, $chattime);
    
    $chat = array();
    
    while(mysqli_stmt_fetch($statement)){
        $chat["username"] = $username;
        $chat["chatcontext"] = $chatcontext;
		$chat["chattime"] = $chattime;
    }
    
    echo json_encode($chat);
    mysqli_close($con);
?>
