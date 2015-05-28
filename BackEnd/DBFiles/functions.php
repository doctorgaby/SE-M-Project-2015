<?php

	/**
     * Encrypting password
     * @param password
     *@return returns salt and encrypted password
     */
	function hashSSHA($password) {
        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }
 
    /**
     * Decrypting password
     * @param salt, password
     *@return returns hash string
     */
    function checkhashSSHA($salt, $password) {
 
        $hash = base64_encode(sha1($password . $salt, true) . $salt);
 
        return $hash;
    }

    /**
    * Function to see if a username exists
    * @param username
    *@return Returns a response with the common format {success:"0/1", message:"message", error:"error"}
    */
    function userExists($username, $database) {
        $query = " SELECT 1 FROM users WHERE username = :user";
        $query_params = array( 
            ':user' => $username
        );
        try {
            // These two statements run the query against the database table. 
            $stmt   = $database->prepare($query);
            $result = $stmt->execute($query_params);
        }
        catch (PDOException $ex) {
            $response["success"] = 0;
            $response["error"] = 1;
            $response["message"] = "Database Error. Please Try Again!";
            return $response;
        }
        $row = $stmt->fetch();
        if ($row) {
            $response["success"] = 1;
            $response["error"] = 0;
            $response["message"] = "The user does exist.";
            return $response;
        }
        else {
            $response["success"] = 0;
            $response["error"] = 0;
            $response["message"] = "The user does not exist.";
            return $response;
        }
    }
?>