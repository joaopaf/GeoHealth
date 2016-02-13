package tk.geohealth.geohealth;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import tk.geohealth.geohealth.models.SolarExposition;

/**
 * Created by GeoHealth on 13/02/16.
 */
public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    private static final int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "GeoHealthPerf";
    private static final String LAST_EXPOSITION = "LastSolarExposition";

    public SessionManager(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        this.editor = sharedPreferences.edit();
    }

    public SolarExposition getLastSolarExposition (){
        Gson gson = new Gson();
        String json = sharedPreferences.getString(LAST_EXPOSITION, null);
        SolarExposition obj = gson.fromJson(json, SolarExposition.class);

        return obj;
    }

    public void setLastSolarExposition (SolarExposition solarExposition){
        Gson gson = new Gson();
        String json = gson.toJson(solarExposition);
        editor.putString(LAST_EXPOSITION, json);
        editor.commit();
    }

    public boolean hasSolarExposition() {
        return this.sharedPreferences.contains(LAST_EXPOSITION);
    }

    public void removeLastSolarExposition() {
        this.editor.remove(LAST_EXPOSITION);
    }
}
