function RequestHandler(){
	
	this.post = function post(url,args){
		$.post(url, args, function(response){
			$("loggedin").html(response);
		});
		return $("loggedin").html();	
	}
	

}
