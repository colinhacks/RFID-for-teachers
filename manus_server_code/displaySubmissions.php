<?php
$url=$_SERVER['REQUEST_URI'];
header("Refresh: 4; URL=$url"); 
?>
<?

include('connection.php');

function humanTiming ($time)
{

    $time = time() - $time; // to get the time since that moment

    $tokens = array (
        31536000 => 'year',
        2592000 => 'month',
        604800 => 'week',
        86400 => 'day',
        3600 => 'hour',
        60 => 'minute',
        1 => 'second'
    );

    foreach ($tokens as $unit => $text) {
        if ($time < $unit) continue;
        $numberOfUnits = floor($time / $unit);
        return $numberOfUnits.' '.$text.(($numberOfUnits>1)?'s':'');
    }

}

$query = "SELECT * FROM submissions ORDER BY time_submitted DESC";

echo "<b style='font-size:40px;'><center>Recent Student Submissions</center></b><br><br>";


if ($result = mysqli_query($cxn, $query)) {
	/* fetch associative array */
	$c = 0;
	while ($row = mysqli_fetch_assoc($result)) {
		// printf ("%s (%s)\n", $row["name"], $row["id"]);
		$id = $row["id"];
		$correct_rfid = $row["correct_rfid"];
		$student_answer_rfid = $row["student_answer_rfid"];
		$time_submitted = $row["time_submitted"];
		
		if ($correct_rfid == $student_answer_rfid) {
			$color = "green";
		} else {
			$color = "red";
		}
echo "<center>";
		if ($c == 0) {
			echo "<div style='font-size:70px;color:white;margin-bottom:10px;padding:10px;padding-top:30px;height:250px;background-color:$color'><b>Submitted  ".humanTiming(strtotime($time_submitted))." ago</b><br><span style='font-size:40px;'><b>Correct RFID:</b> $correct_rfid<br><b>Student Answer:</b> $student_answer_rfid</span></div>";
		} else {
			echo "<div style='font-size:x-large;color:white;margin-bottom:10px;padding:10px;height:100px;background-color:$color'><b>Time submitted: </b> ".humanTiming(strtotime($time_submitted))." ago<br><b>Correct RFID:</b> $correct_rfid<br><b>Student Answer:</b> $student_answer_rfid</div>";
		}
		$c++;
	}

	/* free result set */
	mysqli_free_result($result);
}

?>