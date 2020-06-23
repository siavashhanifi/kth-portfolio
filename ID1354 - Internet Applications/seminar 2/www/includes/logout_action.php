<?php
/*Unsets the global session flags the are set when a person successfully logs in*/
session_start();
unset($_SESSION['LoggedInAs']);
unset($_SESSION['LogInSuccess']);
header('Location: ../login.php');
?>