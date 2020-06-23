<?php
/*
Handles communication with the MariaDB SQL database
*/
class DatabaseHandler{
	private $db = "TastyRecipes";
	private $dbconnection = null;
	private $hostname = "localhost";
	private $username = "Siavash";
	private $password = "Seminar4db";
	/*
	Creates a DatabaseHandler. Establishes a connection with a specified database.
	*/
    public function __construct() {
		$this->dbconnection = mysqli_connect($this->hostname,$this->username,$this->password,$this->db);
		if ($this->dbconnection->connect_error) {
			die("Database connection failed: " . $dbconnection->connect_error);
		}
    }
	
	/*
	Does a query for a user and returns the result;
	@param $username the username
	@return sql-row of a user
	*/
	public function selectUser($username){
		$preparedStatement = $this->dbconnection->prepare("SELECT Username, Password FROM users WHERE Username = ?");
		$preparedStatement->bind_param("s",$username);
		$preparedStatement->execute();
		$result = $preparedStatement->get_result();
		return $result;
	}
	
	/*
	Selects the comments from a certain recipe page.
	*/
	public function selectComments($recipe){
		$commentVector = new \Ds\Vector();
		
		$query = mysqli_query($this->dbconnection, 'SELECT * FROM '.$recipe.'_comments') or die (mysqli_error($this->dbconnection));
		
		while($row = mysqli_fetch_array($query) ){
			$commentVector->push($row);
		}
		
		return $commentVector->toArray();
	}
	
	/*
	Removes a row from a table
	@param the query
	*/
	public function deleteComment($recipe, $commentID){
		$preparedStatement = $this->dbconnection->prepare('DELETE FROM '.$recipe.'_comments WHERE CommentID= ?');
		$preparedStatement->bind_param("i",$commentID);
		$preparedStatement->execute();
	
	}
	
	/*
	Inserts a comment row into a recipe table
	*/
	public function insertComment($recipe, $username, $commentText){
		$result = $this->selectMaxId($recipe);
		$maxid = $result["MAX(CommentID)"];
		$maxid++;
		$preparedStatement = $this->dbconnection->prepare('INSERT INTO '.$recipe.'_comments'.' VALUES (?, ?, ?)');
		$preparedStatement->bind_param("ssi",$username,$commentText,($maxid));
		$preparedStatement->execute();
	}
	
	private function selectMaxId($recipe){
		$queryString= 'SELECT MAX(CommentID) FROM '.$recipe.'_comments';
		$query = mysqli_query($this->dbconnection, $queryString) or die (mysqli_error($this->dbconnection));
		return mysqli_fetch_array($query);
	}
	
}