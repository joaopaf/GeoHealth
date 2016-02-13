package tk.geohealth.geohealth.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import tk.geohealth.geohealth.R;
import tk.geohealth.geohealth.SessionManager;
import tk.geohealth.geohealth.models.SolarExposition;

/**
 * A simple {@link Fragment} subclass.
 */
public class BeachFragment extends Fragment {

    private Timer timer;
    private SessionManager sessionManager;
    private View view;
    private Activity activity;

    public BeachFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        timer = new Timer();
        sessionManager = new SessionManager(getContext());

        view = inflater.inflate(R.layout.fragment_beach, container, false);
        startTimer();
        activity = getActivity();
        return view;
    }

    private void startTimer() {

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                timerMethod();
            }
        }, 0, 1000);
    }

    private void timerMethod() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(sessionManager.hasSolarExposition()) {

                    TextView timeExposition = (TextView) view.findViewById(R.id.timeExposition);
                    TextView timeSolar = (TextView) getView().findViewById(R.id.timeSolar);
                    TextView temperatura = (TextView) getView().findViewById(R.id.temperatura);
                    TextView indiceuv = (TextView) getView().findViewById(R.id.indiceuv);


                    SolarExposition solarExposition = sessionManager.getLastSolarExposition();
                    int minExposureStart = solarExposition.getMinutesSinceExposureStart();
//                    int minSinceCream = solarExposition.getMinutesSinceExposureStart();

                    int hoursExposureStart = minExposureStart / 60;
                    int minutesExposureStart = minExposureStart % 60;
                    String time = String.format("%02d:%02d", hoursExposureStart, minutesExposureStart);
                    timeExposition.setText(time);

                    int temp = Math.round(solarExposition.getTemperature());
                    temperatura.setText(temp + " ºC");

                    int uv = Math.round(solarExposition.getUv());
                    indiceuv.setText("UV " + uv);
                }
            }
        });
    }

}
