<!DOCTYPE html>
<?php
$_SESSION['prevPage'] = '/login';
?>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="views/style/reset.css"/>
		<link rel="stylesheet" type="text/css" href="views/style/menu.css"/>
		<link rel="stylesheet" type="text/css" href="views/style/loginpage/bodydesign.css"/>
		<title>Login - Tasty Recipes</title>
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
								<button class="dropbtn">Recipes 
									<i class="fa fa-caret-down"></i>
								</button>
								<div class="dropdown-content">
									<a href="/meatballs">Meatballs</a>
									<a href="/pancakes">Pancakes</a>
								</div>
					  </div> 
							<!--Login field START-->
                            <?php
								include_once($_SERVER['DOCUMENT_ROOT'].'\views\embeddedphp\loginField.php');
                            ?>
							<!--Login field END-->
						</div>
					<!--Navigation bar END-->
					
				<!--Content START-->
				<div id="content">
					
					<div id="loginForm">
					<?php
						include_once($_SERVER['DOCUMENT_ROOT'].'\views\embeddedphp\loginPageContent.php');
					?>
					</div>

				</div>
				<!--Content END-->
				
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
