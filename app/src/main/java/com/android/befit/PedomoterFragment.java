package com.android.befit;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PedomoterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PedomoterFragment extends Fragment implements SensorEventListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PedomoterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PedomoterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PedomoterFragment newInstance(String param1, String param2) {
        PedomoterFragment fragment = new PedomoterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pedomoter, container, false);
        return view;
    }
    ////////////////////////////////////////////////////////////////////////////


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        //all views initiated here
        stepDetectView = view.findViewById(R.id.stepDetectText);
        startStop = view.findViewById(R.id.button7);
        kcal = view.findViewById(R.id.textView30);
        km = view.findViewById(R.id.textView29);

        //views initiation done

        //checking for permission for activity
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) { //ask for permission
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }
        //permission request done

        //Detecting whether step detector is present or not
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
            isStepDetectorPresent = true;
        } else {
            Toast.makeText(getActivity(), "Sensor Not Present", Toast.LENGTH_SHORT).show();
            isStepDetectorPresent = false;
        }
        //Detection Complete

        sensorManager.unregisterListener(this, sensor);

        anim.setDuration(160); //You can manage the blinking time with this parameter
        anim.setStartOffset(120);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        stepDetectView.startAnimation(anim);

        startStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toggle == false) {
                    startListening();
                    runnable.run();
                    startStop.setText("Stop");
                    toggle = true;
                    anim.cancel();
                } else {
                    stopListening();
                    handler.removeCallbacks(runnable);
                    startStop.setText("Start");
                    toggle = false;
                    stepDetectView.startAnimation(anim);
                }
            }
        });

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                counting = Integer.parseInt(snapshot.child("Time").getValue(String.class));
                counter = counting;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Something Went Wrong!", Toast.LENGTH_LONG).show();
            }
        });

        //Runnable Thread for counter
        handler = new Handler();
        runnable = new Runnable() {

            @Override
            public void run() {
                counter++;
                DatabaseReference counting = database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Time");
                counting.setValue(String.valueOf(counter));
                handler.postDelayed(runnable, 1000);
            }
        };

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                firebaseStep = snapshot.child("Steps").getValue(String.class);
                firebaseKcal = snapshot.child("KCAL").getValue(String.class);
                firebaseStepInt = Integer.parseInt(firebaseStep);
                firebaseKcalBD = Double.parseDouble(firebaseKcal);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Something Went Wrong!", Toast.LENGTH_LONG).show();
            }
        });
    }

    TextView stepDetectView, kcal, km;
    int stepDetect = 0;
    int count = 0;
    double Kcal = 0;
    double KM = 0;
    double firebaseKcalBD = 0;
    SensorManager sensorManager;
    Sensor sensor;
    boolean isStepDetectorPresent;
    boolean toggle = false;
    Button startStop;
    DecimalFormat df = new DecimalFormat("0.0");
    Animation anim = new AlphaAnimation(0.0f, 1.0f);
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String firebaseStep, firebaseKcal;
    int firebaseStepInt = 0;
    int counter = 0;
    int counting=0;
    Runnable runnable;
    Handler handler;


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor == sensor) {
            stepDetect = (int) sensorEvent.values[0];
            count = count + 1;
            stepDetectView.setText(String.valueOf(count));
            Kcal = count * 0.05;
            KM = count * 0.0008;
            kcal.setText(String.valueOf(df.format(Kcal) + " kcal"));
            km.setText(String.valueOf(df.format(KM) + " km"));

            DatabaseReference myRef = database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Steps");
            myRef.setValue(String.valueOf(firebaseStepInt + 1));
            DatabaseReference kcal = database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("KCAL");
            kcal.setValue(String.valueOf(df.format((firebaseStepInt + 1) * 0.05)));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void onResume() {
        super.onResume();
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null && toggle == true) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            sensorManager.unregisterListener(this, sensor);
        }
    }

    public void onPause() {
        super.onPause();
        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null) {
            sensorManager.unregisterListener(this, sensor);
            handler.removeCallbacks(runnable);
        }
    }

    public void startListening() {
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stopListening() {
        sensorManager.unregisterListener(this, sensor);
        handler.removeCallbacks(runnable);
    }

}
