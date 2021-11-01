package com.example.project3softwaremeth;
/**
 * @author Kevan Patel
 * @author Manav Patel
 */

/**
 * Resident student class that extends Student
 */
public class Resident extends Student{

    private static final int residentTution = 12536;
    private static final int residentTutionPartTime = 404;
    private double financialAid =0;
    private boolean gotAid = false;

    /**
     * Resident constructor which calls on its super
     * @param name of Resident student
     * @param major of Resident student
     * @param credits of Resident Student
     */
    public Resident(String name, Major major, int credits) {
        super(name,major,credits,0,0,null,true);
    }

    /**
     * boolean to mark if financial aid was given or not
     * @param aid given to Resident student
     * @return false if already given aid, true if aid is given successfully
     */
    public boolean reciveFinancialAid(double aid){
        if(aid==0){
            return  true;
        }

        this.setTuitionDue(this.getTuitionDue()+this.financialAid);
        financialAid = aid;
        this.setTuitionDue(this.getTuitionDue()-financialAid);
        gotAid = true;
        return true;
    }

    /**
     * class to calculate tuition due for a resident student
     */
    @Override
    public void tuitionDue(){
        if(this.isFulltime(this.getCredits())){
            if(this.getCredits()>16){
                this.setTuitionDue(Double.valueOf(residentTution+universityFee + (this.getCredits()-16)*residentTutionPartTime));
            }else{
            this.setTuitionDue(Double.valueOf(residentTution+universityFee));} //get the tution and turn it into a double
        }
        else{
            this.setTuitionDue(this.getCredits()*residentTutionPartTime+(universityFee*0.8)); //get the partime tution
        }
    }

    /**
     * toString method that continues from student class and adds toString specific to Residents
     * @return
     */
    @Override
    public String toString(){
        if(this.gotAid){
            return super.toString() + ":financial aid $" +String.format("%,.2f",financialAid) ;

        }else{
        return super.toString();
        }
    }
}