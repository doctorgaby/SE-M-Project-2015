<?php

    /**
    * Function to validate a user in the database. 
    *@param username, password, database
    *@return Returns a response with the common format {success:"0/1", message:"message"}
    */
    function login ($username, $password, $database)
    {
        $query = " 
                SELECT 
                    username, 
                    encrypted_password, 
                    salt 
                FROM users 
                WHERE 
                    username = :user 
            ";
        
        $query_params = array(
            ':user' => $username
        );
        
        try {
            $stmt   = $database->prepare($query);
            $result = $stmt->execute($query_params);
        }
        catch (PDOException $ex) {
            $response["success"] = 0;
            $response["message"] = "Database Error1. Please Try Again!";
            return $response;
        }

        $login_ok = false;

        $row = $stmt->fetch();
        if ($row) {
            //we encrypt the received password and check it against the encrypted one.
            $encrypted_password = $row['encrypted_password'];
            $salt = $row['salt'];
            $check = checkhashSSHA($salt, $password);


            if ($encrypted_password===$check) {
                $login_ok = true;
            }
        }
        
        // Here a message is generated whether or not the user logged in successfully.
        if ($login_ok) {
            $response["success"] = 1;
            $response["message"] = "Login successful!";
            return $response;
        } else {
            $response["success"] = 0;
            $response["message"] = "Invalid Credentials!";
            return $response;
        }
    }
?> 
		