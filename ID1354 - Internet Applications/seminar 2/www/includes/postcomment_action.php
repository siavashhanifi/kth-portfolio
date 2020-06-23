<?php
/*action to post a comment*/
session_start();
include("CommentHandler.php");

$userComment = new UserComment($_SESSION['LoggedInAs'], $_POST['commentText'], date("Y-m-d h:i:sa"));
$CommentHandler = unserialize($_SESSION['CommentHandler']);
$redir = $CommentHandler->getPageName();
$CommentHandler->storeComment($userComment);
$_SESSION['CommentHandler'] = serialize($CommentHandler);
echo $redir;
header("Location: ../".$redir.".php");


?>																				 