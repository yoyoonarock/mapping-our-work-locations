package edu.illinois.cs465.mappingourworklocations;

/**
 * Created by Jason on 5/2/2016.
 */
public class Room
{
    String building;
    String roomNumber;
    String note;
    String favorite;
    String availability;

    public Room (String givenBuilding, String givenRoomNumber, String givenNote, String givenFavorite, String givenAvailability)
    {
        building = givenBuilding;
        roomNumber = givenRoomNumber;
        favorite = givenFavorite;
        availability = givenAvailability;

        if (givenNote.equals("null"))
        {
            note = "Note: ";
        }
        else
        {
            note = "Note: " + givenNote;
        }
    }
}
