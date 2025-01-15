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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }


    TextView clkHere;
    EditText sign_mail,sign_pass;
    Button login;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Be FIT");
        builder.setIcon(R.drawable.icon);
        builder.setMessage("Are You Sure You Want To Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        clkHere = findViewById(R.id.textView4);
        sign_mail = findViewById(R.id.editTextTextEmailAddress2);
        sign_pass = findViewById(R.id.editTextTextPassword2);
        login = findViewById(R.id.log);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);


        clkHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });//register here click listener ends here

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = sign_mail.getText().toString();
                String Pass = sign_pass.getText().toString();
                //conditions of edittext
                if (TextUtils.isEmpty(mail)){
                    sign_mail.setError("Please Enter The Email");
                }
                if(TextUtils.isEmpty(Pass)){
                    sign_pass.setError("Please Enter The Password");
                }
                else {
                    progressDialog.setIcon(R.drawable.icon);
                    progressDialog.setTitle("Logging In");
                    progressDialog.setMessage("Please Wait While Login...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    mAuth.signInWithEmailAndPassword(mail, Pass)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    } else {
                                        progressDialog.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setIcon(R.drawable.icon);
                                    builder.setTitle("Login Failed");
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
        });

    }
}