<?php 
							if(!isset($_SESSION['LogInSuccessful'])){
								echo '<h2>Login:</h2>
										<p>Fill in the formula and click on the button to logga in.</p>
										<form action="/login_post" method="post">
											<input type="text" name="username" value="Username">
											<br>
											<input type="password" name="password" value="Password">
											<br>
											<input type="submit" value="Login">
									  </form>';
							}
							elseif($_SESSION['LogInSuccessful'] === False){
								echo  '<h2>Login failed:</h2>
									   <p>Wrong username or password. Try again!</p>
											<form action="/login_post" method="post">
												<input type="text" name="username" value="Username">
												<br>
												<input type="password" name="password" value="Password">
												<br>
												<input type="submit" value="Login">
											</form>';
							}
							elseif($_SESSION['LogInSuccessful'] === True){
								echo '<h2>Login successful<h2>
									  <p>Welcome '.$_SESSION['LoggedInAs'].'!</p>';
			
							}
