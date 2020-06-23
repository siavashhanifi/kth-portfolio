<?php
/*All requests come here and get sent to the View*/
require_once $_SERVER['DOCUMENT_ROOT'].'\classes\startup\Startup.php';

if (session_status() == PHP_SESSION_NONE) {
    session_start();
}

$request = $_SERVER['REQUEST_URI'];
	
if (!isset($_SESSION['view'])) {
	$startup = new Startup();
	$view = $startup->getView();
	$view->presentPage($request);
	$SESSION['view'] = serialize($view);
}

else{
	$view = unserialize($_SESSION['view']);
	$view->presentPage($request);
	$_SESSION['view'] = serialize($view);
}