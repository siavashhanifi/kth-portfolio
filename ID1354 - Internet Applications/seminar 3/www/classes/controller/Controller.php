<?php
require_once $_SERVER['DOCUMENT_ROOT'].'\classes\authentication\Authenticator.php';
require_once $_SERVER['DOCUMENT_ROOT'].'\classes\Model\CommentHandler.php';

/*
A class that delegates operations from the view to the appropriate operations in the model and other lower layers.
*/
class Controller{
	private $authenticator = null;
	private $commentHandler = null;
	
	/*
	Creates a controller
	@param $commentHandler a reference to a commenthandler
	*/
    public function __construct($commentHandler) {
		$this->commentHandler = $commentHandler;
	}
	
	/*
	fetches an array of comments for a specified recipe
	@param $recipe the specified recipe
	@return an array of comments in the format of sql-rows. 
	*/
	public function fetchComments($recipe){
		return $this->commentHandler->fetchComments($recipe);
	}
	
	/*
	Removes a specific comment
	@param $recipe the recipe @param $commentID the id of the comment
	*/
	public function deleteComment($recipe, $commentID){
		$this->commentHandler->deleteComment($recipe, $commentID);
	}
	
	/*
	Adds a new comment
	@param $recipe the recipe which is commented on @param $username the username of the comment-writer @param $commentText the written comment
	*/
	public function addComment($recipe, $username, $commentText){
		$this->commentHandler->addComment($recipe,$username,$commentText);
	}
	
	
	/*
	Attempts a login with given credentials.
	@param $username the username @param $password the password
	@return if valid credentials: $username
	@return if invalid credentials: null
	*/
	public function login($username, $password){		
		$this->authenticator = new Authenticator($username, $password);
		if($this->authenticator->isLoggedIn())
			return $username;
		else
			return null;
	}
	
	/*
	Logs the current user out.
	*/
	public function logout(){
		$this->authenticator = null;
	}
	
}