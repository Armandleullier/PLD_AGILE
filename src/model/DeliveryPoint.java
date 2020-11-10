package model;

import java.sql.Time;
import java.util.*;

/**
 * 
 */
public class DeliveryPoint extends Step {
    /**
     *
     */
    public int deliveryDuration;

    /**
     * Default constructor
     */
    public DeliveryPoint() {
    }

    public DeliveryPoint(Intersection intersection, int deliveryDuration) {
        this.longitude = intersection.getLongitude();
        this.latitude = intersection.getLatitude();
        this.id = intersection.getId();
        this.listSegments = intersection.listSegments;
        this.deliveryDuration = deliveryDuration;
    }

    public DeliveryPoint(long id, double latitude,double longitude,  int deliveryDuration) {
        super(id, latitude, longitude);
        this.deliveryDuration = deliveryDuration;
    }

    /**
     * Getters - Setters
     */
    public int getDeliveryDuration() {
        return deliveryDuration;
    }

    public void setDeliveryDuration(int deliveryDuration) {
        this.deliveryDuration = deliveryDuration;
    }

}