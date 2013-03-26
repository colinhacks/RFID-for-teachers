<?

include('connection.php');

$rfid = $_GET['rfid'];
$name = $_GET['name'];

function get($descriptor, $table, $identifier, $id_value){
	include('connection.php');
	
	$query= "SELECT ".$descriptor." FROM ".$table." WHERE ".$identifier." = '".$id_value."'";
	// echo "<br>".$query."<br>";
	$result = mysqli_query($cxn, $query) or die("Query failed");
	$row = mysqli_fetch_assoc($result);
	
	return $row[$descriptor];
}

function execute($input_query){
	include('connection.php');

	// echo $input_query;
	$result = mysqli_query($cxn, $input_query) or die("<br>Command failed: ".mysql_error());
}

$rfidExisting = get('rfid','items','rfid',$rfid);

if (isset($rfidExisting)) {
	echo "2";
	execute("UPDATE items SET name = '".$name."' WHERE rfid = '".$rfid."'");
} else {
	echo "1";
	echo "INSERT INTO items (name, rfid) VALUES ('".$name."','".$rfid."')";
	execute("INSERT INTO items (name, rfid) VALUES ('".$name."','".$rfid."')");
}

?>