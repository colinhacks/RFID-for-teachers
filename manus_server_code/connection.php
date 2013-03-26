<?

	$hostname = "localhost";
	$username = "username";
	$password = "password";
	$database = "database";

	$cxn = mysqli_connect($hostname, $username,$password, $database) or die("Login failed.");
	// echo 'Successful connection.';
	mysqli_select_db($cxn, $database) or die("Database selection failed");

?>