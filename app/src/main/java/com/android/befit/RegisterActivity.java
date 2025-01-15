package com.android.befit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText name, email, password;
    Button register;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.pass);

        mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        register = findViewById(R.id.button);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = name.getText().toString();
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                String profile = "0";
                String steps = "0";
                String kcal = "0";
                String time = "0";

                //condition for full name
                if (TextUtils.isEmpty(fullName)) {
                    name.setError("Please Enter Your Name");
                    return;
                }
                if (fullName.length() > 13) {
                    name.setError("Please Enter Name Less Than 14 Characters");
                    return;
                }

                //Condition for Email Address.
                if (TextUtils.isEmpty(Email)) {
                    email.setError("Please Enter Your Email");
                    return;
                }
                if (!Email.contains("@") || !Email.contains(".") || Email.contains(" ") || Email.length() <= 6) {
                    email.setError("Given Email Is Not Valid");
                    return;
                }

                // condition for password

                if (TextUtils.isEmpty(Password)) {
                    password.setError("Please Enter Your Password");
                    return;
                }
                if (Password.length() < 6 && Password.length() > 0) {
                    password.setError("Password Cannot be less Than 6 Characters");
                }//condition ends here

                else {
                    progressDialog.setIcon(R.drawable.icon);
                    progressDialog.setTitle("Registration");
                    progressDialog.setMessage("Please Wait While Registration...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(Email, Password)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        users use = new users(fullName, Email, Password,profile,steps,kcal,time);
                                        FirebaseDatabase.getInstance().getReference("Users")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(use).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(RegisterActivity.this, "Registration and Login Successful", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            }
                                        });
                                    } else {
                                        progressDialog.dismiss();

                                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                        builder.setIcon(R.drawable.icon);
                                        builder.setTitle("Registration Failed");
                                        builder.setMessage("Something Went Wrong!");
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        });
                                        builder.create().show();
                                    }
                                }
                            });
                }

            }
        });//register click listener ends here
    }
}