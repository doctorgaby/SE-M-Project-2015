<?php


    /**
    * This function take a user that has logged in through Facebook. Tries to login if the user exists. 
    * Otherwise it creates a user on the database with the users email.
    *@param username, database
    *@return Returns a response with the common format {success:"0/1", message:"message"}
    */
	function fblogin($username, $database) {
		$password = "facebookuser";
	    $response_login = login($username, $password, $database);
	    if ($response_login['success'] === 0) {
	    	$response_register = register($username, $password, $database);
	    }
	    if ($response_login['success'] === 1 || $response_register['success'] === 1) {
    		$response["success"] = 1;
            $response["message"] = "Welcome through Facebook login.";
            return $response;
        } else {
        	$response["success"] = 0;
            $response["message"] = "Problem with Facebook login.";
            $response["message2"] = $response_login['message'];
            $response["message3"] = $response_register['message'];

            return $response;
        }
	}

?> 
