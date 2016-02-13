package tk.geohealth.geohealth;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import tk.geohealth.geohealth.app.App;
import tk.geohealth.geohealth.fragments.BeachFragment;
import tk.geohealth.geohealth.fragments.OutOfBeach;
import tk.geohealth.geohealth.models.Result;
import tk.geohealth.geohealth.models.SolarExposition;
import tk.geohealth.geohealth.network.MapService;

public class MainActivity extends AppCompatActivity implements LocationListener {
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute


    private LocationManager locationManager;
    private LocationListener locationListener;
    private SessionManager sessionManager;

    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            Toast.makeText(this, "Location Updates Activated", Toast.LENGTH_SHORT).show();
        }

        if(sessionManager.hasSolarExposition()) {
            SolarExposition solarExposition = sessionManager.getLastSolarExposition();
            if(solarExposition.getMinutesSinceExposureStart() > 60 * 12) {
                sessionManager.removeLastSolarExposition();
            }
        }

        displayAppropriateFragment();
    }

    private void displayAppropriateFragment() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(sessionManager.hasSolarExposition()) {
            fragmentTransaction.replace(R.id.containerView, new BeachFragment());
        } else {
            fragmentTransaction.replace(R.id.containerView, new OutOfBeach());
        }

        fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(final Location location) {
        String str = "Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude();
//        Toast.makeText(this, str, Toast.LENGTH_LONG).show();

        if( sessionManager.hasSolarExposition() ) {
            SolarExposition solarExposition = sessionManager.getLastSolarExposition();

            if(location.distanceTo(solarExposition.getInitialLocation()) > 300) {
                sessionManager.removeLastSolarExposition();
            }

        } else {

            MapService mapService = App.getMapService();
            Call<Result> call = mapService.isOnBeach(location.getLatitude(), location.getLongitude());
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Response<Result> response, Retrofit retrofit) {
                    if(response.body() != null) {
                        Result result = response.body();

                        SolarExposition solarExposition = new SolarExposition(location);
                        sessionManager.setLastSolarExposition(solarExposition);

                        displayAppropriateFragment();

                        Toast.makeText(getApplicationContext(), "Está na Praia: " + result.isOnBeach() + "; " + result.getTemperature() + "; " + result.getUv(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Erro ao testar tipo de local", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("falha", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Falha ao testar tipo de local", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
