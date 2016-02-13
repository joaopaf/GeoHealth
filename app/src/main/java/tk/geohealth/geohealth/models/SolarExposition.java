package tk.geohealth.geohealth.models;

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

    public SolarExposition(Location location) {
        startDate = new GregorianCalendar();
        over = false;
        initialLocation = location;
        solarCreamList = new ArrayList<>();
    }

    public void close() {
        over = true;
        //Eliminar todos os alarms definidos
    }

    public void putCream() {
        solarCreamList.add(new SolarCream());
    }

    public SolarCream getLastCream() {
        if (solarCreamList.isEmpty())
            return null;
        return solarCreamList.get(solarCreamList.size() - 1);
    }

}
