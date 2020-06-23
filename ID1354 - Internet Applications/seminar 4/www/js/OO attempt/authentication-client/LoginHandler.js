//$.getScript("js/net-client/RequestHandler.js");

function LoginHandler(){
	this.loginSuccessful = false;
	this.username = "";
	
	this.login = function login(myform){
		
		var postUrl = $(myform).attr("action"); //get form action url
		var formData = $(myform).serialize(); //Encode form elements for submission
		this.username = $("input[name=username]").val();
		var requestHandler = new RequestHandler();
		var httpResponse = requestHandler.post(postUrl,formData);
		if(httpResponse == "true")
			this.loginSuccessful = true;
		else
			this.loginSuccessful = false;
	}
	
	this.loggedIn = function loggedIn(){
		return this.loginSuccessful;
	}
	
	this.getUsername = function getUsername(){
		return this.username;
	}
	
}