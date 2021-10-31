package com.example.project3softwaremeth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Controller {
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
}
