package com.example.project3softwaremeth;
/**
 * @author Kevan Patel
 * @author Manav Patel
 */

/**
 *Class to define a roster that holds student objects
 */
public class Roster {
    private Student[] roster;
    private int size; //keep track of the number of students in the roster
    static int NOT_FOUND= -1;

    /**
     * constructor for a roster
     */
    public Roster() {
        this.roster = new Student[4];
        size=0;
    }

    /**
     * Find student index inside array if it exists
     * @param student that we are searching for
     * @return integer that is te index of the album, or NOT_FOUND
     */
    public int find(Student student) {
        for (int i = 0; i < size; i++) {
            if (roster[i].equals(student)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * method to return student at index
     * @param index of student
     * @return the student located at index
     */
    public Student getStudent(int index) {
        return roster[index];
    }
    /**
     * Grows size of roster by 4
     */
    private void grow() {
        Student[] newRoster = new Student[roster.length + 4];
        for (int i = 0; i < size; i++) {
            newRoster[i] = roster[i];
        }
        roster = newRoster;
    }

    /**
     * add method to add student into roster
     * @param student to be added
     * @return true if added,false if unsuccessful
     */
    public boolean add(Student student) {
        if (find(student) == NOT_FOUND) {
            if (size >= roster.length) { // if the roster is full then we grow it
                this.grow();
            }
            roster[size] = student; // set the roster to the count of albums we have thus far
            size++;
            return true;
        }
        return false;
    }

    /**
     * remove method to remove student
     * @param student to be removed
     * @return true if added, false if unsuccessful
     */
    public boolean remove(Student student) {
        int index = find(student);
        if (index >= 0) {
            for (int i = index; i < size - 1; i++) {
                roster[i] = roster[i + 1]; // shift the array down by 1
            }
            size--; // reduce the size of the array by 1
            return true; // if the student is successfully removed
        }
        return false; // if student is not found and therefore cannot be removed.
    }

    /**
     * method to return size of roster
     * @return size of roster as integer
     */
    public int getSize() {
        return size;
    }

    /**
     * print method in any order
     */
    public String print(){
        if (size == 0) {
            return "Student roster is empty!\n";
        } else {
            String output="";
            output+="* list of students in the roster **\n";
            for (int i = 0; i < size; i++) { // loop through number of roster and print with toString format
                output+=roster[i]+"\n";
            }
            output+="* end of roster **";
            return output;
        } // display the list without specifying the order

    }

    /**
     * method to find Student
     * @param student that we are looking for
     * @return the index they are on if they exist
     */
    public Student findStudent(Student student){
        int exist = find(student);
        if(exist != NOT_FOUND){
            return roster[exist];
        }
        return null;
    }

    /**
     * method to print alphabetically by first name
     */
    public String printByName() {
        boolean isEmpty=false;
        // create temp album array to sort
        Student[] tempStudent = new Student[size];
        for (int i = 0; i < size; i++) {
                tempStudent[i] = roster[i];
                }
        if(roster[0]==null){ //TRY TO FIX
            isEmpty=true;
        }
        if (isEmpty) {
            return "Student roster is empty!";
        } else {
            String output= "";
            output+= "* list of students ordered by name **\n";
            // sort the tempalbumn array using selction sort
            for (int i = 0; i < tempStudent.length- 1; i++) {
                int min = i;
                for (int q = i + 1; q < tempStudent.length; q++) {
                    if (tempStudent[i] != null) {
                        if ((tempStudent[q]!=null && (tempStudent[q].getProfile().getName().compareTo(tempStudent[min].getProfile().getName())) < 0 )) {
                            min = q;
                        }
                    }
                }
                //switch the smalled index
                Student temp = tempStudent[min];
                tempStudent[min] = tempStudent[i];
                tempStudent[i] = temp;
            }
            //print the sorted array
            for (int i =0;i<tempStudent.length;i++) {
                if(tempStudent[i]!=null){
                    output+=tempStudent[i].toString()+"\n";
                }
            }
            output+="* end of roster **\n";
            return output;
    }

    }

    /**
     * method to print by last payment date chronologically
     */
    public String printByPaymentDate() {
        boolean isEmpty=false;
        // create temp album array to sort
        Student[] tempStudent = new Student[size];
        for (int i = 0; i < size; i++) {
            if(roster[i].getLastPaymentDate()!=null){
                tempStudent[i] = roster[i];
            }
        }

        if (roster[0]==null && roster[1]==null &&roster[2]==null) {
            return "Student roster is empty!";
        } else {
            String output= "";
            output+="* list of students made payments ordered by payment date **\n";
            // sort the tempstudent array using selction sort
            for (int i = 0; i < tempStudent.length- 1; i++) {
                int min = i;
                for (int q = i + 1; q < tempStudent.length; q++) {
                    if (tempStudent[i] != null && tempStudent[i].getLastPaymentDate()!=null) {
                        if ((tempStudent[q]!=null && (tempStudent[q].getLastPaymentDate().compareTo(tempStudent[min].getLastPaymentDate())) < 0 )) {
                            min = q;
                        }
                    }
                }
                //switch the smalled index
                Student temp = tempStudent[min];
                tempStudent[min] = tempStudent[i];
                tempStudent[i] = temp;
            }
            //print the sorted array
            for (int i =0;i<tempStudent.length;i++) {
                if(tempStudent[i]!=null && tempStudent[i].getLastPaymentDate()!=null){
                    output+=tempStudent[i].toString()+"\n";
                }
            }
            output+="* end of roster **\n";
            return output;
    }
    
}
}

