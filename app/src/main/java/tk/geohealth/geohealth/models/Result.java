package tk.geohealth.geohealth.models;

/**
 * Created by GeoHealth on 13/02/16.
 */
public class Result {
    private boolean result;

    public Result(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
