<?php
	mysql_connect("localhost","1068104","shubham") or die("Connection not Successfull");
	mysql_select_db("1068104") or die("Unable to Connect to the Database");
	if(isset($_POST['Registration_Id']) && isset($_POST['Password'])){
		$reg_Id = $_POST['Registration_Id'];
		$pass = $_POST['Password'];
		$qry = "UPDATE PlacementPortalVolley SET Password = '$pass' WHERE Registration_Id = '$reg_Id'";
		$res = mysql_query($qry);
		if($res){
			echo "Password Updated Successfully";
		}
		else{
			echo "Error";
		}
	}
?>