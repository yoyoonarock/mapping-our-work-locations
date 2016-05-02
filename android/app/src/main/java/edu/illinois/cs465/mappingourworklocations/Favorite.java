package edu.illinois.cs465.mappingourworklocations;

/**
 * Created by Jason on 5/2/2016.
 */
public class Favorite {
    private String building;
    private String roomnumber;
    private String note;

    public Favorite(){
        super();
    }

    public Favorite(String building, String roomnumber, String note) {
        super();
        this.building = building;
        this.roomnumber = roomnumber;
        this.note = note;
    }

    @Override
    public String toString() {
        return this.building + " " + this.roomnumber + "\n" + this.note;
    }
}
