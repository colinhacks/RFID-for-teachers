<?php	
	$hostname = "209.161.142.34";
	$username = "manus_web";
	$password = "boomTNT";
	$database = "manus";
	
		$cxn = mysqli_connect($hostname, $username,$password, $database)
				or die("Login failed.");
			mysqli_select_db($cxn, $database)
				or die("Database selectino failedx");
	?>