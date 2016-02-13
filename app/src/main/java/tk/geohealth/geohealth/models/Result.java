package tk.geohealth.geohealth.models;

/**
 * Created by GeoHealth on 13/02/16.
 */
public class Result {
    private boolean onBeach;
    private float temperature;
    private float uv;




    public boolean isOnBeach() {
        return onBeach;
    }

    public void setOnBeach(boolean onBeach) {
        this.onBeach = onBeach;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getUv() {
        return uv;
    }

    public void setUv(float uv) {
        this.uv = uv;
    }
}
