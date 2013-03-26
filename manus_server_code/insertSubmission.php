<?

include('connection.php');

$correct_rfid = $_GET['correct_rfid'];
$student_answer_rfid = $_GET['student_answer_rfid'];

function execute($input_query){
	include('connection.php');

	// echo $input_query;
	$result = mysqli_query($cxn, $input_query) or die("<br>Command failed: ".mysql_error());
}

execute("INSERT INTO submissions (correct_rfid, student_answer_rfid) VALUES ('".$correct_rfid."','".$student_answer_rfid."')");

?>