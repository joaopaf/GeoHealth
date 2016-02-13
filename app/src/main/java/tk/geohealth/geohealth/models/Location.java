package tk.geohealth.geohealth.models;

import java.io.Serializable;

/**
 * Created by GeoHealth on 13/02/16.
 */
public class Location implements Serializable {
    private float lat;
    private float lon;

    public Location(float lat, float lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }
}
