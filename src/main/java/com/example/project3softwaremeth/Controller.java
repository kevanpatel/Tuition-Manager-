package com.example.project3softwaremeth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyEvent;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.StringTokenizer;

public class Controller {
    Roster roster= new Roster();

    private static final double MAX_AID = 10000;


    @FXML
    private ToggleButton International;

    @FXML
    private ToggleGroup MajorStudent;

    @FXML
    private ToggleGroup Major1;

    @FXML
    private ToggleGroup NYCT;

    @FXML
    private VBox NonResOpts;

    @FXML
    private ToggleButton Tristate;

    @FXML
    private DatePicker paymentDateID;

    @FXML
    private HBox TristateStates;

    @FXML
    private ToggleGroup isResident;

    @FXML
    private CheckBox studyAbroad;

    @FXML
    private TextField creditHours;

    @FXML
    private TextArea messageArea;

    @FXML
    private TextField tutionText;

    @FXML
    private TextField paymentName;

    @FXML
    private TextField paymentAmount;

    @FXML
    private TextField financialAidAmountText;

    @FXML
    private Button paymentButton;

    @FXML
    private Button financialAidSet;

    @FXML
    private RadioButton majorBAP;

    @FXML
    private RadioButton majorCSP;

    @FXML
    private RadioButton majorEEP;

    @FXML
    private RadioButton majorITP;

    @FXML
    private RadioButton majorMEP;

    @FXML
    private TextField Name;

    @FXML
    private RadioButton majorBA;

    @FXML
    private RadioButton majorCS;

    @FXML
    private RadioButton majorEE;

    @FXML
    private RadioButton majorIT;

    @FXML
    private RadioButton majorME;

    @FXML
    private RadioButton nonResidentButton;

    @FXML
    private RadioButton residentButton;

    @FXML
    private RadioButton buttonCT;

    @FXML
    private RadioButton buttonNY;

    @FXML
    private Button setAbroad;

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
        if(payment<=0|| payment>MAX_AID ){
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
        }else {

            tutionText.clear();


            roster.getStudent(index).tuitionDue();
            DecimalFormat decimalFormat= new DecimalFormat();
            decimalFormat.setMaximumFractionDigits(2);
            tutionText.appendText(String.format("%,.2f",roster.getStudent(index).getTuitionDue()));
            messageArea.appendText("Tuition calculated\n");


        }



    }


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
        }

    }
    @FXML
    void selectNonResident(ActionEvent event) {
        NonResOpts.setDisable(false);
    }

    @FXML
    void selectResident(ActionEvent event) {
        NonResOpts.setDisable(true);
        studyAbroad.setSelected(false);
        buttonCT.setSelected(false);
        buttonNY.setSelected(false);
        Tristate.setSelected(false);
        International.setSelected(false);
    }

    @FXML
    void selectTristate(ActionEvent event) {
        if (Tristate.isSelected()){
            TristateStates.setDisable(false);
            studyAbroad.setDisable(true);
            studyAbroad.setSelected(false);
        }
        else{
            TristateStates.setDisable(true);
            studyAbroad.setDisable(false);
            buttonNY.setSelected(false);
            buttonCT.setSelected(false);

        }
    }

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
                if(roster.getStudent(index).getCredits()>12){
                    roster.getStudent(index).setCredits(12);
                }
                roster.getStudent(index).setTotalPayment(0);
                roster.getStudent(index).tuitionDue();
                roster.getStudent(index).setLastPaymentDate(null);
                ((International) roster.getStudent(index)).setAbroad();
            }

        }
        return;

    }

    @FXML
    void checkCreditHours(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)){
            try{
                int integer= Integer.parseInt(creditHours.getText());
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
        }

    }
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
                if(Integer.parseInt(creditHours.getText())!=Student.minCreditsForFulltime){
                    messageArea.appendText("Must have exactly 12 credits\n");

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
    @FXML
    void printStudents(ActionEvent event){
        messageArea.appendText(roster.print());
    }
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
    private State checkState(String state){
        switch (state.toUpperCase()) {
            case "NY":
                return State.NY;
            case "CT":
                return State.CT;
        }
        return null;
    }

    public void calculateTutionDues(ActionEvent actionEvent) {
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
}
