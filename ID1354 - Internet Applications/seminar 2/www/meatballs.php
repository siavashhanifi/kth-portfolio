<!DOCTYPE html>
<?php
session_start();
?>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="style/reset.css"/>
		<link rel="stylesheet" type="text/css" href="style/menu.css"/>
		<link rel="stylesheet" type="text/css" href="style/recipes/bodydesign.css"/>
		<title>Meatballs - Tasty Recipes</title>
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
							<a href="index.php">Home</a>
							<a href="calendar.php">Calendar</a>
							<div class="dropdown">
								<button class="dropbtn"><strong>Recipes</strong> 
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
	
					<h1><strong>Meatballs</strong></h1>
					<!--Image of meatballs START-->
					<img src="images/meatballs.jpg" id="foodimg" alt="image of meatballs"/>
					<!--Image of meatballs END-->
					
					<!--Recipe section START-->
					<div id="recipe">
						<h2><strong>Recipe</strong></h2>
						
						<h3>Ingredients:</h3><br/>
						<ul>
							<li>1/2 cup fine, dry breadcrumbs</li>
							<li>1/2 cup milk</li>
							<li>1 large egg</li>
							<li>1/2 cup grated Parmesan cheese</li>
							<li>1/4 cup finely chopped fresh parsley leaves</li>
							<li>2 teaspoons kosher salt</li>
							<li>Freshly ground black pepper</li>
							<li>1 pound ground meat, such as beef, pork, turkey, chicken, or veal, or a mix</li>
							<li>1/2 cup finely chopped onion (or grated on a coarse grater)</li>
							<li>1 clove garlic, minced</li>
						</ul>
						
						<br/>
						
						<h3>Instructions:</h3><br/>
						<ol>
							<li>Combine the milk and breadcrumbs.</li>
							<li>Whisk the egg, salt, pepper, Parmesan, and parsley.</li>
							<li>Add the ground meat.</li>
							<li>Add the onions and soaked breadcrumbs.</li>
							<li>Form the meat into meatballs.</li>
							<li>Cook the meatballs directly in sauce.</li>
						</ol>
					</div>		
					<!--Recipe section END-->
				</div>
				<!--Content END-->
				
				<!--Commentsection START-->
				<div id="commentsection">
					<h2>Reviews:</h2>
					<?php
						require('comments_meatballs.php');//ADDED FOR ASSIGNMENT 2
					?>
				</div>
				<!--Commentsection END-->
				
		
				<!--Footer START-->
				<footer>
					<?php
						require('reusable/footer.php');
					?>
				</footer>
				<!--Footer END-->
			</div>
			<!--Innerbody END-->
		</div>
		<!--Innerframe END-->
	</body>
</html>
