<?php

    /**
    * This function executes in the database the appropriate request regarding friends.
    *@param database, query, query parameters
    *@return a response in the standard form success 0/1 and a message.
    */
    function friendsExecuter($database, $query, $query_params) {
        //execute remove
        try {
            $stmt   = $database->prepare($query);
            $result = $stmt->execute($query_params);
        }
        catch (PDOException $ex) {
            $response["success"] = 0;
            $response["message"] = "Database Error!";
            return ($response);
        }

        $response["success"] = 1;
        $response["message"] = "Execute Successful.";
        return $response;
    }	

    /**
    * This functions creates the query to create a new friend for a given user.
    *@param username, friend, database
    *@return a response from the friendsExecuter.
    */
    function friendSetExecuter ($username, $friend, $database) {
        //initial query
        $query = "INSERT INTO friends (username, friend, created_at) 
                        VALUES (:username, :friend, " . time() . ")";
        $query_params = array(
            ':username' => $username,
            ':friend' => $friend
            );
        $response = friendsExecuter($database, $query, $query_params);
        return $response;
    }

    /**
    * This functions creates the query to remove a friend for a given user.
    *@param username, friend, database
    *@return a response from the friendsExecuter.
    */
    function friendRemoveExecuter ($username, $friend, $database) {
        //initial query
        $query = "DELETE FROM friends WHERE username = :username and friend = :friend;";
        $query_params = array(
            ':username' => $username,
            ':friend' => $friend
            );
        $response = friendsExecuter($database, $query, $query_params);
        return $response;
    }

?>
