<?php

    /**
    * Main function that recieves the request to get all the points.
    * It calls every subfunction for each of the parameters (speed, fuel, distraction, brake).
    * It creates an array filled with four arrays each representing one of the parameters.
    *@param username, database
    *@return Returns a list with four arrays each representing one of the parameters.
    */
    function getPoints ($username, $database) {
        $speed = getSpeedPoints ($username, $database);
        $brake = getBrakePoints ($username, $database);
        $distraction = getDistractionPoints ($username, $database);
        $fuel = getFuelPoints ($username, $database);
        if ($speed['success'] === 0)
        {
            $response["success"] = 0;
            $response["message"] = $speed['message'];
        }
        else if ($brake['success'] === 0)
        {
            $response["success"] = 0;
            $response["message"] = $brake['message'];
        }
        else if ($distraction['success'] === 0)
        {
            $response["success"] = 0;
            $response["message"] = $distraction['message'];
        }
        else if ($fuel['success'] === 0)
        {
            $response["success"] = 0;
            $response["message"] = $fuel['message'];
        }
        else {
            $response["success"] = 1;
            $response["message"] = "Successful Operation";
        }
        $response["speed"] = $speed['posts'];
        $response["brake"] = $brake['posts'];
        $response["distraction"] = $distraction['posts'];
        $response["fuel"] = $fuel['posts'];
        return $response;
    }

    /**
    * Main function that recieves the request to get the filtered points.
    * It calls every subfunction for each of the parameters (speed, fuel, distraction, brake).
    * It creates an array filled with four arrays each representing one of the parameters.
    *@param 
    *@return Returns a list with four arrays each representing one of the parameters with the filtered values.
    */
    function getFilteredPoints ($username, $start, $stop, $database) {
        $speed = getFilteredSpeedPoints ($username, $start, $stop, $database);
        $brake = getFilteredBrakePoints ($username, $start, $stop, $database);
        $distraction = getFilteredDistractionPoints ($username, $start, $stop, $database);
        $fuel = getFilteredFuelPoints ($username, $start, $stop, $database);
        if ($speed['success'] === 0)
        {
            $response["success"] = 0;
            $response["message"] = $speed['message'];
        }
        else if ($brake['success'] === 0)
        {
            $response["success"] = 0;
            $response["message"] = $brake['message'];
        }
        else if ($distraction['success'] === 0)
        {
            $response["success"] = 0;
            $response["message"] = $distraction['message'];
        }
        else if ($fuel['success'] === 0)
        {
            $response["success"] = 0;
            $response["message"] = $fuel['message'];
        }
        else {
            $response["success"] = 1;
            $response["message"] = "Successful Operation";
        }
        $response["speed"] = $speed['posts'];
        $response["brake"] = $brake['posts'];
        $response["distraction"] = $distraction['posts'];
        $response["fuel"] = $fuel['posts'];
        return $response;
    }

    /**
    * Subfunction that calls the metrics executer with the appropriate values to get all the speed
    * points for a particular user.
    *@param username, database
    *@return Returns the response from the metricsExecuter.
    */
    function getSpeedPoints ($username, $database) {
        $response = metricsExecuter ($username, $database, "speedPoints", "speed");
        return $response;
    }

    /**
    * Subfunction that calls the metrics executer with the appropriate values to get all the brake
    * points for a particular user.
    *@param username, database
    *@return Returns the response from the metricsExecuter.
    */
    function getBrakePoints ($username, $database) {
        $response = metricsExecuter ($username, $database, "brakePoints", "brake");
        return $response;
    }

    /**
    * Subfunction that calls the metrics executer with the appropriate values to get all the distraction
    * points for a particular user.
    *@param username, database
    *@return Returns the response from the metricsExecuter.
    */
    function getDistractionPoints ($username, $database) {
        $response = metricsExecuter ($username, $database, "distractionPoints", "distraction");
        return $response;
    }

    /**
    * Subfunction that calls the metrics executer with the appropriate values to get all the fuel
    * points for a particular user.
    *@param username, database
    *@return Returns the response from the metricsExecuter.
    */
    function getFuelPoints ($username, $database) {
        $response = metricsExecuter ($username, $database, "fuelPoints", "fuel");
        return $response;
    }

    /**
    * Subfunction that calls the metrics executer with the appropriate values to the filtered speed
    * points for a particular user.
    *@param username, database
    *@return Returns the response from the metricsExecuter.
    */
    function getFilteredSpeedPoints ($username, $start, $stop, $database) {
        $response = filteredMetricsExecuter ($username, $start, $stop, $database, "speedPoints", "speed");
        return $response;
    }

    /**
    * Subfunction that calls the metrics executer with the appropriate values to the filtered brake
    * points for a particular user.
    *@param username, database
    *@return Returns the response from the metricsExecuter.
    */
    function getFilteredBrakePoints ($username, $start, $stop, $database) {
        $response = filteredMetricsExecuter ($username, $start, $stop, $database, "brakePoints", "brake");
        return $response;
    }

    /**
    * Subfunction that calls the metrics executer with the appropriate values to the filtered dirver distraction
    * points for a particular user.
    *@param username, database
    *@return Returns the response from the metricsExecuter.
    */
    function getFilteredDistractionPoints ($username, $start, $stop, $database) {
        $response = filteredMetricsExecuter ($username, $start, $stop, $database, "distractionPoints", "distraction");
        return $response;
    }

    /**
    * Subfunction that calls the metrics executer with the appropriate values to the filtered Fuel
    * points for a particular user.
    *@param username, database
    *@return Returns the response from the metricsExecuter.
    */
    function getFilteredFuelPoints ($username, $start, $stop, $database) {
        $response = filteredMetricsExecuter ($username, $start, $stop, $database, "fuelPoints", "fuel");
        return $response;
    }

?>
    