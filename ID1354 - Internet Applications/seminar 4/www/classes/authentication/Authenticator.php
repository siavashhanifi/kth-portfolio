<?php
require_once $_SERVER['DOCUMENT_ROOT'].'\classes\integration\DatabaseHandler.php';

class Authenticator{
	
	private $dbHandler = null;
	private $userLoggedIn = False;
	private $user = null;
	
	/*
	Creates an instance of the class. Validates user credentials and 
	saves the result in private member variables.
	@param $username the username @param $password the password
	*/
    public function __construct($username, $password){
		$this->dbHandler = new DatabaseHandler();
		$result = $this->dbHandler->selectUser($username);
		$md5_password = md5($password); //password encryption

		
		if($result->num_rows !== 0){
			$this->user = $result->fetch_assoc();
			if($this->user['Password'] === $md5_password)
				$this->userLoggedIn = True;
			else
				$this->userLoggedIn = False;
		}
		else
			$this->userLoggedIn = False;

    }
	
	public function getUsername(){
		if($this->userLoggedIn == True)
			return $this->user['Username'];
		else
			return "none";
	}
	
	public function isLoggedIn(){
		return $this->userLoggedIn;
	}
}
