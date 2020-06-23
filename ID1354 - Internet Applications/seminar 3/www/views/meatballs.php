<!DOCTYPE html>
<?php
$_SESSION['prevPage'] = '/meatballs';
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
		<!---Innerbody (a layer on top of the background, keeps "Home logo" and "innerframe" joined together) START-->
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
	
					<h1><strong>Meatballs</strong></h1>
					<!--Image of meatballs START-->
					<img src="views/images/meatballs.jpg" id="foodimg" alt="image of meatballs"/>
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
					include_once('views/embeddedphp/commentSection.php');
					?>
				</div>
				<!--Commentsection END-->
				
		
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
			<!--Innerbody END-->
		</div>
		<!--Innerframe END-->
	</body>
</html>
