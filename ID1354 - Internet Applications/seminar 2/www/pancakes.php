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
		<title>Pancakes - Tasty Recipes</title>
	</head>
	<body>
		<!---Innerbody (a layer on top of the background) START-->
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
					<h1><strong>Pancakes</strong></h1>
					<!--Image of pancakes START-->
					<img src="images/pancakes.jpg" id="foodimg" alt="picture of pancakes"/>
					<!--Image of pancakes END-->
					
					<!--Recipe section START-->
					<div id="recipe">
						<h2><strong>Recipe</strong></h2>
						
						<h3>Ingredients:</h3><br/>
						<ul>
							<li>1 cup all-purpose flour</li>
							<li>2 tablespoons white sugar</li>
							<li>2 teaspoons baking powder</li>
							<li>1 teaspoon salt</li>
							<li>1 egg, beaten</li>
							<li>1 cup milk</li>
							<li>2 tablespoons vegetable oil</li>
						</ul>
	
						<br/>
					
						<h3>Instructions:</h3><br/>
						<ol>
							<li>In a large bowl, mix flour, sugar, baking powder and salt. Make a well in the center, and pour in milk, egg and oil. Mix until smooth..</li>
							<li>Heat a lightly oiled griddle or frying pan over medium high heat. Pour or scoop the batter onto the griddle, using approximately 1/4 cup for each pancake.</li>
						</ol>
						
					</div>
					<!--Recipe section END-->
				</div>
				<!--Content END-->
				
				<!--Comment section  START-->
				<div id="commentsection">
					<h2>Reviews:</h2>
					<?php
						require('comments_pancakes.php'); //ADDED FOR ASSIGNMENT 2
					?>
					
				</div>
				<!--Comment section END-->
				
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
