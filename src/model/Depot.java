package model;

import java.sql.Time;

public class Depot {

    protected long id;
    protected Time departureTime;

    public Depot() {
    }

    public Depot(long id,Time departureTime) {
        this.id = id;
        this.departureTime=departureTime;
    }
    
}
