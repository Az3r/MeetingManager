package com.azer.meetingmanager.ui.home;

import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.App;
import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.data.repositories.MeetingRepository;
import com.azer.meetingmanager.ui.ViewLoader;
import com.azer.meetingmanager.ui.components.OverlayController;
import com.azer.meetingmanager.ui.components.TopbarController;
import com.azer.meetingmanager.ui.detail.MeetingDetailController;
import com.azer.meetingmanager.ui.master.MasterController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class HomeController implements Initializable {


    @FXML
    private AnchorPane root;

    @FXML
    private Hyperlink githubHyperlink;

    @FXML
    private ImageView meetingPhoto;

    @FXML
    private Text meetingTitle;

    @FXML
    private Text meetingBriefDesc;

    private Meeting lastestMeeting;

    @FXML
    private TopbarController topbarController;

    @FXML
    private OverlayController overlayController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTopbar();
        setupOverlay();
        setupLatestMeeting();
    }

    @FXML
    void onOpenHyperlink(ActionEvent event) {
        App.getInstance().getHostServices().showDocument(githubHyperlink.getText());
    }

    private void setupLatestMeeting() {
        MeetingRepository repository = new MeetingRepository(App.getSessionFactory().openSession());
        Meeting meeting = repository.getLatestMeeting();
        repository.close();
        notifiDataChanged(meeting);

    }

    public void notifiDataChanged(Meeting meeting) {
        meetingTitle.setText(meeting.get);
        lastestMeeting = meeting;
    }

    private void setupTopbar() {
        topbarController.setTitle("Home");
    }

    private void setupOverlay() {

        overlayController.setLeftButtonText("See more");
        overlayController.setLeftButtonAction(e -> {
            ViewLoader<MasterController> loader = new ViewLoader<>("views/Master.fxml");
            root.getScene().setRoot(loader.getRoot());

        });

        overlayController.setRightButtonText("Detail");
        overlayController.setRightButtonAction(e -> {
            ViewLoader<MeetingDetailController> loader = new ViewLoader<>("views/MeetingDetail.fxml", root.getScene().getRoot());
            loader.getController().setPreviousParent(loader.getPreviousParent());
            loader.getController().notifyDataChanged(lastestMeeting);
            root.getScene().setRoot(loader.getRoot());
        });
    }

}