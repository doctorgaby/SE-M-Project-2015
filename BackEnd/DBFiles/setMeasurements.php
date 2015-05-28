<?php

    /**
    * Main method to set measurements from a list. The method will go through each list (all values) 
    * and all the appropriate methods will be called with the values.
    *@param username, list, database
    *@return Returns a standard msg with success 0/1 and a message.
    */
    function setMeasurements ($username, $list, $database) {
        $exists = userExists($username, $database);
        if ($exists['success']===1) {
            $speed = $list['speed'];
            $fuel = $list['fuel'];
            $distraction = $list['distraction'];
            $brake = $list['brake'];
            if (is_array ($speed) && is_array($fuel) && is_array($distraction) && is_array($brake)) { 
                foreach($speed as $pair) {
                    $responseSet = setSpeedMeasurements($username, $pair['speed'], $pair['measuredAt'], $database);
                    if ($responseSet['success'] === 0) {
                        $response["success"] = 0;
                        $response["message"] = $responseSet['message'];
                        return $response;
                    }
                }
                foreach($fuel as $pair) {
                    $responseSet = setFuelMeasurements($username, $pair['fuel'], $pair['measuredAt'], $database);
                    if ($responseSet['success'] === 0) {
                        $response["success"] = 0;
                        $response["message"] = $responseSet['message'];
                        return $response;
                    }
                }
                foreach($distraction as $pair) {
                    $responseSet = setDistractionMeasurements($username, $pair['distraction'], $pair['measuredAt'], $database);
                    if ($responseSet['success'] === 0) {
                        $response["success"] = 0;
                        $response["message"] = $responseSet['message'];
                        return $response;
                    }
                }
                foreach($brake as $pair) {
                    $responseSet = setBrakeMeasurements($username, $pair['brake'], $pair['measuredAt'], $database);
                    if ($responseSet['success'] === 0) {
                        $response["success"] = 0;
                        $response["message"] = $responseSet['message'];
                        return $response;
                    }
                }
                $response['success'] = 1;
                $response['message'] = "Measurements Succesfully added to the database.";
                return $response;
            } else {
                $response["success"] = 0;
                $response["message"] = "Error. Corrupt Lists.";
                //Test
                $response["speedlist"] = $speed;
                $response["distractionList"] = $distraction;
                $response["fuelList"] = $fuel;
                $response["brakeList"] = $brake;
                $response["wholelist"] = $list;
                return $response;
            }
        } else {
            $response["success"] = 0;
            $response["message"] = "Username not recognized.";
            return $response;
        }
    }

    //                                 *********************************************
    //                                 **                                         **
    //                                 **             Helper Methods              **
    //                                 **                                         **
    //                                 *********************************************

    /*
        Each of these methods receive a username, a value, a timestamp and the database reference.
        They all call the metricsSetExecuter with the appropriate values.
        They all return a response with the standard structure.
    */

    function setBrakeMeasurements ($username, $brake, $measuredAt, $database) {
        $response = metricsSetExecuter ($username, $brake, $measuredAt, $database, "brakeMeasurements", "brake");
        return $response;
    }

    function setSpeedMeasurements ($username, $speed, $measuredAt, $database) {
        $response = metricsSetExecuter ($username, $speed, $measuredAt, $database, "speedMeasurements", "speed");
        return $response;
    }

    function setFuelMeasurements ($username, $fuel, $measuredAt, $database) {
        $response = metricsSetExecuter ($username, $fuel, $measuredAt, $database, "fuelMeasurements", "fuel");
        return $response;
    }

    function setDistractionMeasurements ($username, $distraction, $measuredAt, $database) {
        $response = metricsSetExecuter ($username, $distraction, $measuredAt, $database, "distractionMeasurements", "distraction");
        return $response;
    }
?>