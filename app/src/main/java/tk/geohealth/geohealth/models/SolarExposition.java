package tk.geohealth.geohealth.models;

import java.io.Serializable;
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


}
