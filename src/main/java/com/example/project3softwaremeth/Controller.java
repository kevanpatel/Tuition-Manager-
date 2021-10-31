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
    private ToggleGroup Major;

    @FXML
    private ToggleGroup Major1;

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
    void addStudent(ActionEvent event) {
        int integer;
        if (Name.getText()==null || Name.getText().isEmpty()){
            messageArea.appendText("No Name Entered\n");
            return;
        }
        if (Major.getSelectedToggle()==null){
            messageArea.appendText("No major selected\n");
            return;
        }
        RadioButton selected=(RadioButton)Major.getSelectedToggle();
        String selectedButton=selected.getText();
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
            com.example.project3softwaremeth.Major major= checkMajor(selectedButton);
            Resident resident= new Resident(Name.getText(),major,integer);
            boolean added= roster.add(resident);
            if (added){
            messageArea.appendText("Student added\n");}
            else {
                messageArea.appendText("Student is already in roster\n");
            }
            return;
        }
        if(nonResidentButton.isSelected() && !Tristate.isSelected() && !International.isSelected()){

        }

    }
    @FXML
    void printStudents(ActionEvent event){
        messageArea.appendText(roster.print());
    }
    private Major checkMajor(String major){
        switch (major.toUpperCase()) {
            case "CS":
                return com.example.project3softwaremeth.Major.CS;
            case "IT":
                return com.example.project3softwaremeth.Major.IT;
            case "BA":
                return com.example.project3softwaremeth.Major.BA;
            case "EE":
                return com.example.project3softwaremeth.Major.EE;
            case "ME":
                return com.example.project3softwaremeth.Major.ME;
        }
        return null;
}
}
