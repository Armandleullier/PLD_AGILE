package controler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import model.*;
import model.Map;
import view.GraphicalView;
import view.MouseGestures;
import view.TextualView;

import javax.swing.*;
import java.io.File;
import java.util.*;

/**
 * 
 */
public class Controller {
    
    protected MouseGestures mg;
    protected ListOfCommand listOfCommand;
    protected State currentState;
    protected Map map;
    protected Rectangle2D screenBounds;

    protected GraphicalView Gview;
    protected TextualView Tview;

    @FXML
    protected Pane overlay;
    @FXML
    protected Pane myPane;
    @FXML
    protected javafx.scene.control.TextArea TextArea;
    @FXML
    protected Label TextTour;
    @FXML
    protected Button confirmAction;
    @FXML
    protected Button LoadMap;
    @FXML
    protected Button LoadRequests;
    @FXML
    protected Button ComputeTour;
    @FXML
    protected Button ExportTour;
    @FXML
    protected Button addRequest;
    @FXML
    protected Button deleteRequest;


    protected final InitialState initialState = new InitialState();
    protected final AddPickupState addPickupState = new AddPickupState();
    protected final AddDeliveryState addDeliveryState = new AddDeliveryState();
    protected final ConfirmRequestState confirmRequestState = new ConfirmRequestState();
    protected final DeleteState deleteState = new DeleteState();

    /**
     * Default constructor
     */
    public Controller() {

        mg = new MouseGestures(this);
        map = new Map();
        listOfCommand = new ListOfCommand();
        currentState = initialState;
    }

    //Getters and setters
    public ListOfCommand getListOfCommand() {
        return listOfCommand;
    }

    public Map getMap() {
        return map;
    }

    protected void setCurrentState(State newState) {
        currentState = newState;
    }

    protected void disableButtons(Boolean bool){
        LoadMap.setDisable(bool);
        LoadRequests.setDisable(bool);
        ComputeTour.setDisable(bool);
        ExportTour.setDisable(bool);
    }

    //Public Methods
    public void LoadRequests(ActionEvent event) {
        currentState.LoadRequests(event,this,map);
    }

    public void computeTour(ActionEvent event) {
        Tview.setMessage("Computing optimal tour...");
        currentState.computeTour(this,map);
    }

    public void ExportRoadMap (ActionEvent event) {
        FileChooser exportFileChooser = new FileChooser();
        exportFileChooser.setTitle("Export RoadMap");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        exportFileChooser.getExtensionFilters().add(extFilter);
        File exportLocation = exportFileChooser.showSaveDialog(((Node)event.getSource()).getScene().getWindow());

        if (exportLocation != null) {
            map.getTour().generateRoadMap(exportLocation.getPath());
        }
    }

    public void leftClick(long idIntersection){
        Intersection intersection = map.getListIntersections().get(idIntersection);
        currentState.leftClick(this, map, listOfCommand, intersection);
    }

    public void leftClick(Step step){
        currentState.leftClick(this, map, listOfCommand, step);
    }

    public void LoadMap(ActionEvent event) {

        currentState.LoadMap(event, this, map);

    }

    public void Zoom(ActionEvent event) { Gview.zoom(); }

    public void UnZoom(ActionEvent event) {
        Gview.unZoom();
    }

    public void resetView(ActionEvent event) {
        Gview.resetView();
    }

    public void addDuration(int duration) {
        currentState.addDuration(duration, this);
    }

    public void addRequest(ActionEvent event) {
        currentState.addRequest(this);
    }

    public void deleteRequest(ActionEvent event){
        currentState.deleteRequest(this);
    }

    public void confirmAction() {
        currentState.confirmAction(this, map);
    }

    public void undo(ActionEvent event) {
        currentState.undo(listOfCommand, this);
    }

    public void redo(ActionEvent event) {
        currentState.redo(listOfCommand,this);
    }

}