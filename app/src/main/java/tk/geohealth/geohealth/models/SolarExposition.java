package tk.geohealth.geohealth.models;

import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by GeoHealth on 13/02/16.
 */
public class SolarExposition  implements Serializable {
    private GregorianCalendar startDate;
    private boolean over;
    private Location initialLocation;
    private List<SolarCream> solarCreamList;

    private float uv;
    private float temperature;
    private GregorianCalendar lastModification;

    public SolarExposition(Location location, float temperature, float uv) {
        this.startDate = new GregorianCalendar();
        this.over = false;
        this.initialLocation = location;
        this.solarCreamList = new ArrayList<>();
        this.temperature = temperature;
        this.uv = uv;
    }

    public void close() {
        this.over = true;
        //Eliminar todos os alarms definidos
    }

    public void putCream() {
        solarCreamList.add(new SolarCream());
    }

    public SolarCream getLastCream() {
        if (this.solarCreamList.isEmpty())
            return null;
        return this.solarCreamList.get(this.solarCreamList.size() - 1);
    }

    public int getMinutesSinceExposureStart() {
        GregorianCalendar now = new GregorianCalendar();
        long millis = now.getTimeInMillis() - this.startDate.getTimeInMillis();

        return (int) ((millis / 1000) / 60);
    }

    public int getMinutesSinceLastSolarCream() {
        GregorianCalendar now = new GregorianCalendar();
        long millis = now.getTimeInMillis() - this.getLastCream().getData().getTimeInMillis();

        return (int) ((millis / 1000) / 60);
    }

    public Location getInitialLocation() {
        return initialLocation;
    }

    public float getUv() {
        return uv;
    }

    public void setUv(float uv) {
        this.uv = uv;
        this.lastModification = new GregorianCalendar();
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
        this.lastModification = new GregorianCalendar();
    }

    public GregorianCalendar getLastModification() {
        return lastModification;
    }
}
