package model;

import java.util.*;

/**
 * 
 */
public class Request extends Observable {
    /**
     *
     */
    protected int order;
    protected PickUpPoint pickUpPoint;
    protected DeliveryPoint deliveryPoint;

    /**
     * Default constructor
     */
    public Request() {
    }

    public Request(Request r) {
        this.order = r.order;
        this.pickUpPoint = r.getPickUpPoint();
        this.deliveryPoint = r.getDeliveryPoint();
        if (deliveryPoint != null && pickUpPoint != null) {
            this.pickUpPoint.setRequest(this);
            this.deliveryPoint.setRequest(this);
        }
    }

    public Request(PickUpPoint pickUpPoint, DeliveryPoint deliveryPoint) {
        this.pickUpPoint = pickUpPoint;
        this.deliveryPoint = deliveryPoint;
        this.pickUpPoint.setRequest(this);
        this.deliveryPoint.setRequest(this);
    }

    /**
     * Getters - Setters
     */
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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

    @Override
    public String toString() {
        return "Request{" +
                "order=" + order +
                ", pickUpPoint=" + pickUpPoint +
                ", deliveryPoint=" + deliveryPoint +
                '}';
    }
}