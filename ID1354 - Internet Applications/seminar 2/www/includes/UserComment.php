<?php

/*Stores information about a specific usercomment, including comment text-content, name of user and the time and date of posting.
 *Also has a method to output the usercomment in html code with or without a delete button next to it*/
class UserComment{

	private $commentText = NULL;
	private $username = NULL;
	private $timeStamp = NULL;

    /*creates a new usercomment*/
	function __construct($usernameParam, $commentTextParam, $timeStampParam) {
        $this->commentText = $commentTextParam;
		$this->username = $usernameParam;
		$this->timeStamp = $timeStampParam;
    }

	public function getCommentText(){
		return $this->commentText;
	}
	public function getUsername(){
		return $this->username;
	}
	public function getTimeStamp(){
		return $this->timeStamp;
	}

    /*echos this usercomment as html code in a readable format
     * @param $allowDeletion if true adds delete button next to message*/
	public function echoUsernameAndComment(bool $allowDeletion){
		if($allowDeletion == TRUE){
			echo '<div class="review">
			<p><strong>';
			echo $this->username;
			echo ':</strong> ';
			echo $this->commentText;
			echo'<form action="includes/deletecomment_action.php" method="post">
								  <input type="hidden" name="commentTimeStamp" value="'.$this->timeStamp; echo'">
								  <input timeStamp="deletebutton" type="submit" value="Ta bort">
				</form>';
			echo '</p></div>';
		}
		else{
			echo '<div class="review">
			<p><strong>';
			echo $this->username;
			echo ':</strong> ';
			echo $this->commentText;
			echo '</p></div>';
		}
	}
}
?>