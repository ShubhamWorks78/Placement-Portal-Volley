<?php
	//Checking if the Script Received all the data it needs or not
	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		//Getting Post data
		$name = $_POST['Name'];
		$regid = $_POST['Reg_Id'];
		$email = $_POST['Email'];
		$branch = $_POST['Branch'];
		$pass = $_POST['Password'];
		$mobile = $_POST['Mobile'];
		
		//Checking if the received values are not Null
		if($name == '' || $regid == '' || $email == '' || $branch == '' || $pass == '' || $mobile==''){
			echo "Please fill all the values";
		}else{
			//if the values are present then
			//connect to our database
			require_once('dbConnect.php');
		}
		
		$sql = "SELECT * FROM LoginDb WHERE Reg_Id = '$regid' OR Email = '$email'";
		
		$check = mysqli_fetch_array(mysqli_query($con,$sql));
		
		//Checking if check has some values or not
		
		if($isset($check)){
			echo 'Username or email already Exists';
		}
		else{
			//If not present insert data into Database
			$sql = "INSERT INTO LoginDb(Name,Reg_Id,Email,Branch,Password,Mobile) VALUES('$name','$regid','$email','$branch','$pass','$mobile')";
			
			//Trying to Insert Data into Database
			if(mysqli_query($con,$sql)){
				echo 'Successfully Registered';
			}else{
				echo 'Oops! Please try again!';
			}
		}
		
		mysqli_close($con);
	}
	else{
		echo 'error';
	}
?>