/*Handles the dynamic presentation of html that is dependent on if a user is logged in or not*/
$(document).ready(function() { 

var recipestr = window.location.pathname.substring(1);
/*
If the dynamically loaded logout-button is pressed 
presentDefaultLoginForm, presentUnprivilegedLoginField and hidePostCommentField
are called.
*/
$("body").on('click', '#logoutButton', function(event){
	$.get("/logout",null,function(response){
		presentUnprivilegedLoginField();
		presentDefaultLoginForm();
		hidePostCommentField();
	});
});

/*
Changes the view depending on if a user is logged in or not.
*/
function adaptViewToPrivileges(){
	$.get("/login_status",null,function(username){
		if(username == "none");
		else{
			presentPrivilegedLoginField(username);
			presentPostCommentField();
		}
	});
	
}

/*
 Presents the login-field in the way as when a user is logged in.
*/
function presentPrivilegedLoginField(username){
	$("#loginField").empty();
	$("#loginField").html("<div id=\"logoutButton\" >logout</div><a id=\"loginButton\" href=\"/login\"><strong>Logged in as: "+username+"</strong></a>");
}

/*
 Presents the login-field in the way as when a user is not logged in.
*/
function presentUnprivilegedLoginField(){
	$("#loginField").empty();
	$("#loginField").html("<a id=\"loginButton\" href=\"/login\"><strong>Login</strong></a>");
}

/*
Presents the default login form if the login page is currently viewed.
*/
function presentDefaultLoginForm(){
	$("#loginField").empty();
	$("#loginField").html("<a id=\"loginButton\" href=\"/login\"><strong>Login</strong></a>");
	$("#loginStatus").html("Login:");
	$("#loginMessage").html("Fill in the formula and click on the button to log in.");
	$("#myform").removeAttr("style");
}

/*
Presents the field for posting comments if a recipe page is currently viewed.
*/
function presentPostCommentField(){
	$("#type-field").html("<p>Comment:</p><form action=\"/post_comment\" method=\"post\" id=\"postcomment\"><input type=\"text\" name=\"commentText\" value=\"Write your review!\"><br><input type=\"hidden\" name=\"recipe\" value=\""+ recipestr + "\"><input type=\"submit\" value=\"send!\"></form>");

}

/*
Hides the field for posting comments if a recipe page is currently viewed.
*/
function hidePostCommentField(){
	$("#type-field").empty();
}

adaptViewToPrivileges();


});

