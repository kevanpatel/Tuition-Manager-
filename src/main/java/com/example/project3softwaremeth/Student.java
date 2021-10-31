package com.example.project3softwaremeth;
/**
 * @author Kevan Patel
 * @author Manav Patel
 */

/**
 * Class that defines all things every type of student has in common
 */
public class Student {
    
    public static final int minCreditsForFulltime = 12;
    public static final int universityFee = 3268;
    private Profile profile;
    private int credits;
    private double tuitionDue;
    private float totalPayment;
    private Date lastPaymentDate;
    private boolean isResident;

    /**
     * getter method for credits
     * @return number of credits
     */
    public int getCredits() {
        return credits;
    }

    /**
     * setter method for credits
     * @param credits credits to be set
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * getter method for tuitionDue
     * @return the tuitionDue
     */
    public double getTuitionDue() {
        return tuitionDue;
    }

    /**
     * setter method for TuitionDue
     * @param tuitionDue to be set
     */
    public void setTuitionDue(Double tuitionDue) {
        this.tuitionDue = tuitionDue;
    }

    /**
     * getter method for TotalPayment
     * @return the totalpayment
     */
    public float getTotalPayment() {
        return totalPayment;
    }

    /**
     * setter method for TotalPayment
     * @param totalPayment to be set
     */
    public void setTotalPayment(float totalPayment) {
        this.totalPayment = totalPayment;
    }

    /**
     * getter method for lastpaymentDate
     * @return value for lastpaymentDate
     */
    public Date getLastPaymentDate() {
        return lastPaymentDate;
    }

    /**
     * setter for lastpaymentdate
     * @param lastPaymentDate to be set to
     */
    public void setLastPaymentDate(Date lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    /**
     * method to check if student is fullTime
     * @param credits they are taking
     * @return true if they are fulltime,false if not
     */
    public boolean isFulltime(int credits){
        if( credits>minCreditsForFulltime){
            return true;
        }
        return false;
    }

    /**
     * getter method for profile
     * @return profile
     */
    public Profile getProfile(){
        return profile;
    }

    /**
     * Constructor for student
     * @param name of student
     * @param major of student
     * @param credits of student
     * @param tuitionDue of student
     * @param totalPayment of student
     * @param lastPaymentDate of student
     * @param isResident status of student
     */
    public Student(String name, Major major, int credits, float tuitionDue,
                   float totalPayment, Date lastPaymentDate,boolean isResident){
        this.profile=new Profile(name,major);
        this.credits=credits;
        this.tuitionDue=tuitionDue;
        this.totalPayment=totalPayment;
        this.lastPaymentDate=lastPaymentDate;
        this.isResident=isResident;
    }

    /**
     * Do Nothing method to be overwritten in all child classes
     */
    public void tuitionDue(){
    }

    /**
     * toString method that all types of students have in common
     * @return String form of information given
     */
    @Override
    public String toString(){
        return profile+ ":"+ credits+ " credit hours:" + "tuition due:" + String.format("%,.2f",this.getTuitionDue()) +
        ":total payment:" + String.format("%,.2f",totalPayment) + ":last payment date: " + (lastPaymentDate==null ? "--/--/--":lastPaymentDate)
                + ":" + (isResident ? "resident":"non-resident");
    }

    /**
     * method to check if two students are the same
     * @param obj to be compared
     * @return true if same, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Student)) {
            return false;
        }
        return (((Student) obj).profile.equals(this.profile));
    }
}