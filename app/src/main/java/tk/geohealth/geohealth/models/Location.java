package tk.geohealth.geohealth.models;

import java.io.Serializable;

/**
 * Created by GeoHealth on 13/02/16.
 */
public class Location implements Serializable {
    private double lat;
    private double lon;

    public Location(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }
}
