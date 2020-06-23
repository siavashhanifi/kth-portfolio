<?php
require_once $_SERVER['DOCUMENT_ROOT'].'\classes\controller\Controller.php';
require_once $_SERVER['DOCUMENT_ROOT'].'\classes\Model\CommentHandler.php';
require_once $_SERVER['DOCUMENT_ROOT'].'\classes\view\View.php';

/*
Handles the startup of the web application.
*/
class Startup{
	
	private $view = null;
	/*
	 Creates a startup function and initializes the controller and the view
	 and provides the references.
	*/
    public function __construct(){
        $this->controller = new Controller(new CommentHandler());
		$this->view = new View($this->controller);
    }
	
	public function getView(){
		return $this->view;
	}
}