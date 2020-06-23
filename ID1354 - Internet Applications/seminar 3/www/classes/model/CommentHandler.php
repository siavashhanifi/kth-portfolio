<?php
require_once $_SERVER['DOCUMENT_ROOT'].'\classes\integration\DatabaseHandler.php';
/*
Handles the fetching, adding and deletion of comments from a database
*/
class CommentHandler{
	private $dbHandler = null;
	/*
	Creates an instance of the class and creates a reference to the databasehandler
	*/
	public function __construct() {
		$this->dbHandler = new DatabaseHandler();		
	}
	/*
	Fetches comments for a specified recipe from the database
	@param $recipe the recipe
	@return an array of comments in the format of sql-rows.
	*/
	public function fetchComments($recipe){
		return $this->dbHandler->selectComments($recipe);
		
	}
	
	
	/*
	Deletes a comment from the database
	@param $recipe the recipe @param $commentID the commentID
	*/
	public function deleteComment($recipe, $commentID){
		$this->dbHandler->deleteComment($recipe,$commentID);
	}
	
	/*
	Adds a comment to the database
	@param $recipe the recipe which is commented on @param $username the username of the comment-writer @param $commentText the written comment
	*/
	public function addComment($recipe, $username, $commentText){
		$this->dbHandler->insertComment($recipe, $username, $commentText);
	}
}