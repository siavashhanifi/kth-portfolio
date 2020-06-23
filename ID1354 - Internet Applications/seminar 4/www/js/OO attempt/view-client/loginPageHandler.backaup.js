//$.getScript("js/authentication-client/LoginHandler.js");
var loggedIn = false;
var username = "";

$(document).ready(function() { 
 
$("#myform").submit(function(event){
	event.preventDefault(); //prevent default action 
	login(this);
	if(loggedIn){
			$("#loginStatus").html("Login successful!");
			$("#loginMessage").html("Welcome " + username);
			$("#myform").hide();
		}
	else{
			$("#loginStatus").html("Login failed.");
			$("#loginMessage").html("Try again");
	}

});

});

function post(url,args){
	var resp;
	$.post(url, args, function( response ) {
			resp = response;
	});
	return "true";
}

function login(myform){
		var postUrl = $(myform).attr("action"); //get form action url
		var formData = $(myform).serialize(); //Encode form elements for submission
		var httpResponse = post(postUrl,formData);
		if(httpResponse == "true"){
			loggedIn = true;
			username = $("input[text=username]").val();
			alert(username);
		}
		else
			loggedIn = false;
}





/*class LoginHandler{
	constructor(){
		var loginSuccessful = false;
		var username = "";
	}
		
	login(myform){
		var postUrl = $(myform).attr("action"); //get form action url
		var formData = $(myform).serialize(); //Encode form elements for submission
		alert(this.username);
		this.username = $("input[text=username]").val();
		var requestHandler = new RequestHandler();
		var httpResponse = requestHandler.post(postUrl,formData);
		if(httpResponse == "true")
			this.loginSuccessful = true;
		else
			this.loginSuccessful = false;
	}
	
	loggedIn(){
		alert(this.username);
		return this.loginSuccessful;
	}
	
	getUsername(){
		return this.username;
	}
	
}


class RequestHandler{
	constructor(){
		var resp = "";
	}
	post(url,args){
		
		$.post(url, args, function( response ) {
			this.resp = response;
		});
		return this.resp;
	}
}*/