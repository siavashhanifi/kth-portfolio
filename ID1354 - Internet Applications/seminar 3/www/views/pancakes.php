<!DOCTYPE html>
<?php
$_SESSION['prevPage'] = '/pancakes';
?>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="views/style/reset.css"/>
		<link rel="stylesheet" type="text/css" href="views/style/menu.css"/>
		<link rel="stylesheet" type="text/css" href="views/style/recipes/bodydesign.css"/>
		<title>Home - Tasty Recipes</title>
	</head>
	<body>
		<!---Innerbody (a layer on top of the background) START-->
		<div id="innerbody">
		
			<!--Home Logo-->
			<a href="/"><img id="logo" src="views/images/logo.png" alt="Tasty Recipes"/></a>
			<!--Home Logo End-->
			
			<!--Innerframe(encloses navigation bar and site content) START-->
			<div id="innerframe">
			
					<!--Navigation bar START-->
					<div class="navbar">
					  <a href="/">Home</a>
					  <a href="/calendar">Calendar</a>
					  <div class="dropdown">
						<button class="dropbtn"><strong>Recipes</strong> 
						  <i class="fa fa-caret-down"></i>
						</button>
						<div class="dropdown-content">
						  <a href="/meatballs">Meatballs</a>
						  <a href="/pancakes">Pancakes</a>
						</div>
					  </div> 
					  <!--Login field-->
                      <?php
						include_once($_SERVER['DOCUMENT_ROOT'].'\views\embeddedphp\loginField.php');
                       ?>
					  <!--Login field END-->
					</div>
					<!--Navigation bar END-->
					
				<!--Content START-->
				<div id="content">
					<h1><strong>Pancakes</strong></h1>
					<!--Image of pancakes START-->
					<img src="views/images/pancakes.jpg" id="foodimg" alt="picture of pancakes"/>
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
					include_once('views/embeddedphp/commentSection.php');
					?>
				</div>
				<!--Comment section END-->
				
				<!--Footer START-->
				<footer>
					<p>
						<a href="http://jigsaw.w3.org/css-validator/check/referer">
							<img style="border:0;width:88px;height:31px" src="http://jigsaw.w3.org/css-validator/images/vcss-blue" alt="Valid CSS!"/>
						</a>
					</p>
				</footer>
				<!--Footer END-->
			</div>
			<!--Innerframe END-->
		</div>
		<!--Innerbody END-->
	</body>
</html>
