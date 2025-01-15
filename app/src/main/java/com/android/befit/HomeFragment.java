package com.android.befit;

import android.Manifest;
import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED) { //ask for permission
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 0);
        }


        //time and formatting
        LocalDateTime localtime = LocalDateTime.now();
        int time = localtime.getHour();
        //time and formatting ends

        //all views initiated here
        greetUser = view.findViewById(R.id.greetUser);
        showUser = view.findViewById(R.id.text_username);
        showUser.setText("");
        displayStep = view.findViewById(R.id.textView11);
        displayKcal = view.findViewById(R.id.textView19);
        displayTime = view.findViewById(R.id.textView23);
        dsplStep = view.findViewById(R.id.status_step);
        stepContinue = view.findViewById(R.id.button8);
        DisplayPic = view.findViewById(R.id.displayPic);
        changePicText = view.findViewById(R.id.changePic);
        changeProfile = view.findViewById(R.id.cardView);
        viewExercise = view.findViewById(R.id.button3);
        viewTips = view.findViewById(R.id.button2);
        //views initiation done

        Dialog dialog = new Dialog(getActivity(),android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
        dialog.setContentView(R.layout.progressdialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();


        //database reference to get username
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = snapshot.child("FName").getValue(String.class);
                step = snapshot.child("Steps").getValue(String.class);
                kcal = snapshot.child("KCAL").getValue(String.class);
                Time = snapshot.child("Time").getValue(String.class);
                avatar = snapshot.child("DP").getValue(String.class);
                timeSpent = Integer.parseInt(Time);
                showUser.setText("Hi, " + name);
                displayStep.setText(step+" Steps");
                displayKcal.setText(kcal+" kcal");
                dsplStep.setText(step);
                if (timeSpent<60){
                    displayTime.setText(timeSpent+" Sec");
                }
                else if (timeSpent>=60 && timeSpent<3600){
                    displayTime.setText(timeSpent/60+" Min");
                }
                else{
                    Double timing;
                    timing = Double.valueOf(timeSpent);
                    displayTime.setText(df.format(timing/3600)+" Hrs");
                }
                dialog.dismiss();

                if (avatar.equals("0")) {
                    DisplayPic.setImageResource(R.drawable.usericon);
                }
                if (avatar.equals("1")) {
                    DisplayPic.setImageResource(R.drawable.avatar_1);
                }
                if (avatar.equals("2")) {
                    DisplayPic.setImageResource(R.drawable.avatar_2);
                }
                if (avatar.equals("3")) {
                    DisplayPic.setImageResource(R.drawable.avatar_3);
                }
                if (avatar.equals("4")) {
                    DisplayPic.setImageResource(R.drawable.avatar_4);
                }
                if (avatar.equals("5")) {
                    DisplayPic.setImageResource(R.drawable.avatar_5);
                }
                if (avatar.equals("6")) {
                    DisplayPic.setImageResource(R.drawable.avatar_6);
                }
                if (avatar.equals("7")) {
                    DisplayPic.setImageResource(R.drawable.avatar_7);
                }
                if (avatar.equals("8")) {
                    DisplayPic.setImageResource(R.drawable.avatar_8);
                }
                if (avatar.equals("9")) {
                    DisplayPic.setImageResource(R.drawable.avatar_9);
                }
                if (avatar.equals("10")) {
                    DisplayPic.setImageResource(R.drawable.avatar_10);
                }
                if (avatar.equals("11")) {
                    DisplayPic.setImageResource(R.drawable.avatar_11);
                }
                if (avatar.equals("12")) {
                    DisplayPic.setImageResource(R.drawable.avatar_12);
                }
                if (avatar.equals("13")) {
                    DisplayPic.setImageResource(R.drawable.avatar_13);
                }
                if (avatar.equals("14")) {
                    DisplayPic.setImageResource(R.drawable.avatar_14);
                }

                if (!avatar.equals("0")) {
                    changePicText.setVisibility(View.INVISIBLE);
                } else {
                    changePicText.setVisibility(View.VISIBLE);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
                Toast.makeText(getActivity(), "Something Went Wrong!", Toast.LENGTH_LONG).show();
            }
        });
        //database reference done

        if (time >= 1 && time < 12) {
            greetUser.setText("Good Morning!");
        } else if (time >= 12 && time < 17) {
            greetUser.setText("Good AfterNoon!");
        } else {
            greetUser.setText("Good Evening!");
        }

        stepContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PedomoterFragment nextFrag= new PedomoterFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_cont, nextFrag)
                        .commit();
            }
        });

        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment frag = new ProfileFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_cont,frag).commit();
            }
        });

        viewExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExerciseFragment fragment = new ExerciseFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_cont,fragment).commit();
            }
        });
    }

    TextView greetUser, showUser, displayStep, displayKcal, displayTime, dsplStep, changePicText;
    String name, step, Time, kcal, avatar;
    ImageView DisplayPic;
    int timeSpent;
    DecimalFormat df = new DecimalFormat("0.0");
    Button stepContinue , viewExercise, viewTips;
    CardView changeProfile;


}