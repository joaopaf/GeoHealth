package tk.geohealth.geohealth.app;

import android.app.Application;

import tk.geohealth.geohealth.network.MapService;
import tk.geohealth.geohealth.network.ServiceGenerator;

/**
 * Created by GeoHealth on 13/02/16.
 */
public class App extends Application {
    private static MapService mapService;

    @Override
    public void onCreate() {
        super.onCreate();

        mapService = ServiceGenerator.createService(MapService.class);
    }

    public static MapService getMapService() {
        return mapService;
    }
}
