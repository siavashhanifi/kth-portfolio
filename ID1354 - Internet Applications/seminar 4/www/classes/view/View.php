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
	
	private function unserializeController(){
		if(!isset($_SESSION['controller'])){
			$_SESSION['controller'] = serialize($this->controller);
		}
		$this->controller = unserialize($_SESSION['controller']);
	}
	
	private function serializeController(){
		$_SESSION['controller'] = serialize($this->controller);
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
					//ajax this
					require $_SERVER['DOCUMENT_ROOT'].'\views\meatballs.php';
					break;
				case '/pancakes':
					require $_SERVER['DOCUMENT_ROOT'].'\views\pancakes.php';
					break;
				case '/get_comments':
					$this->echoComments($this->controller->fetchComments($_POST['recipe']));
					break;
				case '/login':
					require $_SERVER['DOCUMENT_ROOT'].'\views\login.php';
					break;
				case '/login_post':
					$this->unserializeController();
					$this->login($_POST['username'],$_POST['password']);
					$this->serializeController();
					break;
				case '/logout':
					$this->unserializeController();
					$this->logout();
					$this->serializeController();
					break;
				case '/login_status':
					$this->unserializeController();
					$this->getLoginStatus();
					$this->serializeController();
					break;
				case '/post_comment':
					$this->controller->addComment($_POST['recipe'], $_POST['username'], $_POST['commentText']);
					break;
				case '/delete_comment':
					$this->controller->deleteComment($_POST['recipe'],$_POST['commentID']);
					break;
				default:
					require $_SERVER['DOCUMENT_ROOT'].'\views\404.php';
					break;
				}
	}
	
	private function getLoginStatus(){
		echo $this->controller->getUsername();
	}
	
	
	
	private function echoComments($commentList){
		echo json_encode($commentList);
	}
	
	
	private function login($username, $password){
		$loginSuccessful = $this->controller->login($username,$password);
		if($loginSuccessful)
			echo "true";
		else
			echo "false";
	}
	
	private function logout(){
		$this->controller->logout();
	}
		
}