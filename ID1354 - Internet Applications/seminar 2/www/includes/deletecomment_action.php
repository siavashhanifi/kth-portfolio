<?php
/*action to delete a comment specified by the user by clicking on a delete button next to a message*/
session_start();
include("CommentHandler.php");
$CommentHandler = unserialize($_SESSION['CommentHandler']);
$redir = $CommentHandler->getPageName();
$CommentHandler->deleteComment($_POST['commentTimeStamp']);
$_SESSION['CommentHandler'] = serialize($CommentHandler);
header("Location: ../".$redir.".php");

?>


