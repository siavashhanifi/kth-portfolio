<?php
require_once $_SERVER['DOCUMENT_ROOT'].'/classes/controller/Controller.php';


/*
A class that handles the presentation of pages by implementing a simple routing mechanism
and adapting the views/pages through the manipulation of session variables.
*/
class View{

	private $controller;

	/*
	Creates a View
	@param $controller a reference to the controller.
	*/
    public function __construct($controller) {
        $this->controller = $controller;
    }
	
	/*
	Takes a request and updates the view based on it.
	@param $request the request
	*/
	public function presentPage($request){
				switch ($request) {
				case '/' :
					require $_SERVER['DOCUMENT_ROOT'].'\views\index.php';
					break;
				case '' :
					require $_SERVER['DOCUMENT_ROOT'].'\views\index.php';
					break;
				case '/index' :
					require $_SERVER['DOCUMENT_ROOT'].'\views\index.php';
					break;
				case '/calendar' :
					require $_SERVER['DOCUMENT_ROOT'].'\views\calendar.php';
					break;
				case '/meatballs':
					$this->updateCommentView('meatballs', $this->controller->fetchComments('meatballs'));
					require $_SERVER['DOCUMENT_ROOT'].'\views\meatballs.php';
					break;
				case '/pancakes':
					$this->updateCommentView('pancakes', $this->controller->fetchComments('pancakes'));
					require $_SERVER['DOCUMENT_ROOT'].'\views\pancakes.php';
					break;
				case '/login':
					require $_SERVER['DOCUMENT_ROOT'].'\views\login.php';
					break;
				case '/login_post':
					$this->login($_POST['username'],$_POST['password']);
					require $_SERVER['DOCUMENT_ROOT'].'\views\login.php';
					break;
				case '/logout_post':
					$this->logout();
					$this->presentPage($_SESSION['prevPage']);
					break;
				case '/post_comment':
					$this->controller->addComment($_SESSION['recipe'], $_SESSION['LoggedInAs'], $_POST['commentText']);
					$this->presentPage('/'.$_SESSION['recipe']);
					break;
				case '/delete_comment':
					$this->controller->deleteComment($_SESSION['recipe'],$_POST['commentID']);
					$this->presentPage('/'.$_SESSION['recipe']); 
					break;
				default:
					require $_SERVER['DOCUMENT_ROOT'].'\views\404.php';
					break;
				}
	}
	
	
	/*
	Updates the session variables which in turn alter the presentation of
	the views. The views will show if an login attempt has been successful or not
	based on the argument value.
	@param $user holds the username of an successfully logged in user or null.
	*/
	private function updateLoginView($user){
		if($user != null){
			$_SESSION['LogInSuccessful'] = True;
			$_SESSION['LoggedInAs'] = $user;
		}
		else
			$_SESSION['LogInSuccessful'] = False;
	}
	
	/*
	Updates the session variables which in turn alter the presentation of
	the views. Unsets the session variables that indicate if a user is logged in or if a
	failed login attempt has occured.
	*/
	private function resetLoginView(){
		unset($_SESSION['LogInSuccessful']);
		unset($_SESSION['LoggedInAs']);
	}
	
	/*
	Updates a session variable and a global variable with a recipe and the an array of comments.
	@param $recipe a specified recipe @param $commentList an array of sql-rows(comments) in the shape of associative arrays.
	*/
	private function updateCommentView($recipe, $commentList){
		$_SESSION['recipe'] = $recipe;
		$GLOBALS[$recipe . '_comments'] = $commentList;
	}
	
	/*
	Attempts logging a user in
	@param $username the username @param $password the password 
	*/
	private function login($username, $password){
		$user = $this->controller->login($username,$password);
		$this->updateLoginView($user);
	}
	
	/*
	Logs out the currently logged in user
	*/
	private function logout(){
		$this->controller->logout();
		$this->resetLoginView();
	}
		
}