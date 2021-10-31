package com.example.project3softwaremeth;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.StringTokenizer;
/**
 * @author Kevan Patel
 * @author Manav Patel
 */

/**
 * UI Class that manages all of the album collection
 */
public class TuitionManager {
    private static final int invalidCredits = -1;
    private static final double MAX_AID = 10000;
    private static final int minimumCredits= 3;
    private static final int maximumCredits= 24;
    private static final int negativeCredits=0;
    private static final int minimumCreditsInternational=12;
    private static final int invalidLength = -1;
    private static final int invalidCreditOrPaymentLength =3;
    private static final int invalidOrMissing=4;
    private static final int validData =5;
    /**
     * function that reads user input using while loop, terminates if Q is entered
     */
    public void run() {

        Scanner inputScanner = new Scanner(System.in); // Create a Scanner object
        System.out.println("Tuition Manager starts running.");
        Roster roster = new Roster(); // Create the roster
        while (evaluateLine(roster, inputScanner.nextLine())) ;
        System.out.println("Tuition Manager terminated.");

    }

    /**
     * We take in the users string input and return an enum major
     * @param major is the string given to users
     * @return the major as enum or null if not found
     */
    private Major getMajor(String major) {
        switch (major.toUpperCase()) {
            case "CS":
                return Major.CS;
            case "IT":
                return Major.IT;
            case "BA":
                return Major.BA;
            case "EE":
                return Major.EE;
            case "ME":
                return Major.ME;
            default:
                System.out.println("'" + major + "' is not a valid major.");
                return null;
        }

    }

    /**
     * check if amount of credits given is valid
     * @param credits is given via user input and tested to see if valid
     * @return amount of credits if valid otherwise mark as inValid Credits
     */
    private int validCredits(int credits) {
        if (credits < negativeCredits) {
            System.out.println("Credit hours can not be negative.");
            return invalidCredits;
        }
        if (credits < minimumCredits) {
            System.out.println("Minimum credit hours is 3.");
            return invalidCredits;
        }
        if (credits > maximumCredits) {
            System.out.println("Credit hours exceed the maximum 24.");
            return invalidCredits;
        } else {
            return credits;
        }
    }
    /**
     * check if amount of credits given is valid for international students
     * @param credits is given via user input and tested to see if valid
     * @return amount of credits if valid otherwise mark as inValid Credits
     */
    private int validCreditsInternational(int credits) {
        if (credits < negativeCredits) { // Credits can not be negative
            System.out.println("Credit hours can not be negative.");
            return invalidCredits;
        }
        if (credits < minimumCredits) { //3 is Min Credit
            System.out.println("Minimum credit hours is 3.");
            return invalidCredits;
        }
        if (credits > maximumCredits) { //24 is Max amount of credits
            System.out.println("Credit hours exceed the maximum 24.");
            return invalidCredits;
        }
        if (credits < minimumCreditsInternational) { //International students must have at least 12 credits
            System.out.println("International students must enroll at least 12 credits.");
            return invalidCredits;
        } else {
            return credits;
        }
    }

    /**
     * check if inputsize is Valid or AR and AN commands
     * @param inputSizes size of input of user
     * @return inputSize if valid and invalidlength if not
     */
    private int validInputARAN(int inputSizes){
        if (inputSizes == invalidCreditOrPaymentLength) {
            System.out.println("Credit hours missing.");
            return invalidLength;
        }
        if (inputSizes != invalidOrMissing) {
            System.out.println("Missing data in the command line.");
            return invalidLength;
        }
        else{
        return inputSizes;
        }
    }
    /**
     * Method to take user input and run code accordingly
     * @param roster that is to be edited and displayed based on user input
     * @param line user input that is to be taken and ran
     * @return true to continue running and false to end tuitionManager
     */
    private boolean evaluateLine(Roster roster, String line) {
        StringTokenizer tokenString = new StringTokenizer(line, ","); // Tokenize the user input
        int tokenStringLength = tokenString.countTokens();
        String[] input = new String[10]; // Create a String array to store the input in
        input[0]="";
        for (int i = 0; i < tokenStringLength; i++) { // Trim and store the user input
            input[i] = tokenString.nextToken().trim();
        }
        if (input[0].equals("Q")) { // If the user inputs Q then we end the program
            return false;
        }
        if (input[0].equals("AT")) { // If the user inputs A then we add
            // check if valid parameters
            if (tokenStringLength != validData) {
                System.out.println("Missing data in the command line.");
                return true;
            }
            Major major = getMajor(input[2]);
            if (major == null) {
                return true;
            }
            State state;
            switch (input[4].toUpperCase()) {
                case "NY":
                    state = State.NY;
                    break;
                case "CT":
                    state = State.CT;
                    break;
                default:
                    System.out.println("Not part of the tri-state area.");
                    return true;
            }
            int credits = validCredits(Integer.parseInt(input[3]));
            if (credits == invalidCredits) {
                return true;
            }

            TriState triState = new TriState(input[1], major, credits, state);
            boolean added = roster.add(triState);// Add student to roster
            // We display what happened depending on if it was successfully added or not
            System.out.println(added ? "Student added." : "Student is already in roster.");
            return true;
        }
        if (input[0].equals("AR")) {
            int lengthCheck=validInputARAN(tokenStringLength);
            if (lengthCheck==invalidLength){
                return true;
            }
            Major major = getMajor(input[2]);
            if (major == null) {
                return true;
            }
            int credits;
            try {
                credits = validCredits(Integer.parseInt(input[3]));
            } catch (NumberFormatException e) {
                System.out.println("Invalid Credit Hours.");
                return true;
            }
            if (credits == invalidCredits) {
                return true;
            }
            Resident resident = new Resident(input[1], major, credits);
            boolean added = roster.add(resident);// Add student to roster
            // We display what happened depending on if it was successfully added or not
            System.out.println(added ? "Student added." : "Student is already in roster.");
            return true;

        }
        if (input[0].equals("AN")) {
            int lengthCheck=validInputARAN(tokenStringLength);
            if (lengthCheck==invalidLength){
                return true;
            }
            Major major = getMajor(input[2]);
            if (major == null) {
                return true;
            }
            int credits;
            try {
                credits = validCredits(Integer.parseInt(input[3]));
            } catch (NumberFormatException e) {
                System.out.println("Invalid Credit Hours.");
                return true;
            }
            if (credits == invalidCredits) {
                return true;
            }
            NonResident nonResident = new NonResident(input[1], major, credits);
            boolean added = roster.add(nonResident);// Add student to roster
            // We display what happened depending on if it was successfully added or not
            System.out.println(added ? "Student added." : "Student is already in roster.");
            return true;
        }
        if (input[0].equals("AI")) {
            if (tokenStringLength == invalidCreditOrPaymentLength) {
                System.out.println("Credit hours missing.");
                return true;
            }
            if (tokenStringLength != validData) {
                System.out.println("Missing data in the command line.");
                return true;
            }
            Major major = getMajor(input[2]);
            if (major == null) {
                return true;
            }
            boolean abroad;
            if (input[4].equals("true")) {
                abroad = true;
            } else if (input[4].equals("false")) {
                abroad = false;
            } else {
                System.out.println("Did not specify true or false");
                return true;
            }
            int credits;
            try {
                credits = validCreditsInternational(Integer.parseInt(input[3]));
            } catch (NumberFormatException e) {
                System.out.println("Invalid Credit Hours.");
                return true;
            }
            if (credits == invalidCredits) {
                return true;
            }

            International international = new International(input[1], major, credits, abroad);
            boolean added = roster.add(international);// Add album to the collection
            // We display what happened depending on if it was successfully added or not
            System.out.println(added ? "Student added." : "Student is already in roster.");
            return true;
        }
        if (input[0].equals("R")) {
            Major major = getMajor(input[2]);
            if (major == null) {
                return true;
            }
            Student student = new Student(input[1], major, 3, (float) 0, (float) 0, new Date(),true);
            boolean removed = roster.remove(student); //Remove student and show what happened based on results
            System.out.println(removed ? "Student removed from the roster." : "Student is not in the roster.");
            return true;
        }
        if (input[0].equals("P")) {
            roster.print();
            return true;
        }
        if (input[0].equals("PT")){
            roster.printByPaymentDate();
            return true;
        }
        if (input[0].equals("PN")){
            roster.printByName();
            return true;
        }
        if (input[0].equals("C")) {
            for(int i=0;i<roster.getSize();i++){
                if(roster.getStudent(i).getLastPaymentDate()==null && roster.getStudent(i).getTuitionDue()==0 ){
                roster.getStudent(i).tuitionDue();}
            }
            System.out.println("Calculation completed.");
            return true;
        }
        if (input[0].equals("S")) {
           // check if  student exists
           Major major = getMajor(input[2]);
           if (major == null) {
               return true;
           }
           Student tempStudent = new Student(input[1], major, 3, (float) 0, (float) 0, new Date(),false);
           int index = roster.find(tempStudent);
           if (index < 0) { //this means that a student does not exist
            System.out.println("Couldn't find the international student.");
               return true;
           }
           tempStudent = roster.getStudent(index);
         
           if (!(tempStudent instanceof International)) {
            System.out.println("Couldn't find the international student.");
            return true;
        }
        ((International) tempStudent).setAbroad();
        System.out.println("Tuition updated.");
        return true;
        }
        if (input[0].equals("F")) {
            // check if  student exists
            if (tokenStringLength != invalidOrMissing) {
                System.out.println("Missing the amount.");
                return true;
            }
            Major major = getMajor(input[2]);
            if (major == null) {
                return true;
            }
            Student tempStudent = new Student(input[1], major, 3, (float) 0, (float) 0, new Date(),true);
            int index = roster.find(tempStudent);
            if (index < 0) { //this means that a student does not exist
                System.out.println("Student not in the roster.");
                return true;
            }

            tempStudent = roster.getStudent(index);

            Double financialAid = Double.parseDouble(input[3]); // check is the finanical aid amount is correct
            if (financialAid <= 0 || financialAid > MAX_AID) {
                System.out.println("Invalid amount.");
                return true;
            }

            if (tempStudent.getCredits() < Student.minCreditsForFulltime) {
                System.out.println("Parttime student doesn't qualify for the award.");
                return true;
            }

            if (!(tempStudent instanceof Resident)) {
                System.out.println("Not a resident student.");
                return true;
            }

            if (!(((Resident) tempStudent).reciveFinancialAid(financialAid))) {
                System.out.println("Awarded once already.");
                return true;
            }
            System.out.println("Tuition updated.");

            return true;
        }
        if (input[0].equals("T")) {
            if (tokenStringLength == invalidCreditOrPaymentLength) {
                System.out.println("Payment amount missing.");
                return true;
            }
            double payment = Double.parseDouble(input[3]);
            if (payment <= 0) {
                System.out.println("Invalid amount.");
                return true;
            }
            Date inputDate = new Date(input[4] + "");
            if (inputDate.isValid()) {
                Major major = getMajor(input[2]);
                if (major == null) {
                    return true;
                }
                Student student = new Student(input[1], major, 0, (float) 0, (float) payment, inputDate,true);
                Student find = roster.findStudent(student);
                DecimalFormat decimalFormat= new DecimalFormat();
                decimalFormat.setMaximumFractionDigits(2);
                if (find.getTuitionDue() < payment) {
                    System.out.println("Amount is greater than amount due.");
                } else {
                    find.setTuitionDue(find.getTuitionDue() - payment);
                    find.setTotalPayment((float) (find.getTotalPayment() + payment));
                    find.setLastPaymentDate(inputDate);
                    System.out.println("Payment applied.");
                }
                return true;
            } else {
                System.out.println("Payment date invalid.");
                return true;
            }
        }
        if (input[0].equals("")){
            System.out.println("");
            return true;
        }
        System.out.println("Command '" + input[0] + "' not supported!");
        return true;
    }
}
