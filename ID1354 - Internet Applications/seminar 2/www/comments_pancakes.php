<?php
/*Presents the comments for the pancakes recipe page and if a user is logged in; it also presents the post-comment formular*/
session_start();
include('includes/CommentHandler.php');

$CommentHandler;//A pointer to a commentHandler

/*If a comment handler for the recipe page in question already exists, use it*/
if(isset($_SESSION['CommentHandler']) && unserialize($_SESSION['CommentHandler'])->getPageName() == "pancakes"){
	$CommentHandler = unserialize($_SESSION['CommentHandler']);
	$CommentHandler->loadComments();
	$_SESSION['CommentHandler'] = serialize($CommentHandler);


    /*Show post-comment formular if user is logged in*/
	if($_SESSION['LogInSuccess']=="Yes"){
		echo'<p>Kommentera:</p>';
		echo'<form action="includes/postcomment_action.php" method="post">
							  <input type="text" name="commentText" value="Skriv din kommentar">
							  <br>
							  <input type="submit" value="skicka!">
							</form>';
	}
}
/*if a comment handler for the recipe in question does not exist, create a new one*/
else{
	$CommentHandler = new CommentHandler('includes/comments_pancakes.txt');
	$_SESSION['CommentHandler'] = serialize($CommentHandler);

    /*Show post-comment formular if user is logged in*/
	if($_SESSION['LogInSuccess']=="Yes"){
		echo'<p>Kommentera:</p>';
		echo'<form action="includes/postcomment_action.php" method="post">
							  <input type="text" name="commentText" value="Skriv din kommentar">
							  <br>
							  <input type="submit" value="skicka!">
							</form>';
	}
}

?>