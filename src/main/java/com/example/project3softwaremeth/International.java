package com.example.project3softwaremeth;
/**
 * @author Kevan Patel
 * @author Manav Patel
 */

/**
 * International student class that extends NonResident student class
 */
public class International extends NonResident {
    
    private static final int internationalFees = 2650;
    private static final int maxCreditsStudyAbroad = 12;

    private boolean isAbroad;

    /**
     * constructor for International Student class which calls its super
     * @param name of International Student
     * @param major of International Student
     * @param credits of International Student
     * @param isAbroad of International Student
     */
    public International(String name, Major major, int credits,boolean isAbroad){
        super(name,major,credits);
        this.isAbroad=isAbroad;
    }

    /**
     *method to set International Student abroad
     */
    public void setAbroad(){
        if(this.getCredits()>maxCreditsStudyAbroad){
            this.setCredits(maxCreditsStudyAbroad);
        }
      setLastPaymentDate(null);
      setTotalPayment(0);
      setTuitionDue(0.0);
     isAbroad=true;
     tuitionDue();
    }

    /**
     * Calculates tuitiondue for International Student checking if they are abroad or not and based on credits
     */
    @Override
    public void tuitionDue(){
       if(isAbroad){
        this.setTuitionDue(Double.valueOf(universityFee+internationalFees));
       }
       else{
        if(this.getCredits()>16){
            this.setTuitionDue(Double.valueOf(nonresidentTution+universityFee +internationalFees+ (this.getCredits()-16)*nonresidentTutionPartTime));
        }else if(this.getCredits()<minCreditsForFulltime){
        this.setTuitionDue(this.getCredits()*nonresidentTutionPartTime+(universityFee*0.8)); //get the partime tution
        }
        else{
        this.setTuitionDue(Double.valueOf(universityFee+nonresidentTution+internationalFees));    
        }
       }
    }

    /**
     * toString method that takes from super class and adds necesarry extensions based on International
     * @return string of International student
     */
    @Override
    public String toString(){
        if(isAbroad){
            return super.toString() + ":international" +":study abroad";

        }else{
        return super.toString() + ":international";
        }
    }
}
