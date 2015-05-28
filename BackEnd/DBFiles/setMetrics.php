<?php

    /**
    * Method that executes the set into the database.
    *@param database, query and query parameters.
    *@return response with the standard form.
    */
    function executeSet ($database, $query, $query_params) {
        //execute insertion
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
        $response["message"] = "Data Added Succesfully.";
        return $response;
    }	

    /**
    * Method that constructs the appropriate query and query parameters to call the executeSet.
    * This method is used to set values into a given table and a given column.
    *@param username, value, measuredAt, database, table and column
    *@return the response received from the executeSet.
    */
    function metricsSetExecuter ($username, $value, $measuredAt, $database, $table, $column) {
        //initial query
        $query = "INSERT INTO $table (username, $column, measuredAt, uploadedAt) 
                        VALUES (:username, :value, :measuredAt, " . time() . ")";
        $query_params = array(
            ':username' => $username,
            ':value' => $value,
            ':measuredAt' => $measuredAt
            );
        $response = executeSet($database, $query, $query_params);
        return $response;
    }

    /**
    * Method that contructs the appropriate query and query parameters to call the executeSet.
    * This method is used to set the final metrics of a user.
    *@param username, speed, brake, fuel, distraction, measuredAt, database
    *@return the response received form the executeSet
    */
    function finalMetricsSetExecuter ($username, $speed, $brake, $fuel, $distraction, $measuredAt, $database) {
        //initial query
        $query = "INSERT INTO finalmeasurements (username, speed, brake, fuel, distraction, measuredAt, uploadedAt) 
                        VALUES (:username, :speed, :brake,:fuel,:distraction, :measuredAt, " . time() . ")";
        $query_params = array(
            ':username' => $username,
            ':speed' => $speed,
            ':brake' => $brake,
            ':fuel' => $fuel,
            ':distraction' => $distraction,
            ':measuredAt' => $measuredAt
            );
        $response = executeSet($database, $query, $query_params);
        return $response;
    }
?>
