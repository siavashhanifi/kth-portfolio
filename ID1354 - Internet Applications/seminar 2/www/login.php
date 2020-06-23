<!DOCTYPE html>
<?php
session_start();
?>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="style/reset.css"/>
		<link rel="stylesheet" type="text/css" href="style/menu.css"/>
		<link rel="stylesheet" type="text/css" href="style/loginform/bodydesign.css"/>
		<title>Login - Tasty Recipes</title>
	</head>
	<body>
		<!---Innerbody (a layer on top of the background, keeps "Home logo" and "innerframe" joined together) START-->
		<div id="innerbody">
		
			<!--Home Logo-->
			<?php
				require('reusable/homelogo.php');//ADDED FOR ASSIGNMENT 2
			?>
			<!--Home Logo End-->
			
			<!--Innerframe(encloses navigation bar and site content) START-->
			<div id="innerframe">
			
					<!--Navigation bar START-->
						<div class="navbar">
							<a href="index.php">Home</a>
							<a href="calendar.php">Calendar</a>
							<div class="dropdown">
								<button class="dropbtn">Recipes 
								<i class="fa fa-caret-down"></i>
								</button>
								<div class="dropdown-content">
									<a href="meatballs.php">Meatballs</a>
									<a href="pancakes.php">Pancakes</a>
								</div>
							</div>

							<!--Login field START-->
                            <?php
                            /*Presents username if user is logged in*/
							if($_SESSION['LogInSuccess'] == "Yes"){
								echo '<form action="includes/logout_action.php" method="post">
								<input type="submit" id="logoutButton" value="Logga ut">
								</form>';
								echo '<a id="loginButton" href="login.php"><strong>';
								echo "Inloggad som: ";
								echo $_SESSION['LoggedInAs'];
								echo "</strong></a>";
							}
                            /*Presents login button if no one is logged in*/
							else{
								echo '<a id="loginButton" href="login.php"><strong>Logga in</strong></a>';
							}
                            ?>
							<!--Login field END-->
						</div>
					<!--Navigation bar END-->
					
				<!--Content START-->
				<div id="content">
					
					<div id="loginForm">
					<?php
                    //Presents login-formular if no one is logged in and no attempts to login have occured.
                    //Presents login-formular and error message if login attempt failed.
                    //Presents success message if the login was successful.
						if($_SESSION['LogInSuccess'] == "No"){
							echo "<h2>Inloggning misslyckades:</h2>";
							echo "<p>Fel användarnamn eller lösenord. Prova igen!</p>";
							echo'<form action="views/postmethods/loginPostMethod.php" method="post">
						  <input type="text" name="username" value="Username">
						  <br>
						  <input type="password" name="password" value="Password">
						  <br>
						  <input type="submit" value="Login">
						</form>';
						}
						elseif($_SESSION['LogInSuccess'] == "Yes"){
							echo "<h2>Inloggning lyckad!:<h2>";
							echo "<p>Välkommen ";
							echo $_SESSION['LoggedInAs'];
							echo "</p>";
		
						}
						else{
							echo "<h2>Logga in:</h2>";
							echo "<p>Fyll i formuläret och klicka på knappen för att logga in.</p>";
							echo'<form action="views/postmethods/loginPostMethod.php" method="post">
						  <input type="text" name="username" value="Username">
						  <br>
						  <input type="password" name="password" value="Password">
						  <br>
						  <input type="submit" value="Login">
						</form>';
						}
					
						/*<!--Login form start-->
						echo'<form action="includes/login_action.php" method="post">
						  <input type="text" name="username" value="Username">
						  <br>
						  <input type="text" name="password" value="Password">
						  <br>
						  <input type="submit" value="Login">
						</form>';
						<!--Login form end-->*/
						?>
					</div>

				</div>
				<!--Content END-->
				
				<!--Footer START-->
				<footer>
					<?php
						require('reusable/footer.php'); //ADDED FOR ASSIGNMENT 2
					?>
				</footer>
				<!--Footer END-->
				
			</div>
			<!--Innerframe END-->
		</div>
		<!--Innerbody END-->
	</body>
</html>
