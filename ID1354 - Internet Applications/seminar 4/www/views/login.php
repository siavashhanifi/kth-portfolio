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
		<script src="https://code.jquery.com/jquery-3.4.1.js"
			  integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
			  crossorigin="anonymous"></script>
		<script src="js/auth-handler.js"></script>
		<script src="js/login.js"></script>
		<title>Login - Tasty Recipes</title>
		
	</head>
	<body>
		<loggedin hidden></loggedin>
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
							<div id="loginField">
							<a id="loginButton" href="/login"><strong>Login</strong></a>
							</div>
							<!--Login field END-->
						</div>
					<!--Navigation bar END-->
					
				<!--Content START-->
				<div id="content">
					<div id="loginForm">
						<h2 id="loginStatus">Login:</h2>
						<p id="loginMessage">Fill in the formula and click on the button to login.</p>
						<form action="/login_post" method="post" id="myform">
							<input type="text" name="username" value="Username">
							<br>
							<input type="password" name="password" value="Password">
							<br>
							<input type="submit" value="Login">
						</form>
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
