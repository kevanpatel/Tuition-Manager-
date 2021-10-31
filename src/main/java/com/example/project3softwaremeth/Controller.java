package com.example.project3softwaremeth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyEvent;

public class Controller {
    Roster roster= new Roster();
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
    void selectInternational(ActionEvent event) {
        if (International.isSelected()){
            TristateStates.setDisable(true);
            studyAbroad.setDisable(false);
        }
        else{
            TristateStates.setDisable(false);
            studyAbroad.setDisable(true);
        }

    }
    @FXML
    void selectNonResident(ActionEvent event) {
        NonResOpts.setDisable(false);
    }

    @FXML
    void selectResident(ActionEvent event) {
        NonResOpts.setDisable(true);
    }

    @FXML
    void selectTristate(ActionEvent event) {
        if (Tristate.isSelected()){
            TristateStates.setDisable(false);
            studyAbroad.setDisable(true);
        }
        else{
            TristateStates.setDisable(true);
            studyAbroad.setDisable(false);

        }
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
            RadioButton selected2=(RadioButton) NYCT.getSelectedToggle();
            String selectedState=selected2.getText();
            State state= checkState(selectedState);


            if(Tristate.isSelected() ){
                if( NYCT.getSelectedToggle()==null){
                    messageArea.appendText("Please select state\n");

                return;
                }
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
}
