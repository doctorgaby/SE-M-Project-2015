<?php

//Requires the connection to the database.
require_once("config.inc.php");
//Includes once all the files used by the Index.
include_once("DBFiles/register.php");
include_once("DBFiles/login.php");
include_once("DBFiles/fblogin.php");
include_once("DBFiles/functions.php");
include_once("DBFiles/setMeasurements.php");
include_once("DBFiles/getMeasurements.php");
include_once("DBFiles/setPoints.php");
include_once("DBFiles/getPoints.php");
include_once("DBFiles/getMetrics.php");
include_once("DBFiles/setMetrics.php");
include_once("DBFiles/finalMetricsScore.php");
include_once("DBFiles/friends.php");

//Checks if the Post is empty. This allows us to have a HTTP front end to test
// the database in a more practical way.
if (!empty($_POST)) {
    //Stores into the variable choice the action submitted in the post.
    $choice = $_POST['action'];

    //Possible action choices. They all return a response which will be returned to the program.
    // As a standard the response will have a success and a message. This response can have 
    // additional information depending on whether or not information is expected from the action
    // of choice.

    if ($choice === "login") {
        // Login choice. Reads the username and the password and calls the login method.
    	$username = $_POST['username'];
    	$password = $_POST['password'];
    	$response = login ($username, $password, $db);
    } else if ($choice === "register") {
        // Register choice. Reads the username and the password and call the register method.
    	$username = $_POST['username'];
    	$password = $_POST['password'];
    	$response = register($username, $password, $db);  
    } else if ($choice === "fblogin") {
        // Facebook login choice. Reads the username and calls the fblogin method.
    	$username = $_POST['username'];
    	$response = fblogin($username, $db);
    } else if ($choice === "setMeasurements") {
        // Set measurements choice. Reads the username. Decodes the json array for the list. Calls
        // the setMeasurements method.
        $username = $_POST['username'];
        $list = json_decode($_POST['list'], true);
        $response = setMeasurements($username, $list, $db);
    } else if ($choice === "setPoints") {
        // Set points choice. Reads the username. Decodes the json array for the list. Calls the 
        // setPoints method.
        $username = $_POST['username'];
        $list = json_decode($_POST['list'], true);
        $response = setPoints($username, $list, $db);
    } else if ($choice === "getMeasurements") {
        // Get measurements choice. Reads the username. Calls the getMeasurements method.
        $username = $_POST['username'];
        $response = getMeasurements($username, $db);
    } else if ($choice === "getFilteredMeasurements") {
        // Get filtered measurements choice. Reads the username, start and stop. Calls the 
        // getFilteredMeasurements method.
        $username = $_POST['username'];
        $start = $_POST['start'];
        $stop = $_POST['stop']; 
        $response = getFilteredMeasurements($username, $start, $stop, $db);
    } else if ($choice === "getPoints") {
        // Get points choice. Reads the username and calls the getPoints method.
        $username = $_POST['username'];
        $response = getPoints($username, $db);
    } else if ($choice === "getFilteredPoints") {
        // Get filtered points choice. Reads the username, start and stop. Calls the 
        // getFilteredPoints method.
        $username = $_POST['username'];
        $start = $_POST['start'];
        $stop = $_POST['stop']; 
        $response = getFilteredPoints($username, $start, $stop, $db);
    } else if ($choice === "setFriend") {
        // Set friend choice. Reads the username and friend to be added to that user. Calls
        // the friendSetExecuter.
        $username = $_POST['username'];
        $friend= $_POST['friend'];
        $response = friendSetExecuter ($username, $friend, $db);
    } else if ($choice === "removeFriend") {
        // Remove friend choice. Reads the username and the friend to be removed from that
        // user. Calls the friendRemoveExecuter.
        $username = $_POST['username'];
        $friend= $_POST['friend'];
        $response = friendRemoveExecuter ($username, $friend, $db);
    } else if ($choice === "setFinalScore") {
        // Set final score choice. Reads the username along with his scores. Reads the speed, 
        // brake, fuel and distraction scores along with the measuredAt value. Calls the 
        // finalMetricsSetExecuter.
        $username = $_POST['username'];
        $speed= $_POST['speed'];
        $brake= $_POST['brake'];
        $fuel= $_POST['fuel'];
        $distraction= $_POST['distraction'];
        $measuredAt= $_POST['measuredAt'];
        $response = finalMetricsSetExecuter ($username, $speed, $brake, $fuel, $distraction, $measuredAt, $db);
    } else if ($choice === "getFinalScore") {
        // Get Final Score choice. Reads the username and calls the getUserFinalMetrics method.
        $username = $_POST['username'];
        $response = getUserFinalMetrics ($db, $username);
    } else if ($choice === "getFilteredFinalScore") {
        //Get filtered final score choice. Reads the username, the start and the stop and calls the 
        // getUserFilteredFinalMetrics method.
        $username = $_POST['username'];
        $start = $_POST['start'];
        $stop = $_POST['stop'];
        $response = getUserFilteredFinalMetrics ($db, $username, $start, $stop);
    } else if ($choice === "getAllScores") {
        //Get all score choice. Calls the getAllScores method.
        $response = getAllScores ($db);
    } else if ($choice === "getAllFilteredScores") {
        //Get all filtered scores choice. Calls the getFilteredScoresAllUsers method.
        $start = $_POST['start'];
        $stop = $_POST['stop'];
        $response = getFilteredScoresAllUsers($db, $start, $stop);
    } else if ($choice === "getFriendsScores") {
        //Get friends scores. Reads the username and call the getFriendsScores method.
        $username = $_POST['username'];
        $response = getFriendsScores ($db, $username);
    } else if ($choice === "getFriendsFilteredScores") {
        //Get friends filtered scores. Reads the username, start and stop
        // and calls the getFriendsFilteredScores method.
        $username = $_POST['username'];
        $start = $_POST['start'];
        $stop = $_POST['stop'];
        $response = getFriendsFilteredScores ($db, $username, $start, $stop);
    } else if ($choice === "getAllFriends") {
        //Get all friends choice. Reads the username and calls the getAllFriends method.
        $username = $_POST['username'];
        $response = getAllFriends ($db, $username);
    } else {
        // The action of choice does not match any of the possible choices.
    	$response["success"] = 0;
    	$response["file"] = "index.php";
    	$response["message"] = "Invalid choice for the index.";
    }
    // Take this away when done controlling and testing. Also remember to use the else correctly.
    die(json_encode($response)); 
} else {
/*
    //Correct way of using the else.
	$response["success"] = 0;
    $response["message"] = "Invalid Choice.";
    die(json_encode($response));
	*/

    //This part is the HTTP front end for testing the Database. On launch of the application this part should
    // be removed and the else should be used correctly.
?>
		<h1>Index</h1> 
        <h4> 
            Test for back end functionality 
            <br />
            Possible actions: login, register, fblogin, getMeasurements, setMeasurements, getFilteredMeasurements
        </h4>
        <h7> Directions: 
            <br /> 
            Action: Needs to always be filled.
            <br /> 
            Username: Used for all functions.
            <br /> 
            The rest of them are properly marked.

            </h7>
            <br />
		<form action="index.php" method="post"> 
		    action:<br /> 
		    <input type="text" name="action" placeholder="action" /> 
            <h4>username is used for all purposes</h4>
		    username:<br /> 
		    <input type="text" name="username" placeholder="username" value="" /> 
                    <br /> 
		    friend:<br /> 
		    <input type="text" name="friend" placeholder="friend" value="" /> 
            <h4>password is used for login and register only</h4>
		    password:<br /> 
		    <input type="password" name="password" placeholder="password" value="" /> 
		    

            <h2>Set</h2>
            speed:<br /> 
            <input type="number" name="speed" placeholder="speed" value="" /> 
            <br />
            brake:<br /> 
            <input type="number" name="brake" placeholder="brake" value="" /> 
            <br />
            distraction:<br /> 
            <input type="number" name="distraction" placeholder="distraction" value="" /> 
            <br />
            fuel:<br /> 
            <input type="number" name="fuel" placeholder="fuel" value="" /> 
            <br />
            measuredAt:<br /> 
            <input type="number" name="measuredAt" placeholder="measuredAt" value="" /> 
            <br />

            <h2> Get Filtered </h2>
            start:<br />
            <input type="number" name="start" placeholder="start" /> 
            <br />
            stop:<br /> 
            <input type="number" name="stop" placeholder="stop" /> 
            <br />

            <h2> Setter Tester </h2>
            JSONList:<br /> 
            <input type="text" name="list" placeholder="list" value="" /> 
            <h4>JSONList is used to test the setMeasurements and SetPoints only.</h4>
            <br />

            <input type="submit" value="Submit Action" /> 
		</form> 
		<a href="index.php"></a>
	<?php
}

?>