<?php
/*Presents login button if no one is logged in*/
if(!isset($_SESSION['LogInSuccessful']) || $_SESSION['LogInSuccessful'] == False){
	echo '<a id="loginButton" href="/login"><strong>Login</strong></a>';
} 
/*Presents username if user is logged in*/
elseif($_SESSION['LogInSuccessful'] == True){
	echo '<form action="/logout_post" method="post"><input type="submit" id="logoutButton" value="Logout"></form>'.
		 '<a id="loginButton" href="/login"><strong>Logged in as: '.$_SESSION['LoggedInAs'].'</strong></a>';
}

