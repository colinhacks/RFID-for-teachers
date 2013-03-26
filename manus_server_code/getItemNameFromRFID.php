<?

include('connection.php');

function get($descriptor, $table, $identifier, $id_value){
	include('connection.php');

	$query= "SELECT ".$descriptor." FROM ".$table." WHERE ".$identifier." = '".$id_value."'";
	// echo "<br>".$query."<br>";
	$result = mysqli_query($cxn, $query) or die("Query failed");
	$row = mysqli_fetch_assoc($result);
	
	return $row[$descriptor];
}

echo get("name", "items", "rfid", $_GET['rfid']);

?>