package com.azer.meetingmanager.ui.master;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.azer.meetingmanager.App;
import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.ui.components.MeetingContainerController;
import com.azer.meetingmanager.ui.components.TopbarController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class MasterController implements Initializable {

    private static final String DISPLAY_LIST = "List view";
    private static final String DISPLAY_CARD = "Card view";

    @FXML
    private TopbarController topbarController;

    @FXML
    private MeetingContainerController meetingContainerController;

    @FXML
    private Button displayButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTopbar();
        settupMeetingContainer();
        
    }

    private void settupMeetingContainer() {
        changeMeetingContainer(true);
        List<Meeting> meetings = App.getUnitOfWork().getOpeningMeetings();
        meetingContainerController.notifyCollectionChanged(meetings);
    }
    private void setupTopbar() {
        topbarController.setTitle("Meetings");
    }

    private void changeMeetingContainer(boolean vbox) {
        displayButton.setText(vbox ? DISPLAY_CARD : DISPLAY_LIST);
        meetingContainerController.changeContainerPane(vbox);
    }

    @FXML
    public void onChangeDisplay(ActionEvent event) {
        changeMeetingContainer(displayButton.getText().equals(DISPLAY_LIST));
    }
}