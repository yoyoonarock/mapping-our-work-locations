<?php
	require "init.php";

	if (mysqli_connect_errno($conn))
	{
	   echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}

	$username = $_POST["username"];
	$password = $_POST["password"];

	$sql = "SELECT id FROM Login WHERE username='$username' and password='$password'";
	$result = mysqli_query($conn, $sql);
	$row = mysqli_fetch_array($result);
	$data = $row[0];

	if($data)
	{
		echo "Login successful.";
	}
	else
	{
		echo "Login failed.";
	}
	mysqli_close($conn);
?>