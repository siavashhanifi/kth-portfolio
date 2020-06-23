$(document).ready(function(){

var recipestr = window.location.pathname.substring(1);

/*
dynamically loaded postcomment-form calls the postComment function
sending the comment text as argument.
*/
$("body").on('submit', '#postcomment', function(event){
	event.preventDefault(); //prevent default action 
	commentText = $(this).children("input[name=commentText]").val();
	postComment(commentText);
});


/*
dynamically loaded remove buttons call the deleteComment function,
sending the commentid as argument.
*/
$("body").on('click', '.remove', function(event){
	var commentid = $(this).attr("commentid");
	$(this).parent().hide(); 
	deleteComment(commentid);
});

/*
Loads all the comments into the commentsection
*/
function loadComments(){
	$.post("/get_comments",{recipe: recipestr}, function(comments){
		var jsonComments = JSON.parse(comments);
		presentComments(jsonComments);
	});
}

/*
Takes an array of comments and presents them in html-format under the #comments division.
The presentation varies depending on if the comment is of a currently logged in user or not.
If the user is logged in a remove-button is acompanied with his/her comments.
*/
function presentComments(comments){
	$("#comments").empty();
	$.get("login_status",null, function(userLoggedIn){
		if(userLoggedIn != "none"){
			$.each(comments, function(i, item) {
				if(comments[i].Username == userLoggedIn)
					$("#comments").append("<div class=\"review\"  id=\""+comments[i].CommentID+"\"><p><strong>"+comments[i].Username+": </strong>"+comments[i].CommentText + "</p><button class=\"remove\" recipe=\""+ recipestr +"\" commentid=\""+comments[i].CommentID+"\">remove</button>");
				else
					$("#comments").append("<div class=\"review\" id=\""+comments[i].CommentID+"\"><p><strong>"+comments[i].Username+": </strong>"+comments[i].CommentText + "</p></div>");
			});
		}
		else{
			$.each(comments, function(i, item) {
			$("#comments").append("<div class=\"review\" id=\""+comments[i].CommentID+"\"><p><strong>"+comments[i].Username+": </strong>"+comments[i].CommentText + "</p></div>");

			});
		}
		
	});
}

/*
Sends an AJAX post request to delete a specified comment and then reloads the comments
@param commentid_arg the commentid
*/
function deleteComment(commentid_arg) {
	$.post("/delete_comment", {recipe: recipestr, commentID: commentid_arg},function(response){
			loadComments(recipestr);
	}) ;	
}

/*
Sends an AJAX post request to add a comment.
@param commentText_arg the comment text.
*/
function postComment(commentText_arg){
	$.get("login_status",null, function(userLoggedIn){
		$.post("/post_comment", {recipe: recipestr, username: userLoggedIn, commentText: commentText_arg},function(response){
			loadComments(recipestr);
		});
	});
}

loadComments();

});