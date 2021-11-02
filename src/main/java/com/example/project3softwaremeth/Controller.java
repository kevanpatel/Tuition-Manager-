package com.example.project3softwaremeth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.text.DecimalFormat;

/**
 * @author Kevan Patel
 * @author Manav Patel
 */

/**
 * Controller class that holds commands for GUI
 */
public class Controller {
    Roster roster= new Roster();

    private static final double MAX_AID = 10000;

    /**
     * ID for International togglebutton
     */
    @FXML
    private ToggleButton International;
    /**
     * ID for status togglebutton
     */
    @FXML
    private ToggleGroup isResident;

    /**
     *ID for MajorStudent Group
     */
    @FXML
    private ToggleGroup MajorStudent;

    /**
     * ID for Major1 Group for payments
     */
    @FXML
    private ToggleGroup Major1;

    /**
     *ID for NY and CT buttons
     */
    @FXML
    private ToggleGroup NYCT;
    /**
     * ID for NonResOpts held in Vbox
     */
    @FXML
    private VBox NonResOpts;

    /**
     *ID for Tristate togglebutton
     */
    @FXML
    private ToggleButton Tristate;

    /**
     *ID for paymentDateID
     */
    @FXML
    private DatePicker paymentDateID;

    /**
     *ID for Tristate States held in HBox
     */
    @FXML
    private HBox TristateStates;

    /**
     *ID for studyAbroad check
     */
    @FXML
    private CheckBox studyAbroad;

    /**
     *ID for area where we enter credit hours
     */
    @FXML
    private TextField creditHours;

    /**
     *ID for where messages get displayed to User
     */
    @FXML
    private TextArea messageArea;

    /**
     *ID for where tuition will be displayed if wanting to calculate for a single student
     */
    @FXML
    private TextField tutionText;

    /**
     *ID for Name of student who is paying
     */
    @FXML
    private TextField paymentName;

    /**
     *ID for amount being payed
     */
    @FXML
    private TextField paymentAmount;

    /**
     *ID for financial aid being given
     */
    @FXML
    private TextField financialAidAmountText;

    /**
     *ID for student being added
     */
    @FXML
    private TextField Name;

    /**
     *ID for nonresident radio button
     */
    @FXML
    private RadioButton nonResidentButton;

    /**
     *ID for resident radio button
     */
    @FXML
    private RadioButton residentButton;

    /**
     *id for CT button
     */
    @FXML
    private RadioButton buttonCT;

    /**
     *id for NY button
     */
    @FXML
    private RadioButton buttonNY;

    /**
     * method to pay tuition
     * @param event in which it will activate
     */
    @FXML
    void payTution(ActionEvent event){
        if (paymentName.getText()==null || paymentName.getText().isEmpty()){
            messageArea.appendText("No Name Entered\n");
            return;
        }
        if (Major1.getSelectedToggle()==null){
            messageArea.appendText("No major selected\n");
            return;
        }

        if (paymentAmount.getText()==null || paymentAmount.getText().isEmpty()){
            messageArea.appendText("No financial Entered\n");
            return;
        }


        double payment = Double.parseDouble(paymentAmount.getText());
        if(payment<=0){
            messageArea.appendText("Invalid amount\n");
            return;
        }
        if(paymentDateID.getValue()==null){
            messageArea.appendText("No date selected\n");
            return;

        }

        String inputDate = paymentDateID.getValue().toString();
        Date date = new Date(inputDate,true);

        if(date.isValid()){

            RadioButton selected=(RadioButton) Major1.getSelectedToggle();
            String selectedMajor=selected.getText();
            Major major= checkMajor(selectedMajor);

            Student tempStudent = new Student(paymentName.getText(), major, 3, (float) 0, (float) 0, new Date(),false);

            int index = roster.find(tempStudent);
            if (index < 0) { //this means that a student does not exist
                messageArea.appendText("Couldn't find the student.\n");
            }else {

               if(roster.getStudent(index).getTuitionDue()<payment){
                   messageArea.appendText("Amount is greater than amount due.\n");
               }else{
                   roster.getStudent(index).setTuitionDue(roster.getStudent(index).getTuitionDue()-payment);
                   roster.getStudent(index).setTotalPayment( Float.parseFloat((roster.getStudent(index).getTotalPayment()+payment)+""));
                   roster.getStudent(index).setLastPaymentDate(date);
                   messageArea.appendText("Payment applied\n");

               }

            }

        }else{

            messageArea.appendText("Payment date invalid\n");
        }
        return ;


    }

    /**
     * method to give financial aid
     * @param event in which it will occur
     */
    @FXML
    void payFinancialAid (ActionEvent event){
        if (paymentName.getText()==null || paymentName.getText().isEmpty()){
            messageArea.appendText("No Name Entered\n");
            return;
        }
        if (Major1.getSelectedToggle()==null){
            messageArea.appendText("No major selected\n");
            return;
        }

        if (financialAidAmountText.getText()==null || financialAidAmountText.getText().isEmpty()){
            messageArea.appendText("No financial Entered\n");
            return;
        }


        double payment = Double.parseDouble(financialAidAmountText.getText());
        if(payment<0|| payment>MAX_AID ){
            messageArea.appendText("Invalid amount\n");
            return;
        }



            RadioButton selected=(RadioButton) Major1.getSelectedToggle();
            String selectedMajor=selected.getText();
            Major major= checkMajor(selectedMajor);

            Student tempStudent = new Student(paymentName.getText(), major, 3, (float) 0, (float) 0, new Date(),false);

            int index = roster.find(tempStudent);
            if (index < 0) { //this means that a student does not exist
                messageArea.appendText("Couldn't find the student.\n");
            }else {

                if(roster.getStudent(index).getCredits()< Student.minCreditsForFulltime){
                    messageArea.appendText("Part time student doesn't qualify for the award.\n");
                    return;
                }
                if( !(roster.getStudent(index) instanceof Resident)){
                    messageArea.appendText("Not a resident student.\n");
                    return;

                }
                if(!(((Resident) roster.getStudent(index)).reciveFinancialAid(payment))){
                    messageArea.appendText("Awarded once already\n");
                    return;
                }
                messageArea.appendText("Tuition updated \n");
                return;
            }


        return ;

    }

    /**
     * method to calculate tuitiondue individually
     * @param event in which it will occur
     */
    @FXML
    void tutionDue( ActionEvent event){

        if (Name.getText()==null || Name.getText().isEmpty()){
            messageArea.appendText("No Name Entered\n");
            return;
        }
        if (MajorStudent.getSelectedToggle()==null){
            messageArea.appendText("No major selected\n");
            return;
        }
        RadioButton selected=(RadioButton) MajorStudent.getSelectedToggle();
        String selectedMajor=selected.getText();
        Major major= checkMajor(selectedMajor);

        Student tempStudent = new Student(Name.getText(), major, 3, (float) 0, (float) 0, new Date(),false);

        int index = roster.find(tempStudent);
        if (index < 0) { //this means that a student does not exist
            messageArea.appendText("Couldn't find the student.\n");
            return ;
        }else if (roster.getStudent(index).getLastPaymentDate()==null && roster.getStudent(index).getTuitionDue()==0) {

            tutionText.clear();


            roster.getStudent(index).tuitionDue();
            DecimalFormat decimalFormat= new DecimalFormat();
            decimalFormat.setMaximumFractionDigits(2);
            tutionText.appendText(String.format("%,.2f",roster.getStudent(index).getTuitionDue()));
            messageArea.appendText("Tuition calculated\n");
            return;

        }
        else {
            tutionText.clear();
            messageArea.appendText("Tuition has already be calculated\n");
            return;
        }

    }


    /**
     * button that disables/unselects necessary buttons when a specific one is selected/unselected
     * @param event in which it will occur
     */
    @FXML
    void selectInternational(ActionEvent event) {
        if (International.isSelected()){
            TristateStates.setDisable(true);
            studyAbroad.setDisable(false);
            buttonCT.setSelected(false);
            buttonNY.setSelected(false);
        }
        else{
            TristateStates.setDisable(false);
            studyAbroad.setDisable(true);
            studyAbroad.setSelected(false);
            buttonNY.setDisable(true);
            buttonCT.setDisable(true);
        }

    }
    /**
     * button that disables/unselects necessary buttons when a specific one is selected/unselected
     * @param event in which it will occur
     */
    @FXML
    void selectNonResident(ActionEvent event) {
        NonResOpts.setDisable(false);
        buttonNY.setDisable(true);
        buttonCT.setDisable(true);
        studyAbroad.setDisable(true);
        Tristate.setSelected(false);
        International.setSelected(false);
    }
    /**
     * button that disables/unselects necessary buttons when a specific one is selected/unselected
     * @param event in which it will occur
     */
    @FXML
    void selectResident(ActionEvent event) {
        NonResOpts.setDisable(true);
        studyAbroad.setSelected(false);
        buttonCT.setSelected(false);
        buttonNY.setSelected(false);
        Tristate.setSelected(false);
        International.setSelected(false);
    }
    /**
     * button that disables/unselects necessary buttons when a specific one is selected/unselected
     * @param event in which it will occur
     */
    @FXML
    void selectTristate(ActionEvent event) {
        if (Tristate.isSelected()){
            TristateStates.setDisable(false);
            studyAbroad.setDisable(true);
            studyAbroad.setSelected(false);
            buttonCT.setDisable(false);
            buttonNY.setDisable(false);
        }
        else{
            TristateStates.setDisable(true);
            studyAbroad.setDisable(true);
            buttonNY.setSelected(false);
            buttonCT.setSelected(false);

        }
    }
    /**
     * button that sets an international student to abroad status
     * @param event in which it will occur
     */
    @FXML
    void setInternational(ActionEvent event) {

        if (paymentName.getText()==null || paymentName.getText().isEmpty()){
            messageArea.appendText("No Name Entered\n");
            return;
        }
        if (Major1.getSelectedToggle()==null){
            messageArea.appendText("No major selected\n");
            return;
        }

        RadioButton selected=(RadioButton) Major1.getSelectedToggle();
        String selectedMajor=selected.getText();
        Major major= checkMajor(selectedMajor);

        Student tempStudent = new Student(paymentName.getText(), major, 3, (float) 0, (float) 0, new Date(),false);

        int index = roster.find(tempStudent);
        if (index < 0) { //this means that a student does not exist
            messageArea.appendText("Couldn't find the student.\n");
            return ;
        }else {
            if( !(roster.getStudent(index) instanceof International)){
                messageArea.appendText("Not a international student.\n");
                return;

            }else {
                if(((com.example.project3softwaremeth.International) roster.getStudent(index)).checkAbroad()){
                    roster.getStudent(index).setTotalPayment(0);
                    roster.getStudent(index).tuitionDue();
                    roster.getStudent(index).setLastPaymentDate(null);
                    ((International) roster.getStudent(index)).setAbroad(false);
                    messageArea.appendText("Student no longer abroad.\n");
                }else {
                    roster.getStudent(index).setTotalPayment(0);
                    roster.getStudent(index).tuitionDue();
                    roster.getStudent(index).setLastPaymentDate(null);
                    ((International) roster.getStudent(index)).setAbroad(true);
                    messageArea.appendText("Student set abroad.\n");
                }
            }

        }
        return;

    }

    /**
     * method to remove student from roster
     * @param event in which it will occur
     */
    @FXML
    void removeStudent(ActionEvent event) {
        if (Name.getText()==null || Name.getText().isEmpty()){
            messageArea.appendText("No Name Entered\n");
            return;
        }
        if (MajorStudent.getSelectedToggle()==null){
            messageArea.appendText("No major selected\n");
            return;
        }
        RadioButton selected=(RadioButton) MajorStudent.getSelectedToggle();
        String selectedButton=selected.getText();
        Major major= checkMajor(selectedButton);

        Student student = new Student(Name.getText(),major, 3, (float) 0, (float) 0, new Date(),true);
        boolean removed = roster.remove(student);
        if(removed){
            messageArea.appendText("Student removed\n");
        }else{
            messageArea.appendText("Student not found\n");

        }

    }

    /**
     * method to add students
     * @param event in which it will occur
     */
    @FXML
    void addStudent(ActionEvent event) {
        int integer;
        if (Name.getText()==null || Name.getText().isEmpty()){
            messageArea.appendText("No Name Entered\n");
            return;
        }
        if (MajorStudent.getSelectedToggle()==null){
            messageArea.appendText("No major selected\n");
            return;
        }

        if(isResident.getSelectedToggle()==null){
            messageArea.appendText("No status selected\n");
            return;

        }


        RadioButton selected=(RadioButton) MajorStudent.getSelectedToggle();
        String selectedMajor=selected.getText();
        Major major= checkMajor(selectedMajor);


        Boolean isabroad  = studyAbroad.isSelected();


        try{
            integer= Integer.parseInt(creditHours.getText());
            if(integer<0){
                messageArea.appendText("Credit Hours can not be negative\n");
                return;
            }
            if(integer<3){
                messageArea.appendText("Minimum Credit Hours is 3\n");
                return;
            }
            if(integer>24){
                messageArea.appendText("Maximum Credit Hours is 24\n");
                return;
            }
        }
        catch (NumberFormatException e){
            messageArea.appendText("Not an Integer\n");
            return;
        }
        if(residentButton.isSelected()){
            Resident resident= new Resident(Name.getText(),major,integer);
            resident.setTuitionDue(0.0);
            boolean added= roster.add(resident);
            if (added){
            messageArea.appendText("Student added\n");}
            else {
                messageArea.appendText("Student is already in roster\n");
            }
            return;
        }
        if(nonResidentButton.isSelected()){
            if(Tristate.isSelected() ){
                if( NYCT.getSelectedToggle()==null){
                    messageArea.appendText("Please select state\n");
                    return;
                }
                RadioButton selected2=(RadioButton) NYCT.getSelectedToggle();
                String selectedState=selected2.getText();
                State state= checkState(selectedState);
                TriState tristate= new TriState(Name.getText(),major,integer,state);
                boolean added= roster.add(tristate);
                if (added){
                    messageArea.appendText("Student added\n");}
                else {
                    messageArea.appendText("Student is already in roster\n");
                }
                return;
            }
            else if(International.isSelected()){
                if(isabroad){
                if(Integer.parseInt(creditHours.getText())>Student.minCreditsForFulltime){
                    messageArea.appendText("Must have 12 credits at most\n");
                    return;
                    }}
                if(!isabroad){
                    if(Integer.parseInt(creditHours.getText())<Student.minCreditsForFulltime){
                        messageArea.appendText("Minimum credits for non-abroad International Students is 12\n");
                        return;
                    }
                }

                International international= new International(Name.getText(),major,integer,isabroad);

                boolean added= roster.add(international);
                if (added){

                    messageArea.appendText("Student added\n");}
                else {
                    messageArea.appendText("Student is already in roster\n");
                }
                return;

            }
            else {
                NonResident nonresident = new NonResident(Name.getText(), major, integer);
                boolean added = roster.add(nonresident);
                if (added) {
                    messageArea.appendText("Student added\n");
                } else {
                    messageArea.appendText("Student is already in roster\n");
                }
                return;
            }

        }

    }

    /**
     * method to print all students in roster
     * @param event in which it will occur
     */
    @FXML
    void printStudents(ActionEvent event){
        messageArea.appendText(roster.print());
    }

    /**
     * method to print all students in roster by date
     * @param event in which it will occur
     */
    @FXML
    void printbyDate(ActionEvent event){
        messageArea.appendText(roster.printByPaymentDate());
    }

    /**
     * method to print all students in roster by name
     * @param event in which it will occur
     */
    @FXML
    void printStudentsinOrder(ActionEvent event){
        messageArea.appendText(roster.printByName());
    }
    /**
     * method to calculate tuition due for roster
     * @param actionEvent in which it occurs
     */
    @FXML
    void calculateTutionDues(ActionEvent actionEvent) {
        if(roster.getSize()==0){
            messageArea.appendText("Roster is empty\n");
            return;
        }else{
            for(int i=0;i<roster.getSize();i++){
                if(roster.getStudent(i).getLastPaymentDate()==null && roster.getStudent(i).getTuitionDue()==0 ){
                    roster.getStudent(i).tuitionDue();
                }

            }
            messageArea.appendText("Tuition calculated for all students\n");
            return;
        }
    }


    /**
     * method to check major of student from selected major button
     * @param major given major from user input
     * @return enum form of Major
     */
    private Major checkMajor(String major){
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
        }
        return null;
}

    /**
     * method to check state of student in tristate area from user input
     * @param state given state from user input
     * @return enum form of state
     */
    private State checkState(String state){
        switch (state.toUpperCase()) {
            case "NY":
                return State.NY;
            case "CT":
                return State.CT;
        }
        return null;
    }
}



