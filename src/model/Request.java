package model;

import java.util.*;

/**
 * Consisting of a pickup point and a delivery point, the deliverer must first collect the package before delivering it.
 *
 * @author T-REXANOME
 */
public class Request extends Observable {
    protected int order;
    protected PickUpPoint pickUpPoint;
    protected DeliveryPoint deliveryPoint;

    /**
     * Default constructor.
     */
    public Request() {
    }

    /**
     * Constructor.
     *
     * @param r request
     */
    public Request(Request r) {
        this.order = r.order;
        this.pickUpPoint = r.getPickUpPoint();
        this.deliveryPoint = r.getDeliveryPoint();
        if (deliveryPoint != null && pickUpPoint != null) {
            this.pickUpPoint.setRequest(this);
            this.deliveryPoint.setRequest(this);
        }
    }

    /**
     * Constructor.
     *
     * @param pickUpPoint   intersection for which a package must be picked up
     * @param deliveryPoint intersection for which a package must be delivered
     */
    public Request(PickUpPoint pickUpPoint, DeliveryPoint deliveryPoint) {
        this.pickUpPoint = pickUpPoint;
        this.deliveryPoint = deliveryPoint;
        this.pickUpPoint.setRequest(this);
        this.deliveryPoint.setRequest(this);
    }

    /*
     * Getters - Setters
     */

    public int getOrder() {
        return order;
    }

    public PickUpPoint getPickUpPoint() {
        return pickUpPoint;
    }

    public void setPickUpPoint(PickUpPoint pickUppoint) {
        this.pickUpPoint = pickUppoint;
    }

    public DeliveryPoint getDeliveryPoint() {
        return deliveryPoint;
    }

    public void setDeliveryPoint(DeliveryPoint deliveryPoint) {
        this.deliveryPoint = deliveryPoint;
    }

}