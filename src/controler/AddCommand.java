package controler;

import model.Map;
import model.Request;

/**
 * Create the command which adds the request r to the plan m.
 *
 * @author T-REXANOME
 */
public class AddCommand implements Command {

    private Map map;
    private Request request;
    private Long precedingPickUpId;
    private Long precedingDeliveryId;

    /**
     * Constructor.
     *
     * @param m          the map to which f is added
     * @param r          the request added to m
     * @param deliveryId id of the delivery point
     * @param pickUpId   id of the pick up point
     */
    public AddCommand(Map m, Request r, Long pickUpId, Long deliveryId) {
        this.map = m;
        this.request = r;
        this.precedingPickUpId = pickUpId;
        this.precedingDeliveryId = deliveryId;
    }

    /**
     * Do action.
     */
    @Override
    public int doCommand() {
        int errorCode = 0;
        if(precedingDeliveryId !=0 && precedingPickUpId != 0)
            errorCode = map.addRequest(request,precedingPickUpId,precedingDeliveryId);
        else
            errorCode= map.addRequest(request);
        return errorCode;
    }

    /**
     * Temporary remove the last added command (this command may be reinserted again with redo).
     */
    @Override
    public int undoCommand() {
        map.removeRequest(request);
        return 0;
    }
}
