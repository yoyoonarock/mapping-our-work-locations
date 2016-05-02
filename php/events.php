<?php
    require "init.php";

    // Output JSON response.
    $response = array();

    $sql = "SELECT r.building AS building, r.roomnumber AS roomnumber, e.eventdate AS eventdate, e.starttime AS starttime, e.endtime AS endtime, e.availability AS availability, e.description AS description FROM Events e INNER JOIN Rooms r ON e.roomid=r.id;";
    $result = mysql_query($sql);
     
    if (mysql_num_rows($result) > 0)
    {
        // At least 1 event exists.

        $response["events"] = array();
     
        while ($row = mysql_fetch_array($result))
        {
            $event = array();
            $event["building"] = $row["building"];
            $event["roomnumber"] = $row["roomnumber"];
            $event["eventdate"] = $row["eventdate"];
            $event["starttime"] = $row["starttime"];
            $event["endtime"] = $row["endtime"];
            $event["availability"] = $row["availability"];
            $event["description"] = $row["description"];
     
            // Push single event into final response array.
            array_push($response["events"], $event);
        }
        // success
        $response["success"] = 1;

        echo json_encode($response);
    }
    else
    {
        // No events were found.

        $response["success"] = 0;
        $response["message"] = "No events found.";

        echo json_encode($response);
    }
?>