package com.example.project3softwaremeth;
/**
 * @author Kevan Patel
 * @author Manav Patel
 */

/**
 * Nonresident student class whose super class is Student
 */
public class NonResident extends Student{

    public static final int nonresidentTution = 29737;
    public static final int nonresidentTutionPartTime = 966;

    /**
     * Constructor for NonResident class
     * @param name of nonresident student
     * @param major of nonresident student
     * @param credits of nonresident student
     */
    public NonResident(String name, Major major, int credits){
        super(name,major,credits,0,0,null,false);
        
    }

    /**
     * calculates tuition due of NonResident student
     */
    @Override
    public void tuitionDue(){
        
            if(this.getCredits()>16){
                this.setTuitionDue(Double.valueOf(nonresidentTution+universityFee + (this.getCredits()-16)*nonresidentTutionPartTime));
            }else if(this.getCredits()<12){ 
            this.setTuitionDue(this.getCredits()*nonresidentTutionPartTime+(universityFee*0.8)); //get the partime tution
            }
            else{
            this.setTuitionDue(Double.valueOf(universityFee+nonresidentTution));    
            }
    }
}
