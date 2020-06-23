<!DOCTYPE html>
<?php
$_SESSION['prevPage'] = '/calendar';
?>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" type="text/css" href="views/style/reset.css"/>
		<link rel="stylesheet" type="text/css" href="views/style/menu.css"/>
		<link rel="stylesheet" type="text/css" href="views/style/calendar/bodydesign.css"/>
		<title>Home - Tasty Recipes</title>
		<script src="https://code.jquery.com/jquery-3.4.1.js"
			  integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
			  crossorigin="anonymous"></script>
		<script src="js/auth-handler.js"></script>
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
					  <a href="/calendar"><strong>Calendar</strong></a>
					  <div class="dropdown">
						<button class="dropbtn">Recipes 
						  <i class="fa fa-caret-down"></i>
						</button>
						<div class="dropdown-content">
						  <a href="/meatballs">Meatballs</a>
						  <a href="/pancakes">Pancakes</a>
						</div>
					  </div>
					  <!--Login field-->
							<div id="loginField">
							<a id="loginButton" href="/login"><strong>Login</strong></a>
							</div>
					  <!--Login field END-->					  
					</div>
					<!--Navigation bar END-->
					
					
				<!--Content START-->
				<div id="content">
						<div class = "dayblock" id="day1"><p>1</p></div>
						<div class = "dayblock" id="day2"><p>2</p></div>
						<div class = "dayblock" id="day3"><p>3</p></div>
						<div class = "dayblock" id="day4"><p>4</p></div>
						<div class = "dayblock" id="day5"><p>5</p></div>
						<div class = "dayblock" id="day6"><p>6</p></div>
						<div class = "dayblock" id="day7"><p>7</p></div>
						<div class = "dayblock" id="day8"><p>8</p></div>
						<div class = "dayblock" id="day9"><p>9</p></div>
						<div class = "dayblock" id="day10"><p>10</p><a href="/pancakes"><img src="views/images/pancakescartoon.png" alt="picture of pancakes"/></a></div>
						<div class = "dayblock" id="day11"><p>11</p></div>
						<div class = "dayblock" id="day12"><p>12</p></div>
						<div class = "dayblock" id="day13"><p>13</p></div>
						<div class = "dayblock" id="day14"><p>14</p></div>
						<div class = "dayblock" id="day15"><p>15</p></div>
						<div class = "dayblock" id="day16"><p>16</p></div>
						<div class = "dayblock" id="day17"><p>17</p></div>
						<div class = "dayblock" id="day18"><p>18</p></div>
						<div class = "dayblock" id="day19"><p>19</p></div>
						<div class = "dayblock" id="day20"><p>20</p><a href="/meatballs"><img src="views/images/meatballscartoon.png" alt="picture of pancakes"/></a></div>
						<div class = "dayblock" id="day21"><p>21</p></div>
						<div class = "dayblock" id="day22"><p>22</p></div>
						<div class = "dayblock" id="day23"><p>23</p></div>
						<div class = "dayblock" id="day24"><p>24</p></div>
						<div class = "dayblock" id="day25"><p>25</p></div>
						<div class = "dayblock" id="day26"><p>26</p></div>
						<div class = "dayblock" id="day27"><p>27</p></div>
						<div class = "dayblock" id="day28"><p>28</p></div>
				</div>
				<!--Content END-->
				
				<!--footer START-->
				<footer>
					<p>
						<a href="http://jigsaw.w3.org/css-validator/check/referer">
							<img style="border:0;width:88px;height:31px" src="http://jigsaw.w3.org/css-validator/images/vcss-blue" alt="Valid CSS!"/>
						</a>
					</p>
				</footer>
				<!--footer END-->
				
			</div>
			<!--Innerframe END-->
		</div>
		<!--Innerbody END-->
	</body>
</html>
