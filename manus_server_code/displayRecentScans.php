<?

include('connection.php');

$query = "SELECT * FROM items ORDER BY last_updated DESC";

echo "<b><center>Recent Scans</center></b><br><br>";

if ($result = mysqli_query($cxn, $query)) {
	/* fetch associative array */
	while ($row = mysqli_fetch_assoc($result)) {
		// printf ("%s (%s)\n", $row["name"], $row["id"]);
		$id = $row["id"];
		$name = $row["name"];
		$rfid = $row["rfid"];
		$last_updated = $row["last_updated"];
		
		echo "<b>Object Name:</b> $name<br><b>RFID Number:</b> $rfid<br><b>Time Scanned:</b> $last_updated<hr><br>";

	}

	/* free result set */
	mysqli_free_result($result);
}

?>