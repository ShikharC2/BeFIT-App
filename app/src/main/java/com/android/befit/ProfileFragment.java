package com.android.befit;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    String pic;
    String enableOkButton;

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        //all view initiated here
        F_Name = view.findViewById(R.id.textView2);
        showUser = view.findViewById(R.id.editFootstep);
        showMail = view.findViewById(R.id.editTextTextEmailAddress);
        showPass = view.findViewById(R.id.editTextTextPassword);
        showFootstep = view.findViewById(R.id.footstepView);
        save = view.findViewById(R.id.button4);
        saveStatus = view.findViewById(R.id.button15);
        hideShow = view.findViewById(R.id.imageView17);
        ProfilePic = view.findViewById(R.id.cardView);
        DisplayPic = view.findViewById(R.id.displayPic);
        changePicText = view.findViewById(R.id.changePic);
        resetTime = view.findViewById(R.id.textView34);
        //views initiation done

        //Database reference to fetch data
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                avatar = snapshot.child("DP").getValue(String.class);
                Name = snapshot.child("FName").getValue(String.class);
                Mail = snapshot.child("Mail").getValue(String.class);
                Passcode = snapshot.child("Code").getValue(String.class);
                footstep = snapshot.child("Steps").getValue(String.class);
                time = snapshot.child("Time").getValue(String.class);
                F_Name.setText(Name);
                showUser.setText(Name);
                showMail.setText(Mail);
                showPass.setText(Passcode);
                showFootstep.setText(footstep);

                pic = avatar;
                //Display Picture preview

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

                //Display picture preview done!

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Something Went Wrong!", Toast.LENGTH_LONG).show();
            }
        });
        //Database reference done!

        //Updating profile
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = showUser.getText().toString();
                String mail = showMail.getText().toString();
                String password = showPass.getText().toString();

                //check that credentials are same or not!
                if (name.equals(Name) && mail.equals(Mail) && password.equals(Passcode)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Be Fit")
                            .setIcon(R.drawable.icon)
                            .setMessage("Entered Credentials are Already Saved!");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.create().show();
                } else {

                    //condition for name
                    if (TextUtils.isEmpty(name)) {
                        showUser.setError("Please Enter Your Name");
                        return;
                    }
                    if (name.length() > 13) {
                        showUser.setError("Please Enter Name Less Than 14 Characters");
                        return;
                    }
                    //condition for Email
                    if (TextUtils.isEmpty(mail)) {
                        showMail.setError("Please Enter Your Email");
                        return;
                    }
                    if (!mail.contains("@") || !mail.contains(".") || mail.contains(" ") || mail.length() <= 6) {
                        showMail.setError("Given Email Is Not Valid");
                        return;
                    }
                    //condition for password
                    if (TextUtils.isEmpty(password)) {
                        showPass.setError("Please Enter Your Password");
                        return;
                    }
                    if (password.length() < 6 && password.length() > 0) {
                        showPass.setError("Password Cannot be less Than 6 Characters");
                    } else {

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference email = database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Mail");
                        email.setValue(mail);
                        DatabaseReference fName = database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("FN");
                        fName.setValue(name);
                        DatabaseReference passCode = database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Code");
                        passCode.setValue(password);
                        Snackbar.make(view, "Profile Updated Successfully!", Snackbar.LENGTH_LONG).show();
                    }

                }
            }
        });


        //updating Status
        saveStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String FtStep = showFootstep.getText().toString();
                if (FtStep.equals(footstep)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Be Fit")
                            .setIcon(R.drawable.icon)
                            .setMessage("Entered FootSteps are Already Saved!");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.create().show();
                }
                else{
                    if (TextUtils.isEmpty(FtStep)){
                        showFootstep.setError("Please Enter The FootSteps");
                    }
                    else{
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference FooTStep = database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Steps");
                        FooTStep.setValue(FtStep);
                        DatabaseReference Calo = database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("KCAL");
                        Double FootInt = Double.parseDouble(FtStep);
                        Calo.setValue(String.valueOf(df.format(FootInt*0.05)));
                        Snackbar.make(view,"FootSteps Successfully Updated!",Snackbar.LENGTH_LONG).show();
                    }
                }

            }
        });

        resetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Be Fit")
                        .setIcon(R.drawable.icon)
                        .setMessage("Are you sure you want to reset your Time to 0?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference reset = database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Time");
                        reset.setValue("0");
                        Snackbar.make(view,"Your Time has been reset to 0", Snackbar.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        ProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProfileDialog();
            }
        });

        hideShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (showPass.getInputType() == 129) {
                    showPass.setInputType(InputType.TYPE_CLASS_TEXT);
                    hideShow.setBackgroundResource(R.drawable.ic_baseline_visibility_24);
                } else {
                    showPass.setInputType(129);
                    hideShow.setBackgroundResource(R.drawable.ic_baseline_visibility_off_24);
                }

            }
        });

    }

    TextView F_Name, changePicText, resetTime;
    ImageView DisplayPic;
    String Name, Mail, Passcode, avatar, footstep, time;
    EditText showMail, showUser, showPass, showFootstep;
    Button save, saveStatus;
    CardView ProfilePic;
    ImageView hideShow;
    DecimalFormat df = new DecimalFormat("0.0");

    public void openProfileDialog() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.profile_pic_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        //check box initialize
        ImageView check0 = dialog.findViewById(R.id.check0);
        ImageView check1 = dialog.findViewById(R.id.check1);
        ImageView check2 = dialog.findViewById(R.id.check2);
        ImageView check3 = dialog.findViewById(R.id.check3);
        ImageView check4 = dialog.findViewById(R.id.check4);
        ImageView check5 = dialog.findViewById(R.id.check5);
        ImageView check6 = dialog.findViewById(R.id.check6);
        ImageView check7 = dialog.findViewById(R.id.check7);
        ImageView check8 = dialog.findViewById(R.id.check8);
        ImageView check9 = dialog.findViewById(R.id.check9);
        ImageView check10 = dialog.findViewById(R.id.check10);
        ImageView check11 = dialog.findViewById(R.id.check11);
        ImageView check12 = dialog.findViewById(R.id.check12);
        ImageView check13 = dialog.findViewById(R.id.check13);
        ImageView check14 = dialog.findViewById(R.id.check14);
        //check box initialization done!

        //conditions for check visibility
        if (avatar.equals("0")) {
            check0.setVisibility(View.VISIBLE);
            enableOkButton = "0";
        }
        if (avatar.equals("1")) {
            check1.setVisibility(View.VISIBLE);
            enableOkButton = "1";
        }
        if (avatar.equals("2")) {
            check2.setVisibility(View.VISIBLE);
            enableOkButton = "2";
        }
        if (avatar.equals("3")) {
            check3.setVisibility(View.VISIBLE);
            enableOkButton = "3";
        }
        if (avatar.equals("4")) {
            check4.setVisibility(View.VISIBLE);
            enableOkButton = "4";
        }
        if (avatar.equals("5")) {
            check5.setVisibility(View.VISIBLE);
            enableOkButton = "5";
        }
        if (avatar.equals("6")) {
            check6.setVisibility(View.VISIBLE);
            enableOkButton = "6";
        }
        if (avatar.equals("7")) {
            check7.setVisibility(View.VISIBLE);
            enableOkButton = "7";
        }
        if (avatar.equals("8")) {
            check8.setVisibility(View.VISIBLE);
            enableOkButton = "8";
        }
        if (avatar.equals("9")) {
            check9.setVisibility(View.VISIBLE);
            enableOkButton = "9";
        }
        if (avatar.equals("10")) {
            check10.setVisibility(View.VISIBLE);
            enableOkButton = "10";
        }
        if (avatar.equals("11")) {
            check11.setVisibility(View.VISIBLE);
            enableOkButton = "11";
        }
        if (avatar.equals("12")) {
            check12.setVisibility(View.VISIBLE);
            enableOkButton = "12";
        }
        if (avatar.equals("13")) {
            check13.setVisibility(View.VISIBLE);
            enableOkButton = "13";
        }
        if (avatar.equals("14")) {
            check14.setVisibility(View.VISIBLE);
            enableOkButton = "14";
        }
        //conditions for check visibility done!


        //profile pic 0
        CardView pic0 = dialog.findViewById(R.id.cardView2);
        pic0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pic = "0";
                check0.setVisibility(View.VISIBLE);
                check1.setVisibility(View.INVISIBLE);
                check2.setVisibility(View.INVISIBLE);
                check3.setVisibility(View.INVISIBLE);
                check4.setVisibility(View.INVISIBLE);
                check5.setVisibility(View.INVISIBLE);
                check6.setVisibility(View.INVISIBLE);
                check7.setVisibility(View.INVISIBLE);
                check8.setVisibility(View.INVISIBLE);
                check9.setVisibility(View.INVISIBLE);
                check10.setVisibility(View.INVISIBLE);
                check11.setVisibility(View.INVISIBLE);
                check12.setVisibility(View.INVISIBLE);
                check13.setVisibility(View.INVISIBLE);
                check14.setVisibility(View.INVISIBLE);
            }
        });

        //profile pic 1
        CardView pic1 = dialog.findViewById(R.id.cardView5);
        pic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pic = "1";
                check0.setVisibility(View.INVISIBLE);
                check1.setVisibility(View.VISIBLE);
                check2.setVisibility(View.INVISIBLE);
                check3.setVisibility(View.INVISIBLE);
                check4.setVisibility(View.INVISIBLE);
                check5.setVisibility(View.INVISIBLE);
                check6.setVisibility(View.INVISIBLE);
                check7.setVisibility(View.INVISIBLE);
                check8.setVisibility(View.INVISIBLE);
                check9.setVisibility(View.INVISIBLE);
                check10.setVisibility(View.INVISIBLE);
                check11.setVisibility(View.INVISIBLE);
                check12.setVisibility(View.INVISIBLE);
                check13.setVisibility(View.INVISIBLE);
                check14.setVisibility(View.INVISIBLE);
            }
        });

        //profile pic 2
        CardView pic2 = dialog.findViewById(R.id.cardView7);
        pic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pic = "2";
                check0.setVisibility(View.INVISIBLE);
                check1.setVisibility(View.INVISIBLE);
                check2.setVisibility(View.VISIBLE);
                check3.setVisibility(View.INVISIBLE);
                check4.setVisibility(View.INVISIBLE);
                check5.setVisibility(View.INVISIBLE);
                check6.setVisibility(View.INVISIBLE);
                check7.setVisibility(View.INVISIBLE);
                check8.setVisibility(View.INVISIBLE);
                check9.setVisibility(View.INVISIBLE);
                check10.setVisibility(View.INVISIBLE);
                check11.setVisibility(View.INVISIBLE);
                check12.setVisibility(View.INVISIBLE);
                check13.setVisibility(View.INVISIBLE);
                check14.setVisibility(View.INVISIBLE);
            }
        });

        //profile pic 3
        CardView pic3 = dialog.findViewById(R.id.cardView6);
        pic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pic = "3";
                check0.setVisibility(View.INVISIBLE);
                check1.setVisibility(View.INVISIBLE);
                check2.setVisibility(View.INVISIBLE);
                check3.setVisibility(View.VISIBLE);
                check4.setVisibility(View.INVISIBLE);
                check5.setVisibility(View.INVISIBLE);
                check6.setVisibility(View.INVISIBLE);
                check7.setVisibility(View.INVISIBLE);
                check8.setVisibility(View.INVISIBLE);
                check9.setVisibility(View.INVISIBLE);
                check10.setVisibility(View.INVISIBLE);
                check11.setVisibility(View.INVISIBLE);
                check12.setVisibility(View.INVISIBLE);
                check13.setVisibility(View.INVISIBLE);
                check14.setVisibility(View.INVISIBLE);
            }
        });

        //profile pic 4
        CardView pic4 = dialog.findViewById(R.id.cardView8);
        pic4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pic = "4";
                check0.setVisibility(View.INVISIBLE);
                check1.setVisibility(View.INVISIBLE);
                check2.setVisibility(View.INVISIBLE);
                check3.setVisibility(View.INVISIBLE);
                check4.setVisibility(View.VISIBLE);
                check5.setVisibility(View.INVISIBLE);
                check6.setVisibility(View.INVISIBLE);
                check7.setVisibility(View.INVISIBLE);
                check8.setVisibility(View.INVISIBLE);
                check9.setVisibility(View.INVISIBLE);
                check10.setVisibility(View.INVISIBLE);
                check11.setVisibility(View.INVISIBLE);
                check12.setVisibility(View.INVISIBLE);
                check13.setVisibility(View.INVISIBLE);
                check14.setVisibility(View.INVISIBLE);
            }
        });

        //profile pic 5
        CardView pic5 = dialog.findViewById(R.id.cardView9);
        pic5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pic = "5";
                check0.setVisibility(View.INVISIBLE);
                check1.setVisibility(View.INVISIBLE);
                check2.setVisibility(View.INVISIBLE);
                check3.setVisibility(View.INVISIBLE);
                check4.setVisibility(View.INVISIBLE);
                check5.setVisibility(View.VISIBLE);
                check6.setVisibility(View.INVISIBLE);
                check7.setVisibility(View.INVISIBLE);
                check8.setVisibility(View.INVISIBLE);
                check9.setVisibility(View.INVISIBLE);
                check10.setVisibility(View.INVISIBLE);
                check11.setVisibility(View.INVISIBLE);
                check12.setVisibility(View.INVISIBLE);
                check13.setVisibility(View.INVISIBLE);
                check14.setVisibility(View.INVISIBLE);
            }
        });

        //profile pic 6
        CardView pic6 = dialog.findViewById(R.id.cardView10);
        pic6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pic = "6";
                check0.setVisibility(View.INVISIBLE);
                check1.setVisibility(View.INVISIBLE);
                check2.setVisibility(View.INVISIBLE);
                check3.setVisibility(View.INVISIBLE);
                check4.setVisibility(View.INVISIBLE);
                check5.setVisibility(View.INVISIBLE);
                check6.setVisibility(View.VISIBLE);
                check7.setVisibility(View.INVISIBLE);
                check8.setVisibility(View.INVISIBLE);
                check9.setVisibility(View.INVISIBLE);
                check10.setVisibility(View.INVISIBLE);
                check11.setVisibility(View.INVISIBLE);
                check12.setVisibility(View.INVISIBLE);
                check13.setVisibility(View.INVISIBLE);
                check14.setVisibility(View.INVISIBLE);
            }
        });

        //profile pic 7
        CardView pic7 = dialog.findViewById(R.id.cardView11);
        pic7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pic = "7";
                check0.setVisibility(View.INVISIBLE);
                check1.setVisibility(View.INVISIBLE);
                check2.setVisibility(View.INVISIBLE);
                check3.setVisibility(View.INVISIBLE);
                check4.setVisibility(View.INVISIBLE);
                check5.setVisibility(View.INVISIBLE);
                check6.setVisibility(View.INVISIBLE);
                check7.setVisibility(View.VISIBLE);
                check8.setVisibility(View.INVISIBLE);
                check9.setVisibility(View.INVISIBLE);
                check10.setVisibility(View.INVISIBLE);
                check11.setVisibility(View.INVISIBLE);
                check12.setVisibility(View.INVISIBLE);
                check13.setVisibility(View.INVISIBLE);
                check14.setVisibility(View.INVISIBLE);
            }
        });

        //profile pic 8
        CardView pic8 = dialog.findViewById(R.id.cardView13);
        pic8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pic = "8";
                check0.setVisibility(View.INVISIBLE);
                check1.setVisibility(View.INVISIBLE);
                check2.setVisibility(View.INVISIBLE);
                check3.setVisibility(View.INVISIBLE);
                check4.setVisibility(View.INVISIBLE);
                check5.setVisibility(View.INVISIBLE);
                check6.setVisibility(View.INVISIBLE);
                check7.setVisibility(View.INVISIBLE);
                check8.setVisibility(View.VISIBLE);
                check9.setVisibility(View.INVISIBLE);
                check10.setVisibility(View.INVISIBLE);
                check11.setVisibility(View.INVISIBLE);
                check12.setVisibility(View.INVISIBLE);
                check13.setVisibility(View.INVISIBLE);
                check14.setVisibility(View.INVISIBLE);
            }
        });

        //profile pic 9
        CardView pic9 = dialog.findViewById(R.id.cardView12);
        pic9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pic = "9";
                check0.setVisibility(View.INVISIBLE);
                check1.setVisibility(View.INVISIBLE);
                check2.setVisibility(View.INVISIBLE);
                check3.setVisibility(View.INVISIBLE);
                check4.setVisibility(View.INVISIBLE);
                check5.setVisibility(View.INVISIBLE);
                check6.setVisibility(View.INVISIBLE);
                check7.setVisibility(View.INVISIBLE);
                check8.setVisibility(View.INVISIBLE);
                check9.setVisibility(View.VISIBLE);
                check10.setVisibility(View.INVISIBLE);
                check11.setVisibility(View.INVISIBLE);
                check12.setVisibility(View.INVISIBLE);
                check13.setVisibility(View.INVISIBLE);
                check14.setVisibility(View.INVISIBLE);

            }
        });

        //profile pic 10
        CardView pic10 = dialog.findViewById(R.id.cardView14);
        pic10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pic = "10";
                check0.setVisibility(View.INVISIBLE);
                check1.setVisibility(View.INVISIBLE);
                check2.setVisibility(View.INVISIBLE);
                check3.setVisibility(View.INVISIBLE);
                check4.setVisibility(View.INVISIBLE);
                check5.setVisibility(View.INVISIBLE);
                check6.setVisibility(View.INVISIBLE);
                check7.setVisibility(View.INVISIBLE);
                check8.setVisibility(View.INVISIBLE);
                check9.setVisibility(View.INVISIBLE);
                check10.setVisibility(View.VISIBLE);
                check11.setVisibility(View.INVISIBLE);
                check12.setVisibility(View.INVISIBLE);
                check13.setVisibility(View.INVISIBLE);
                check14.setVisibility(View.INVISIBLE);
            }
        });

        //profile pic 11
        CardView pic11 = dialog.findViewById(R.id.cardView15);
        pic11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pic = "11";
                check0.setVisibility(View.INVISIBLE);
                check1.setVisibility(View.INVISIBLE);
                check2.setVisibility(View.INVISIBLE);
                check3.setVisibility(View.INVISIBLE);
                check4.setVisibility(View.INVISIBLE);
                check5.setVisibility(View.INVISIBLE);
                check6.setVisibility(View.INVISIBLE);
                check7.setVisibility(View.INVISIBLE);
                check8.setVisibility(View.INVISIBLE);
                check9.setVisibility(View.INVISIBLE);
                check10.setVisibility(View.INVISIBLE);
                check11.setVisibility(View.VISIBLE);
                check12.setVisibility(View.INVISIBLE);
                check13.setVisibility(View.INVISIBLE);
                check14.setVisibility(View.INVISIBLE);
            }
        });

        //profile pic 12
        CardView pic12 = dialog.findViewById(R.id.cardView18);
        pic12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pic = "12";
                check0.setVisibility(View.INVISIBLE);
                check1.setVisibility(View.INVISIBLE);
                check2.setVisibility(View.INVISIBLE);
                check3.setVisibility(View.INVISIBLE);
                check4.setVisibility(View.INVISIBLE);
                check5.setVisibility(View.INVISIBLE);
                check6.setVisibility(View.INVISIBLE);
                check7.setVisibility(View.INVISIBLE);
                check8.setVisibility(View.INVISIBLE);
                check9.setVisibility(View.INVISIBLE);
                check10.setVisibility(View.INVISIBLE);
                check11.setVisibility(View.INVISIBLE);
                check12.setVisibility(View.VISIBLE);
                check13.setVisibility(View.INVISIBLE);
                check14.setVisibility(View.INVISIBLE);
            }
        });

        //profile pic 13
        CardView pic13 = dialog.findViewById(R.id.cardView19);
        pic13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pic = "13";
                check0.setVisibility(View.INVISIBLE);
                check1.setVisibility(View.INVISIBLE);
                check2.setVisibility(View.INVISIBLE);
                check3.setVisibility(View.INVISIBLE);
                check4.setVisibility(View.INVISIBLE);
                check5.setVisibility(View.INVISIBLE);
                check6.setVisibility(View.INVISIBLE);
                check7.setVisibility(View.INVISIBLE);
                check8.setVisibility(View.INVISIBLE);
                check9.setVisibility(View.INVISIBLE);
                check10.setVisibility(View.INVISIBLE);
                check11.setVisibility(View.INVISIBLE);
                check12.setVisibility(View.INVISIBLE);
                check13.setVisibility(View.VISIBLE);
                check14.setVisibility(View.INVISIBLE);
            }
        });

        //profile pic 14
        CardView pic14 = dialog.findViewById(R.id.cardView20);
        pic14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pic = "14";
                check0.setVisibility(View.INVISIBLE);
                check1.setVisibility(View.INVISIBLE);
                check2.setVisibility(View.INVISIBLE);
                check3.setVisibility(View.INVISIBLE);
                check4.setVisibility(View.INVISIBLE);
                check5.setVisibility(View.INVISIBLE);
                check6.setVisibility(View.INVISIBLE);
                check7.setVisibility(View.INVISIBLE);
                check8.setVisibility(View.INVISIBLE);
                check9.setVisibility(View.INVISIBLE);
                check10.setVisibility(View.INVISIBLE);
                check11.setVisibility(View.INVISIBLE);
                check12.setVisibility(View.INVISIBLE);
                check13.setVisibility(View.INVISIBLE);
                check14.setVisibility(View.VISIBLE);
            }
        });

        Button ok = dialog.findViewById(R.id.button5);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("DP");
                myRef.setValue(pic);
                dialog.dismiss();
                Toast.makeText(getActivity(), "Profile Picture Changed Successfully!", Toast.LENGTH_SHORT).show();
            }
        });


        Button cancel = dialog.findViewById(R.id.button6);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}