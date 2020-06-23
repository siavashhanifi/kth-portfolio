<?php
    /*Compares the login credentials passed in the post request, to the usernames and passwords stored in a textfile
      and sets global session flags based on the result*/
	session_start();

	$usrpwdTextFile = fopen("usrpwd", "r") or exit("Unable to open file!"); //File handle to the file storing the usernames and passwords
    $usrpwd; //Associative array using username as key to map to password value.

    /*Loads content from file into associative array*/
	while(!feof($usrpwdTextFile)){
		$usrpwd[rtrim(fgets($usrpwdTextFile))] = rtrim(fgets($usrpwdTextFile));
	}
	fclose($usrpwdTextFile);

    /*Sets flags if the credentials passed are valid*/
	if($usrpwd[$_POST[username]] == $_POST[password]){
		$_SESSION['LoggedInAs'] = $_POST[username];
		$_SESSION['LogInSuccess'] = "Yes";
		header('Location: ../login.php');
	}
    /*Sets flags if the credentials passed are invalid*/
	else{
	$_SESSION['LogInSuccess'] = "No";
	header('Location: ../login.php');
	}
?>