<?php
	define("hostType","localhost");
	define("User","1068104");
	define("Password","shubham");
	define("Db_Name","1068104");
	
	mysql_connect(hostType,User,Password) or die("Connection not Successfull");
	mysql_select_db(Db_Name) or die("Database not found");
	
	$qry = "SELECT * FROM placementStats";
	$result = mysql_query($qry);
	
	if(!$result){
		echo "Cannot Execute Query";
	}
	
	$output = array();
	while($res = mysql_fetch_array($result)){
		array_push($output,$raw);
	}
	
	echo json_encode($output);
?>