/*Handles the AJAX for logging in and the dynamic presentation of html on the login-page*/
$(document).ready(function() {
	
$("body").on('submit', '#myform', function(event){
	event.preventDefault(); //prevent default action 
	login(this);
});

/*
Changes the view depending on if a user is logged in or not.
*/
 function adaptLoginFormToPrivileges(){
	$.get("/login_status",null,function(username){
		if(username == "none");
		else{
			presentPrivilegedLoginForm(username);
			presentPrivilegedLoginField(username);
		}
	});
 }
 
 /*
 Presents the login field in the way as when a user is logged in.
 @param username, the logged in user.
 */
 function presentPrivilegedLoginField(username){
	$("#loginField").empty();
	$("#loginField").html("<div id=\"logoutButton\" >logout</div><a id=\"loginButton\" href=\"/login\"><strong>Logged in as: "+username+"</strong></a>");
}
 
  /*
 Presents the login form in the way as when a user is logged in.
 @param username, the logged in user.
 */
 function presentPrivilegedLoginForm(username){
	$("#loginStatus").html("Login successful!");
	$("#loginMessage").html("Welcome " + username);
	$("#myform").hide();
 }
 
 /*
 Presents the user with a message that the login-attempt failed.
 */
 function presentLoginFailedMessage(){
	$("#loginStatus").html("Login failed.");
	$("#loginMessage").html("Try again");
 }
 
  /*
 Logs the user in 
 */
 function login(myform){
	 	var post_url = $(myform).attr("action"); //get form action url
		var form_data = $(myform).serialize(); //Encode form elements for submission
		var username = $("input[name=username]").val();
		
		$.post( post_url, form_data, function( response ) {
			if(response == "true"){
				presentPrivilegedLoginForm(username);
				presentPrivilegedLoginField(username);
			}
			else{
				presentLoginFailedMessage();
			}
		});
}


adaptLoginFormToPrivileges();
 
});
