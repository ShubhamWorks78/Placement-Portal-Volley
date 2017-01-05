<?php
	//Php file to Check If Data Provided by User is Correct or Not
	
	mysql_connect("localhost","1068104","shubham") or die ("Connection not Successfull");
	
	mysql_select_db("1068104") or die ("Database not found");
	if(isset($_POST['Registration_Id']) && isset($_POST['Password'])){
		$reg_Id = $_POST['Registration_Id'];
		$pass = $_POST['Password'];
		$qry = "SELECT * FROM PlacementPortalVolley WHERE Registration_Id='$reg_Id' AND Password='$pass'";
		$result = mysql_fetch_array($qry);
		if(mysql_num_rows($result)==0){
			echo "User Not Present";
		}
		else{
			echo "Data Present";
		}
	}
	else{
		echo "Wrong Data Format";
	}
?>