<?php
	require "init.php";

	if (mysqli_connect_errno($conn))
	{
	   echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}
	
	$username = $_POST["username"];
	$password = $_POST["password"];
	
	$sql = "SELECT * FROM Login WHERE username = '$username'";
	$query = mysqli_query($conn, $sql) or die("Can not query the TABLE!"); 
	
	// Count the number of rows. If a row exists, then the username exists! 
	$row = mysqli_num_rows($query); 
	if ($row >= 1) 
	{ 
		echo "This username already exists."; 
	} 
	else 
	{ 
		//ADD THE USERNAME TO THE DB 
		$sql2 = "INSERT INTO Login(username, password) VALUES ('$username', '$password')";
		$data = mysqli_query($conn, $sql2) or die("Can't Insert! "); 
	} 
        
	if($data)
	{
		echo "Registration successful.";
	}
	else
	{
		echo "Registration failed.";
	}
	mysqli_close($conn);
?>