<!DOCTYPE html>
<?php
session_start();
?>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="style/reset.css"/>
		<link rel="stylesheet" type="text/css" href="style/menu.css"/>
		<link rel="stylesheet" type="text/css" href="style/bodydesign.css"/>
		<title>Home - Tasty Recipes</title>
	</head>
	<body>
		<!---Innerbody (a layer on top of the background, keeps "Home logo" and "innerframe" joined together) START-->
		<div id="innerbody">
		
			<!--Home Logo-->
			<?php
				require('reusable/homelogo.php');
            ?>
			<!--Home Logo End-->
			
			<!--Innerframe(encloses navigation bar and site content) START-->
			<div id="innerframe">
			
					<!--Navigation bar START-->
						<div class="navbar">
							<a href="index.php"><strong>Home</strong></a>
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
						<div id="slideshow">
							  <img class="mySlides" src="images/meatballs.jpg" alt="meatballs"/>
							  <img class="mySlides" src="images/pancakes.jpg" alt="pancakes"/>
						</div>
						<script src="slideshow.js"></script>
						
						<div id="calendarpromotion">
						<h2>Feeling indecisive today?</h2>
						<p>We at Tasty Recipes can recommend our recipe calendar. A calendar developed to help you decide, press on the picture below to get there: </p>
							<a href="calendar.php"><img src="images/calendar_icon.png" alt="calendar icon"/></a>
						</div>
				</div>
				<!--Content END-->
				
				<!--Footer START-->
				<footer>
					<?php
						require('reusable/footer.php');
					?>
				</footer>
				<!--Footer END-->
				
			</div>
			<!--Innerframe END-->
		</div>
		<!--Innerbody END-->
	</body>
</html>
