package tk.geohealth.geohealth.models;

import java.util.GregorianCalendar;

/**
 * Created by GeoHealth on 13/02/16.
 */
public class SolarCream {
    private GregorianCalendar data;

    public SolarCream() {
        data = new GregorianCalendar();
    }

    public GregorianCalendar getData() {
        return data;
    }

}
