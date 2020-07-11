package com.azer.meetingmanager.ui.home;

import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.App;
import com.azer.meetingmanager.ui.ViewLoader;
import com.azer.meetingmanager.ui.components.OverlayController;
import com.azer.meetingmanager.ui.components.TopbarController;
import com.azer.meetingmanager.ui.master.MasterController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;

public class HomeController implements Initializable {

    @FXML
    private TopbarController topbarController;
    @FXML
    private OverlayController overlayController;
    @FXML
    private AnchorPane root;
    @FXML
    private Hyperlink githubHyperlink;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupTopbar();
        setupOverlay();

    }

    @FXML
    void onOpenHyperlink(ActionEvent event) {
        App.getInstance().getHostServices().showDocument(githubHyperlink.getText());
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

        });
    }

}