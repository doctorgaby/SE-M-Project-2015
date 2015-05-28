<?php

    /**
    * Function to register a user in the database. 
    *@param username, password, database
    *@return Returns a response with the common format {success:"0/1", message:"message"}
    */
    function register ($username, $password, $database)
    {
        if (empty($username) || empty($password)) {
            
            // Create some data that will be the JSON response 
            $response["success"] = 0;
            $response["message"] = "Please Enter a Username and Password. user: '$username' pass: '$password'";
            return $response;
        }
        
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
            $response["message"] = "Database Error1. Please Try Again!";
            return $response;
        }
        $row = $stmt->fetch();
        if ($row) {
            $response["success"] = 0;
            $response["message"] = "I'm sorry, this username is already in use.";
            return $response;
        }

        //If this part is reached then it means that the username does not exist.
        $hash = hashSSHA($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"]; // salt

        $query = "INSERT INTO users ( username, encrypted_password, salt, created_at ) 
                    VALUES (:user, :encpass, :salt, now() ) ";
        
        $query_params = array(
            ':user' => $username,
            ':encpass' => $encrypted_password,
            ':salt' => $salt
        );
        try {
            $stmt   = $database->prepare($query);
            $result = $stmt->execute($query_params);
        }
        catch (PDOException $ex) {
            $response["success"] = 0;
            $response["message"] = "Database Error2. Please Try Again!";
            return $response;
        }
        $response["success"] = 1;
        $response["message"] = "User Successfully Added!";
        return $response;
    }
?>
