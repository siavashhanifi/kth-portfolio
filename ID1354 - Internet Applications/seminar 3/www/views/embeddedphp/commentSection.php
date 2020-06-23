<?php
$recipe = $_SESSION['recipe'];
$commentList = $GLOBALS[$recipe . '_comments'];
foreach($commentList as $comment){
	if(isset($_SESSION['LogInSuccessful']) && $_SESSION['LogInSuccessful']==True && $comment['Username'] ===  $_SESSION['LoggedInAs'])
		echo '<div class="review" id='.$comment['CommentID'].'>  <p><strong>'.$comment['Username'].': </strong>'.$comment['CommentText'].'</p><form action="/delete_comment" method="post"><input type="hidden" name="commentID" value="'.$comment['CommentID'].'"><input type="submit" value="remove"/></form></div>';
	else
		echo '<div class="review" id='.$comment['CommentID'].'>  <p><strong>'.$comment['Username'].': </strong>'.$comment['CommentText'].'</p></div>';
}

if(isset($_SESSION['LogInSuccessful']) && $_SESSION['LogInSuccessful']==True){
		echo '<div id="postcommentsection">
				<p>Comment:</p>
				<form action="/post_comment" method="post">
				<input type="text" name="commentText" value="Write your review!">
				<br>
				<input type="submit" value="send!">
			</form>
			</div>';
}