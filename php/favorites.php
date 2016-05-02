<?php
	require "init.php";

	// Output JSON response.
    $response = array();

    $username = 'jpao2'

    $sql = "SELECT r.building AS building, r.roomnumber AS roomnumber, r.note AS note FROM Rooms r INNER JOIN Favorites f ON f.roomid = r.id INNER JOIN Login l ON l.id = f.userid WHERE l.username = '$username'";
    $result = mysql_query($sql);
     
    if (mysql_num_rows($result) > 0)
    {
        // At least 1 favorite exists.

        $response["favorites"] = array();
     
        while ($row = mysql_fetch_array($result))
        {
            $favorite = array();
            $favorite["building"] = $row["building"];
            $favorite["roomnumber"] = $row["roomnumber"];
            $favorite["note"] = $row["note"];
     
            // Push single favorite into final response array.
            array_push($response["favorites"], $favorite);
        }
        // success
        $response["success"] = 1;

        echo json_encode($response);
    }
    else
    {
        // No events were found.

        $response["success"] = 0;
        $response["message"] = "No favorites found.";

        echo json_encode($response);
    }
?>