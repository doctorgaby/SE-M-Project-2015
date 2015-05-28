<?php

    /**
    * Main function that recieves the request to get all the measurements.
    * It calls every subfunction for each of the parameters (speed, fuel, distraction, brake).
    * It creates an array filled with four arrays each representing one of the parameters.
    *@param username, database
    *@return Returns a list with four arrays each representing one of the parameters.
    */
    function getMeasurements ($username, $database) {
        $speed = getSpeedMeasurements ($username, $database);
        $brake = getBrakeMeasurements ($username, $database);
        $distraction = getDistractionMeasurements ($username, $database);
        $fuel = getFuelMeasurements ($username, $database);
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
    * Main function that recieves the request to get the filtered measurements.
    * It calls every subfunction for each of the parameters (speed, fuel, distraction, brake).
    * It creates an array filled with four arrays each representing one of the parameters.
    *@param username, start, stop, database
    *@return Returns a list with four arrays each representing one of the parameters with the filtered values.
    */
    function getFilteredMeasurements ($username, $start, $stop, $database) {
        $speed = getFilteredSpeedMeasurements ($username, $start, $stop, $database);
        $brake = getFilteredBrakeMeasurements ($username, $start, $stop, $database);
        $distraction = getFilteredDistractionMeasurements ($username, $start, $stop, $database);
        $fuel = getFilteredFuelMeasurements ($username, $start, $stop, $database);
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
    * measurements for a particular user.
    *@param username, database
    *@return Returns the response from the metricsExecuter.
    */
    function getSpeedMeasurements ($username, $database) {
        $response = metricsExecuter ($username, $database, "speedMeasurements", "speed");
        return $response;
    }

    /**
    * Subfunction that calls the metrics executer with the appropriate values to get all the brake
    * measurements for a particular user.
    *@param username, database
    *@return Returns the response from the metricsExecuter.
    */
    function getBrakeMeasurements ($username, $database) {
        $response = metricsExecuter ($username, $database, "brakeMeasurements", "brake");
        return $response;
    }

    /**
    * Subfunction that calls the metrics executer with the appropriate values to get all the driver distraction
    * measurements for a particular user.
    *@param username, database
    *@return Returns the response from the metricsExecuter.
    */
    function getDistractionMeasurements ($username, $database) {
        $response = metricsExecuter ($username, $database, "distractionMeasurements", "distraction");
        return $response;
    }

    /**
    * Subfunction that calls the metrics executer with the appropriate values to get all the fuel consumption
    * measurements for a particular user.
    *@param username, database
    *@return Returns the response from the metricsExecuter.
    */
    function getFuelMeasurements ($username, $database) {
        $response = metricsExecuter ($username, $database, "fuelMeasurements", "fuel");
        return $response;
    }

    /**
    * Subfunction that calls the metrics executer with the appropriate values to get the filtered speed
    * measurements for a particular user.
    *@param username, database
    *@return Returns the response from the metricsExecuter.
    */
    function getFilteredSpeedMeasurements ($username, $start, $stop, $database) {
        $response = filteredMetricsExecuter ($username, $start, $stop, $database, "speedMeasurements", "speed");
        return $response;
    }

    /**
    * Subfunction that calls the metrics executer with the appropriate values to get the filtered brake
    * measurements for a particular user.
    *@param username, database
    *@return Returns the response from the metricsExecuter.
    */
    function getFilteredBrakeMeasurements ($username, $start, $stop, $database) {
        $response = filteredMetricsExecuter ($username, $start, $stop, $database, "brakeMeasurements", "brake");
        return $response;
    }

    /**
    * Subfunction that calls the metrics executer with the appropriate values to get the filtered driver distraction
    * measurements for a particular user.
    *@param username, database
    *@return Returns the response from the metricsExecuter.
    */
    function getFilteredDistractionMeasurements ($username, $start, $stop, $database) {
        $response = filteredMetricsExecuter ($username, $start, $stop, $database, "distractionMeasurements", "distraction");
        return $response;
    }

    /**
    * Subfunction that calls the metrics executer with the appropriate values to get the filtered fuel consumption
    * measurements for a particular user.
    *@param username, database
    *@return Returns the response from the metricsExecuter.
    */
    function getFilteredFuelMeasurements ($username, $start, $stop, $database) {
        $response = filteredMetricsExecuter ($username, $start, $stop, $database, "fuelMeasurements", "fuel");
        return $response;
    }

?>
	