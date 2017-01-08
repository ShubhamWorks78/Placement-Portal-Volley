<?php
	mysql_connect("localhost","1068104","shubham") or die("Connection not Successfull");
	mysql_select_db("1068104") or die("Database not found");
	if(isset($_POST['Username']) && isset($_POST['Registration_Id']) && isset($_POST['Password']) && isset($_POST['Email']) && isset($_POST['Mobile']) && isset($_POST['Branch'])){
		$name = $_POST['Username'];
		$reg_Id = $_POST['Registration_Id'];
		$pass = $_POST['Password'];
		$email = $_POST['Email'];
		$branch = $_POST['Branch'];
		$mob = $_POST['Mobile'];
		if($name=='' || $reg_Id=='' || $pass=='' || $email=='' || $branch=='' || $mob==''){
			echo "Please fill all the Boxes.";
		}
		else{
			$qry = "SELECT * FROM PlacementPortalVolley WHERE Registration_Id='$reg_Id' OR Email='$email'";
			
			$chck = mysql_fetch_array($qry);
			
			if(mysql_num_rows($chck)==0){			
				$qrr = "INSERT INTO PlacementPortalVolley VALUES ('$name','$reg_Id','$pass','$email','$mob','$branch')";
				mysql_query($qrr) or die("Query Problem");
			}
			else{
				echo "Don't Enter Duplicated Data";
			}
		}
	}
	else{
		echo "Error";
	}
?>