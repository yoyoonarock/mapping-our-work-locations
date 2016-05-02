package edu.illinois.cs465.mappingourworklocations;

/**
 * Created by Jason on 5/2/2016.
 */
public class Event
{
    String eventName;
    String building;
    String roomNumber;
    String eventDate;
    String startTime;
    String endTime;
    String availability;
    String description;

    public Event (String givenName, String givenBuilding, String givenRoomNumber, String givenDate, String givenStartTime, String givenEndTime, String givenAvailability, String givenDescription)
    {
        eventName = givenName;
        building = givenBuilding;
        roomNumber = givenRoomNumber;
        eventDate = givenDate;
        startTime = givenStartTime;
        endTime = givenEndTime;
        availability = givenAvailability;

        if (givenDescription.equals("null"))
        {
            description = "Description: None.";
        }
        else
        {
            description = "Description: " + givenDescription;
        }
    }
}
