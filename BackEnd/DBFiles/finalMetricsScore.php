<?php

    //                                 *********************************************
    //                                 **                                         **
    //                                 **               DB EXECUTOR               **
    //                                 **                                         **
    //                                 *********************************************


    /**
    * Method that executes a query with specific query parameters in the database.
    *@param database, query, query_params
    *@return a response in the standard way, success 0/1 and a message.
    */
    function executeGetFromDB ($database, $query, $query_params) {

        //execute query with received info
        try {
            $stmt   = $database->prepare($query);
            $result = $stmt->execute($query_params);
        }
        catch (PDOException $ex) {
            $response["success"] = 0;
            $response["message"] = "Database Error!";
            return ($response);
        }

        // Finally, we can retrieve all of the found rows into an array using fetchAll 
        $rows = $stmt->fetchAll();
        return $rows;
    }	

    //                                 *********************************************
    //                                 **                                         **
    //                                 **               PARSE METHODS             **
    //                                 **                                         **
    //                                 *********************************************

    /**
    * Parsing method. Takes the results from the database and parses through them to create 
    * the appropriate answer in json form.
    *@param rows. Response from the sql query.
    *@return response in the standard way. It also has an array under the value posts.
    */
    function parseFinalMetrics ($rows) {
    	//Put the rows into the appropriate json parser
        if ($rows) {
            $response["success"] = 1;
            $response["message"] = "Post Available!";
            $response["posts"]   = array();

            foreach ($rows as $row) {
                $post             = array();
                $post["speed"] = $row["speed"];
                $post["brake"] = $row["brake"];
                $post["fuel"] = $row["fuel"];
                $post["distraction"] = $row["distraction"];
                $post["measuredAt"]  = $row["measuredAt"];
                
                //update our repsonse JSON data
                array_push($response["posts"], $post);
            }
            return $response;
        } else {
            $response["success"] = 1;
            $response["message"] = "No Post Available!";
            //$response["query"] = $query;
            //$response["query_params"] = $query_params;
            $response["posts"]   = array();
            return $response;
        }
    }

    /**
    * Parsing method. Takes the results from the database and parses through them to create 
    * the appropriate answer in json form.
    *@param database, users, start, stop, username
    *@return a response in the standard way. It has also a list in the posts section of the response.
    */
    function parseScores($database, $users, $start, $stop, $username) {
	    //Put the rows into the appropriate json parser
        $response["posts"]             = array();
        $userTag = 'username';

        //If its just for friends we need to add the username as well.
        if ($username !== "") {
            $post             = array();
        	$post["speed"] = getFilteredScoresSQL ($database, $username, $start,$stop,"speed");
            $post["brake"] = getFilteredScoresSQL ($database, $username, $start,$stop,"brake");
            $post["fuel"] = getFilteredScoresSQL ($database, $username, $start,$stop,"fuel");
            $post["distraction"] = getFilteredScoresSQL ($database, $username, $start,$stop,"distraction");
            $post["username"] = $username;
            array_push($response["posts"], $post);
            $userTag = 'friend';
        }

        //Check for each of the users in the sql return.
        foreach ($users as $user) {
            $post             = array();
            $post["speed"] = getFilteredScoresSQL ($database, $user[$userTag], $start,$stop,"speed");
            $post["brake"] = getFilteredScoresSQL ($database, $user[$userTag], $start,$stop,"brake");
            $post["fuel"] = getFilteredScoresSQL ($database, $user[$userTag], $start,$stop,"fuel");
            $post["distraction"] = getFilteredScoresSQL ($database, $user[$userTag], $start,$stop,"distraction");
            $post["username"] = $user[$userTag];
            array_push($response["posts"], $post);
        }
            
        //update our repsonse JSON data
        $response["success"] = 1;
        $response["message"] = "Users Available!";
        return $response;
    }


    //                                 *********************************************
    //                                 **                                         **
    //                                 **               HELP METHODS              **
    //                                 **                                         **
    //                                 *********************************************

    /*
        Helper methods to generate a list of users. These users can be either friends for a specific user or 
        all the available users.
    */

    function getAllUsers ($database) {
    	$query = "Select username FROM users;";
        $sqlresponse = executeGetFromDB($database, $query, $query_params);
        return $sqlresponse;
    }

    function getUserFriends ($database, $username) {
    	$query = "Select friend FROM friends where username =:user;";
        $query_params = array(
            ':user' => $username
        );
        $sqlresponse = executeGetFromDB($database, $query, $query_params);
        return $sqlresponse;
    }


    //                                 *********************************************
    //                                 **                                         **
    //                                 **               MAIN METHODS              **
    //                                 **                                         **
    //                                 *********************************************

    /*
        Main methods concerning finalmetrics. They all use a parser as a helper method to put the information
        from the database into a json form understandable for the application.
    */


    function getUserFinalMetrics ($database, $username) {
		$query = "Select * FROM finalmeasurements where username =:user ORDER BY measuredAt DESC LIMIT 1;";
        $query_params = array(
            ':user' => $username
        );
        $sqlresponse = executeGetFromDB($database, $query, $query_params);
        $response = parseFinalMetrics ($sqlresponse);
        return $response;
	}

	function getUserFilteredFinalMetrics ($database, $username, $start, $stop) {
	    $query = "Select * FROM finalmeasurements where username =:user AND measuredAt >= :start AND measuredAt <= :stop ORDER BY measuredAt ASC";
            $query_params = array(
            ':user' => $username,
            ':start' => $start,
            ':stop' => $stop
        );
        $sqlresponse = executeGetFromDB($database, $query, $query_params);
        $response = parseFinalMetrics ($sqlresponse);
        return $response;
	}

	function getAllScores ($database) {
		$users = getAllUsers ($database);
		$response = parseScores($database, $users, 0,time(), "");
		return $response;
	}

	function getFilteredScoresAllUsers($database, $start, $stop) {
		$users = getAllUsers ($database);
		$response = parseScores($database, $users, $start, $stop, "");
		return $response;
	}

	function getFriendsScores ($database, $username) {
		$friends = getUserFriends ($database, $username);
		$response = parseScores($database, $friends, 0,time(), $username);
		return $response;
	}

	function getFriendsFilteredScores ($database, $username, $start, $stop) {
		$friends = getUserFriends ($database, $username);
		$response = parseScores($database, $friends, $start, $stop, $username);
		return $response;
	}

    /**
    * Method to get all the friends from the database.
    *@param database, username
    *@return a response in standard form with a list of friends under the posts section in the response.
    */
    function getAllFriends ($database, $username) {
        $rows = getUserFriends ($database, $username);
        if ($rows) {
            $response["success"] = 1;
            $response["message"] = "Post Available!";
            $response["posts"]   = array();

            foreach ($rows as $row) {
                $post             = array();
                $post["friend"] = $row["friend"];
                
                //update our repsonse JSON data
                array_push($response["posts"], $post);
            }
            return $response;
        } else {
            $response["success"] = 1;
            $response["message"] = "No Post Available!";
            //$response["query"] = $query;
            //$response["query_params"] = $query_params;
            $response["posts"]   = array();
            return $response;
        }
    }

    /**
    * Method to get filtered scores from the database. Creates the appropriate query with the right query parameters
    * and calls the executeGetFromDB method.
    *@param database, username, start, stop, column
    *@return response in standard form with a list of scores under the posts section of the reponse.
    */
    function getFilteredScoresSQL ($database, $username, $start,$stop,$column) {
        $query = "Select SUM($column) FROM finalmeasurements where username = :user AND measuredAt >= :start AND measuredAt <= :stop";
        $query_params = array(
        ':user' => $username,
        ':start' => $start,
        ':stop' => $stop
        );
        $string = "SUM(".$column.")";
        $res = current(executeGetFromDB($database, $query, $query_params));
        $value = $res[$string];
        if (is_null($value))
            $value = 0;
        return $value;        
    }

?>
		