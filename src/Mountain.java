/********************************************************************************
 ∗ @file: Mountain.java
 ∗ @description: Creates an object of type Mountain that has the name, elevations, mountain range and locations of
 *  mountains around the world
 *
 * @dataset_source: Data obtained from kaggle.com, titled "List of mountains in the world".
 * Data retrieved in September 2024.
 *
 *  @dataset_modifications:
 *     1. Added commas into a number of entries that were missing them in key areas
 *
∗ @author: Benton Phillips
∗ @date: November 15, 2024
 *
 *********∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗∗*/
public class Mountain implements Comparable<Mountain> {
    private String name;
    private Integer MElevation;
    private Integer FtElevation;
    private String range;
    private String location;

    //Constructor that sets the name of the mountain
    Mountain(String name){
        this.name = name;
    }

    //Overloaded constructor that sets all mountain variables
    Mountain(String name, Integer MElevation, Integer FtElevation, String range, String location){
        this.name = name;
        this.MElevation = MElevation;
        this.FtElevation = FtElevation;
        this.range = range;
        this.location = location;
    }

    //Sets the name of the mountain
    public void setName(String name) {
        this.name = name;
    }

    //Gets the name of the mountain
    public String getName() {
        return name;
    }

    //Sets the elevation of the mountain in meters
    public void setMElevation(Integer MElevation){
        this.MElevation = MElevation;
    }

    //Gets the elevation of the mountain in meters
    public Integer getMElevation() {
        return MElevation;
    }

    //Sets the elevation of the mountain in feet
    public void setFtElevation(Integer FtElevation){
        this.FtElevation = FtElevation;
    }

    //Gets the elevation of the mountain in feet
    public Integer getFtElevation() {
        return FtElevation;
    }

    //Sets the mountain range of the mountain
    public void setRange(String range){
        this.range = range;
    }

    //Gets the mountain range of the mountain
    public String getRange() {
        return range;
    }

    //Sets the location of the mountain
    public void setLocation(String location){
        this.location = location;
    }

    //Gets the location of the mountain
    public String getLocation() {
        return location;
    }

    //Puts all mountain information into String format in a readable manner
    @Override
    public String toString(){
        return String.format("Mountain: %s, Elevation(meters): %d, Elevation(feet): %d, Mountain Range: %s, Location: %s", name, MElevation, FtElevation, range, location);
    }

    //Checks to see if two mountain objects are equal based on if they have the exact same elevation
    public boolean equals(Mountain mountain){
        if (MElevation.equals(mountain.getMElevation())){
            if (FtElevation.equals(mountain.getFtElevation())){
                return true;
            }
        }
        return false;
    }

    //Compares mountain objects based on elevation
    @Override
    public int compareTo(Mountain mountain){
        if (this.MElevation.equals(mountain.getMElevation())){
            return this.FtElevation.compareTo(mountain.getFtElevation());
        }
        return this.MElevation.compareTo(mountain.getMElevation());
    }

}


