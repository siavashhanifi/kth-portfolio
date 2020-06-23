//$.getScript("js/authentication-client/LoginHandler.js");

$(document).ready(function() { 
 
$("#myform").submit(function(event){
	event.preventDefault(); //prevent default action 
	var loginHandler = new LoginHandler();
	loginHandler.login(this);
	if(loginHandler.loggedIn()){
			$("#loginStatus").html("Login successful!");
			$("#loginMessage").html("Welcome " + loginHandler.getUsername());
			$("#myform").hide();
		}
	else{
			$("#loginStatus").html("Login failed.");
			$("#loginMessage").html("Try again");
	}

});

});