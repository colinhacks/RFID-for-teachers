<?php 
include('UncleFredsCookies.php');

			
$rfid = $_GET['rfid'];
$name = $_GET['name']



function get($descriptor, $table, $identifier, $id_value){
			

			$query= "Select $descriptor from $table where $identifier = '$id_value'" ;
			//echo $query;
			$result = mysqli_query($cxn, $query)
				or die("Query failed");
			$row = mysqli_fetch_assoc($result);
			return $row[$descriptor];
		
			}

function execute($input_query){
	
		//echo "<br/>database selected";
		////echo "<br/>$input_query";
	$result = mysqli_query($cxn, $input_query)
		or die("Command failed: ".mysql_error());
	////echo "<br/>successful execution.<br/>";
	}
			
$rfidExisting = get('rfid','items','rfid',$rfid)

if(isset($rfidExisting){
	execute('update table items set name = \'$name\' where rfid = \'$rfid\'' );
	
}else{

	execute('insert into table items set name = \'$name\' where rfid = \'$rfid\'' );
	
}
}


?>