<?php

    /**
    * This function executes the getter in the database. It takes in the query, query parameters and a tag that
    * depends on which measurement it wants to take from the sql response.
    *@param database, query, query parameters, tag
    *@return Returns a response with the common format {success:"0/1", message:"message", posts: list}
    */
    function executeGet ($database, $query, $query_params, $tag) {

        //execute query
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

        //Put the rows into the appropriate json parser
        if ($rows) {
            $response["success"] = 1;
            $response["message"] = "Post Available!";
            $response["posts"]   = array();
            
            foreach ($rows as $row) {
                $post             = array();
                $post[$tag] = $row[$tag];
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
    * Creates the query and the query parameters for all the values depending on the table it wants to look into.
    *@param username, database, table, tag
    *@return Returns a response with the common format {success:"0/1", message:"message", posts: list}
    */
    function metricsExecuter ($username, $database, $table, $tag) {
        //initial query
        $query = "Select * FROM $table where username =:user ORDER BY measuredAt ASC";
        $query_params = array(
            ':user' => $username
        );
        $response = executeGet($database, $query, $query_params, $tag);
        return $response;
    }

    /**
    * Creates the query and the query parameters filtered values depending on the table it wants to look into.
    *@param username, start, stop, database, table, tag
    *@return Returns a response with the common format {success:"0/1", message:"message", posts: list}
    */
    function filteredMetricsExecuter ($username, $start, $stop, $database, $table, $tag) {
        //initial query
        $query = "Select * FROM $table where username =:user AND measuredAt >= :start AND measuredAt <= :stop ORDER BY measuredAt ASC";
        $query_params = array(
            ':user' => $username,
            ':start' => $start,
            ':stop' => $stop
        );
        $response = executeGet($database, $query, $query_params, $tag);
        return $response;
    }
?>
