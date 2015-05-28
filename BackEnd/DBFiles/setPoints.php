<?php

    /**
    * Main method to set points from a list. The method will go through each list (all values) and all the 
    * appropriate methods will be called with the values.
    *@param username, list, database
    *@return Returns a standard msg with success 0/1 and a message.
    */
    function setPoints ($username, $list, $database) {
        if (userExists($username, $database)) {
            $speed = $list['speed'];
            $fuel = $list['fuel'];
            $distraction = $list['distraction'];
            $brake = $list['brake'];
            if (is_array ($speed) && is_array($fuel) && is_array($distraction) && is_array($brake)) {
                foreach($speed as $pair) {
                    $responseSet = setSpeedPoints($username, $pair['speed'], $pair['measuredAt'], $database);
                    if ($responseSet['success'] === 0) {
                        $response["success"] = 0;
                        $response["message"] = $responseSet['message'];
                        return $response;
                    }
                }
                foreach($fuel as $pair) {
                    $responseSet = setFuelPoints($username, $pair['fuel'], $pair['measuredAt'], $database);
                    if ($responseSet['success'] === 0) {
                        $response["success"] = 0;
                        $response["message"] = $responseSet['message'];
                        return $response;
                    }
                }
                foreach($distraction as $pair) {
                    $responseSet = setDistractionPoints($username, $pair['distraction'], $pair['measuredAt'], $database);
                    if ($responseSet['success'] === 0) {
                        $response["success"] = 0;
                        $response["message"] = $responseSet['message'];
                        return $response;
                    }
                }
                foreach($brake as $pair) {
                    $responseSet = setBrakePoints($username, $pair['brake'], $pair['measuredAt'], $database);
                    if ($responseSet['success'] === 0) {
                        $response["success"] = 0;
                        $response["message"] = $responseSet['message'];
                        return $response;
                    }
                }
                $response['success'] = 1;
                $response['message'] = "Points Succesfully added to the database.";
                return $response;
            } else
            {
                $response["success"] = 0;
                $response["message"] = "Error. Corrupt Lists.";
                return $response;
            }
        }
        else {
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

    function setBrakePoints ($username, $brake, $measuredAt, $database) {
        $response = metricsSetExecuter ($username, $brake, $measuredAt, $database, "brakePoints", "brake");
        return $response;
    }

    function setSpeedPoints ($username, $speed, $measuredAt, $database) {
        $response = metricsSetExecuter ($username, $speed, $measuredAt, $database, "speedPoints", "speed");
        return $response;
    }

    function setFuelPoints ($username, $fuel, $measuredAt, $database) {
        $response = metricsSetExecuter ($username, $fuel, $measuredAt, $database, "fuelPoints", "fuel");
        return $response;
    }

    function setDistractionPoints ($username, $distraction, $measuredAt, $database) {
        $response = metricsSetExecuter ($username, $distraction, $measuredAt, $database, "distractionPoints", "distraction");
        return $response;
    }
?>
