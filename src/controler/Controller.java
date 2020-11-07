package controler;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import model.Intersection;
import model.Map;
import model.Path;
import model.Request;
import view.GraphicalView;
import view.TextualView;

import java.io.File;
import java.util.*;

/**
 * Controller
 *
 * @author T-REXANOME
 */
public class Controller {

    ListOfCommand listOfCommand;
    State currentState;
    Map map;

    protected GraphicalView Gview;
    protected TextualView Tview;
    protected TextArea TextMessage;

    public void setTextArea(TextArea textArea) {
        TextMessage = textArea;
    }

    public void setGview(GraphicalView gview) {
        this.Gview = gview;
    }

    public void setTview(TextualView tview) {
        this.Tview = tview;
    }

    protected final InitialState initialState = new InitialState();
    protected final RequestStatePickUpPoint requestStatePickUpPoint = new RequestStatePickUpPoint();
    protected final RequestStateDeliveryPoint requestStateDeliveryPoint = new RequestStateDeliveryPoint();
    protected final RequestStateConfirmation requestStateConfirmation = new RequestStateConfirmation();
    protected final DeleteState deleteState = new DeleteState();

    /**
     * Default constructor
     */
    public Controller(Map newMap) {
        listOfCommand = new ListOfCommand();
        currentState = initialState;
        map = newMap;
    }

    protected void setCurrentState(State newState) {
        currentState = newState;
    }

    /**
     * Load the map.
     *
     * @param event
     */
    public void LoadMap(ActionEvent event) {
        FileChooser mapFileChooser = new FileChooser();
        mapFileChooser.setTitle("Load Map");
        mapFileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML", "*.xml"));
        File mapFile = mapFileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        XMLLoader xmlloader = new XMLLoader();
        xmlloader.parseMapXML(mapFile.getAbsolutePath(), map);

    }

    /**
     * Load requests.
     *
     * @param event
     */
    public void LoadRequests(ActionEvent event) {
        FileChooser requestsFileChooser = new FileChooser();
        requestsFileChooser.setTitle("Load Requests");
        requestsFileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML", "*.xml"));
        File requestsFile = requestsFileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        XMLLoader xmlloader = new XMLLoader();
        xmlloader.parseRequestXML(requestsFile.getAbsolutePath(), map);
    }

    /**
     *
     */
    public void computeOptimalTour() {
        Algorithm algo = new Algorithm(map);
        HashMap<Long, HashMap<Long, Path>> mapSmallestPaths = algo.computeSmallestPaths();
        algo.computeOptimalTour(mapSmallestPaths);
    }

    /**
     * @param idIntersection
     */
    public void leftClick(long idIntersection) {
        Intersection intersection = map.getListIntersections().get(idIntersection);
        currentState.leftClick(this, map, listOfCommand, intersection);
    }

    /**
     * @param duration
     */
    public void addDuration(int duration) {
        currentState.addDuration(duration, this);
    }

    /**
     *
     */
    public void addRequest() {
        currentState.addRequest(this);
    }

    /**
     *
     */
    public void confirmRequest() {
        currentState.confirmRequest(this, map);
    }
}

