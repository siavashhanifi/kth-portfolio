<?php
session_start();
include('UserComment.php');
/*Handles the loading, deletion and posting of comments to a specified recipe page, from/to a textfile used to store the comments*/
class CommentHandler{

	private $arrayOfComments;
	private $commentStorageFile; //holds path 'include/<storage file name>
    private $commentStorageFileNoInclude; //holds '<storage file name>'


    /*Removes whitelines from the commentstoragefile*/
    private function removeWhiteLines(){
        $arr = file($this->commentStorageFileNoInclude);
        $fileToWrite = fopen($this->commentStorageFileNoInclude, "r+");
        foreach($arr as $line){
            if(strcmp($line, "\n") != 0 || strcmp(ltrim($line), '') != 0){
                fwrite($fileToWrite, $line);
            }
        }
        fclose($fileToWrite);
    }

	function __construct($commentStorageFileParam) {
		$this->commentStorageFile = $commentStorageFileParam;
        $this->commentStorageFileNoInclude = str_replace("includes/", '', $commentStorageFileParam);
		$this->loadComments();
    }

    /*Loads the comments from the textfile into an indexed array and echos them to html*/
	public function loadComments(){
		$arrayOfComments;
		$commentsFile = fopen($this->commentStorageFile, "r");
		$n = 0;
		while(!feof($commentsFile)){
			$tempTimeStamp = fgets($commentsFile);
			$tempUsr = rtrim(fgets($commentsFile));
			$tempComment = fgets($commentsFile);

            if(ltrim($tempTimeStamp) == '' || ltrim($tempUsr) == ''  || ltrim($tempComment) == ''){
                continue;
            }
            else{
				$arrayOfComments[$n] = new UserComment($tempUsr, $tempComment, $tempTimeStamp);
				if($arrayOfComments[$n]->getUsername() == $_SESSION['LoggedInAs']){
					$arrayOfComments[$n]->echoUsernameAndComment(TRUE);
				}
				else{
					$arrayOfComments[$n]->echoUsernameAndComment(FALSE);
				}
				$n = $n + 1;
            }
		}
		fclose($commentsFile);
		$this->arrayOfComments = $arrayOfComments;
	}

    /*Stores a usercomment into the storage file
     * @param $userComment the usercomment*/
    public function storeComment(UserComment $userComment){
        $fileToWrite = fopen($this->commentStorageFileNoInclude, 'a+');
		if(!(fgets($fileToWrite) == "")){
			fwrite($fileToWrite, "\n");
		}
		fwrite($fileToWrite, $userComment->getTimeStamp());
		fwrite($fileToWrite, "\n");
		fwrite($fileToWrite, $userComment->getUsername(), strlen($userComment->getUsername()));
		fwrite($fileToWrite, "\n");
		fwrite($fileToWrite, $userComment->getCommentText(), strlen($userComment->getCommentText()));
		fclose($fileToWrite);
        $this->removeWhiteLines();

	}

    /*Deletes the comment associated with the specified timestamp
     * @param $commentTimeStamp the specified timestamp*/
	public function deleteComment($commentTimeStamp){
		$fileToWrite = fopen($this->commentStorageFileNoInclude, "w");
		foreach($this->arrayOfComments as $userComment){
			if(strcmp(rtrim(ltrim($userComment->getTimeStamp())), rtrim(ltrim($commentTimeStamp))) == 0){
			}
			else{
				fwrite($fileToWrite, $userComment->getTimeStamp());
				fwrite($fileToWrite, $userComment->getUsername()."\n");
                fwrite($fileToWrite, $userComment->getCommentText());
            }
		}
		fclose($fileToWrite);
	}

    /*returns which recipe page this CommentHandler is associated with*/
    public function getPageName(){
        $pagename = str_replace('comments_', '',$this->commentStorageFileNoInclude);
        $pagename = str_replace('.txt', '',$pagename);
        return trim($pagename);
    }
}
?>