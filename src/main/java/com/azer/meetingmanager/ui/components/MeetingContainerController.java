package com.azer.meetingmanager.ui.components;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.azer.meetingmanager.data.models.Meeting;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class MeetingContainerController implements Initializable {

    @FXML
    private ScrollPane root;

    private ObservableList<Meeting> items = FXCollections.observableArrayList(new Meeting(),new Meeting(),new Meeting(),new Meeting(),new Meeting(),new Meeting());

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * switch between two display styles: VBox or TilePane
     * 
     * @param vbox true = use VBox, otherwise use TilePane
     */
    public void changeContainerPane(boolean vbox) {

        // HBox item for VBox container; VBox item for TilePane container
        Pane container = vbox ? createVBoxContainer() : createTilePaneContainer();
        String file = vbox ? "views/MeetingItemHBox.fxml" : "views/MeetingItemVBox.fxml";

        for (Meeting meeting : items) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(file));
                Parent itemRoot = loader.load();
                MeetingItemController controller = loader.getController();
                controller.notifyDataChanged(meeting);
                container.getChildren().add(itemRoot);

            } catch (IOException e) {
                System.err.println("Unable to load MeetingItem.fxml");
                throw new ExceptionInInitializerError(e);
            }
        }

        root.setContent(container);
    }

    private VBox createVBoxContainer() {
        VBox container = new VBox();
        return container;
    }

    private TilePane createTilePaneContainer() {
        TilePane tilePane = new TilePane();
        tilePane.setAlignment(Pos.CENTER_LEFT);
        tilePane.setHgap(30.0);
        tilePane.setVgap(60.0);
        tilePane.setPadding(new Insets(30));
        return tilePane;
    }
}