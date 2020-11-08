package controler;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import model.Intersection;
import model.Map;
import model.Request;

import java.io.File;

/**
 * @author T-REXANOME
 */
public class InitialState implements State {

    long CurrentId;
    Request request = new Request();

    /**
     * Default constructor.
     */
    public InitialState() {
    }

    /**
     * Add a request.
     *
     * @param controller
     */
    @Override
    public void addRequest(Controller controller) {
        if (!controller.map.getListRequests().isEmpty()) {
            controller.addPickupState.entryAction(controller, request);
            controller.setCurrentState(controller.addPickupState);
        }
    }

    /**
     * Delete a request.
     *
     * @param controller
     */
    public void deleteRequest(Controller controller) {
        Request request = controller.map.getRequestByIntersectionId(CurrentId);
        if (request != null) {
            controller.deleteState.entryAction(controller, request);
            controller.setCurrentState(controller.deleteState);
        }
    }

    /**
     * Left click.
     *
     * @param controller
     * @param map
     * @param listOfCommand
     * @param i             intersection
     */
    @Override
    public void leftClick(Controller controller, Map map, ListOfCommand listOfCommand, Intersection i) {

        //Unselect preceding point
        controller.Gview.undrawMouseSelection(CurrentId);
        controller.Gview.undrawMouseSelection(CurrentId);

        //Select preceding point
        controller.Gview.drawMouseSelection(i.getId());
        controller.Gview.drawMouseSelection(i.getId());

        CurrentId = i.getId();

        //Diplay in textual view if the point is a request
        if (controller.map.getRequestByIntersectionId(i.getId()) != null)
            controller.Tview.selectRequest(controller.map.getRequestByIntersectionId(i.getId()));
    }

    /**
     * @param idIntersection id of an interection
     * @param controller
     */
    @Override
    public void mouseOn(long idIntersection, Controller controller) {
    }

    /**
     * Undo.
     *
     * @param listOfCommand
     * @param controller
     */
    @Override
    public void undo(ListOfCommand listOfCommand, Controller controller) {
        listOfCommand.undo();
    }

    /**
     * Redo.
     *
     * @param listOfCommand
     * @param controller
     */
    @Override
    public void redo(ListOfCommand listOfCommand, Controller controller) {
        listOfCommand.redo();
    }

    /**
     * Load the map.
     *
     * @param event
     * @param controller
     * @param map
     */
    @Override
    public void LoadMap(ActionEvent event, Controller controller, Map map) {
        FileChooser mapFileChooser = new FileChooser();
        mapFileChooser.setTitle("Load Map");
        mapFileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML", "*.xml"));
        File mapFile = mapFileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        XMLLoader xmlloader = new XMLLoader();
        xmlloader.parseMapXML(mapFile.getAbsolutePath(), map);

        controller.Tview.setMessage("Please load a request list");
    }

    /**
     * Load requests.
     *
     * @param event
     * @param controller
     * @param map
     */
    @Override
    public void LoadRequests(ActionEvent event, Controller controller, Map map) {
        FileChooser requestsFileChooser = new FileChooser();
        requestsFileChooser.setTitle("Load Requests");
        requestsFileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML", "*.xml"));
        File requestsFile = requestsFileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        XMLLoader xmlloader = new XMLLoader();
        xmlloader.parseRequestXML(requestsFile.getAbsolutePath(), map);
        controller.Gview.enableSelection();
        entryAction(controller);
    }

    /**
     * @param controller
     */
    public void entryAction(Controller controller) {
        controller.Tview.setMessage("Compute Tour, add request or load new request or map.");
    }
}