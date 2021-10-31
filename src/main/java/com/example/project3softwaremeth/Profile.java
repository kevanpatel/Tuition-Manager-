package com.example.project3softwaremeth;
/**
 * @author Kevan Patel
 * @author Manav Patel
 */

/**
 * Profile class that helps to uniquely create and identify students
 */
public class Profile {
    private String name;
    private Major major;

    /**
     * Constructor for Profile
     * @param name of student in profile
     * @param major of student in profile
     */
    public Profile(String name, Major major) {
        this.name = name;
        this.major = major;
    }

    /**
     * getter method to get name of student
     * @return name we need
     */
    public String getName(){
        return name;
    }

    /**
     * equals method override to compare two profiles
     * @param obj to be compared
     * @return false if not the same, true if they are
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Profile)) {
            return false;
        }
        return (((Profile) obj).name.equals(this.name) && ((Profile) obj).major.equals(this.major));

    }

    /**
     * toString method to print name and major
     * @return string form of name and major
     */
    @Override
    public String toString() {
        return name + ":" + major;
    }
}