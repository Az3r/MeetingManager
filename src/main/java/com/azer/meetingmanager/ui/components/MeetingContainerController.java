package com.azer.meetingmanager.ui.components;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import com.azer.meetingmanager.App;
import com.azer.meetingmanager.data.LoggedUserResource;
import com.azer.meetingmanager.data.models.Meeting;
import com.azer.meetingmanager.data.models.User;
import com.azer.meetingmanager.ui.OnItemActionListener;
import com.azer.meetingmanager.ui.ViewLoader;
import com.azer.meetingmanager.ui.detail.MeetingDetailController;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class MeetingContainerController implements Initializable, ListChangeListener<Meeting> {

    @FXML
    private ScrollPane root;

    // propperties for each meeting item
    private String leftButtonText;
    private String rightButtonText;
    private OnItemActionListener<Meeting> leftButtonListener;
    private OnItemActionListener<Meeting> rightButtonListener;

    // container propperties
    private Pane container;
    private VBox vboxLayout = createVBoxContainer();
    private TilePane tilePaneLayout = createTilePaneContainer();
    private String file = "views/MeetingItemHBox.fxml";
    private ObservableList<Meeting> items = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setupItemDefault();
        changeContainerPane(true);

        items.addListener(this);
        settupMeetingItems();
    }

    /** retrieve all available meetings and display them */
    private void settupMeetingItems() {
        List<Meeting> meetings = App.getUnitOfWork().getAllMeetings();
        items.addAll(meetings);
    }

    /**
     * Default behavior of each meeting item
     */
    private void setupItemDefault() {
        setLeftButtonText("Detail");
        setRightButtonText("Register");
        setLeftButtonListener(e -> {
            ViewLoader<MeetingDetailController> loader = new ViewLoader<>("views/MeetingDetail.fxml",
                    root.getScene().getRoot());
            loader.getController().setPreviousParent(loader.getPreviousParent());
            root.getScene().setRoot(loader.getRoot());
        });
        setRightButtonListener(meeting -> {
            LoggedUserResource resource = LoggedUserResource.getInstance();
            if (resource.isGuess()) {
                new Alert(AlertType.ERROR, "You must login first!", ButtonType.OK).showAndWait();
                return;
            }
            User user = resource.getUser();
            if (App.getUnitOfWork().isAccepted(user, meeting))
                new Alert(AlertType.CONFIRMATION, "You have already joined this meeting", ButtonType.OK).showAndWait();
            else if (App.getUnitOfWork().isPending(user, meeting))
                new Alert(AlertType.CONFIRMATION, "Your request is currently in pending list", ButtonType.OK)
                        .showAndWait();
            else {
                if (App.getUnitOfWork().registerMeeting(user, meeting))
                    new Alert(AlertType.CONFIRMATION, "Successfully send request to admin ", ButtonType.OK)
                            .showAndWait();
                else
                    new Alert(AlertType.ERROR, "Internal server error, please check log", ButtonType.OK).showAndWait();
            }
        });
    }

    private void bindItemBehavior(MeetingItemController controller, Meeting value) {
        controller.setLeftButtonText(leftButtonText);
        controller.setRightButtonText(rightButtonText);
        controller.setLeftButtonAction(leftButtonListener, value);
        controller.setRightButtonAction(rightButtonListener, value);
    }

    private Parent createMeetingItemNode(Meeting meeting) {
        Parent node = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(file));
            node = loader.load();
            MeetingItemController controller = loader.getController();
            bindItemBehavior(controller, meeting);
            controller.notifyDataChanged(meeting);

        } catch (Exception e) {
            System.err.println(e);
            throw new ExceptionInInitializerError(e);
        }

        return node;
    }

    public void changeContainerPane(boolean vbox) {
        container = vbox ? vboxLayout : tilePaneLayout;
        file = vbox ? "views/MeetingItemHBox.fxml" : "views/MeetingItemVBox.fxml";
        if (container.getChildren().isEmpty())
            addToContainer(items);
        root.setContent(container);
    }

    private static VBox createVBoxContainer() {
        VBox container = new VBox();
        return container;
    }

    private static TilePane createTilePaneContainer() {
        TilePane tilePane = new TilePane();
        tilePane.setAlignment(Pos.CENTER_LEFT);
        tilePane.setHgap(30.0);
        tilePane.setVgap(60.0);
        tilePane.setPadding(new Insets(30));
        return tilePane;
    }

    public void setCollection(Collection<Meeting> collection) {
        this.items.setAll(collection);
    }

    public void setLeftButtonText(String text) {
        this.leftButtonText = text;
    }

    public void setRightButtonText(String text) {
        this.rightButtonText = text;
    }

    public void setLeftButtonListener(OnItemActionListener<Meeting> listener) {
        this.leftButtonListener = listener;
    }

    public void setRightButtonListener(OnItemActionListener<Meeting> listener) {
        this.rightButtonListener = listener;
    }

    private void addToContainer(List<? extends Meeting> collection) {
        for (Meeting meeting : items) {
            container.getChildren().add(createMeetingItemNode(meeting));
        }
    }

    private void removeFromContainer(int fromInclusive, int size) {
        container.getChildren().remove(fromInclusive, fromInclusive + size);
    }

    @Override
    public void onChanged(Change<? extends Meeting> c) {
        c.next();
        if (c.wasAdded()) {
            addToContainer(c.getAddedSubList());
        } else if (c.wasRemoved()) {
            removeFromContainer(c.getFrom(), c.getRemovedSize());
        }
    }
}