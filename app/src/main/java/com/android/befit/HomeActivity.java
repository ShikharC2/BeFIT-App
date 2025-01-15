package com.android.befit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    //public void onBackPressed() {
        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.icon);
        builder.setMessage("Are You Sure You Want To Exit?");
        builder.setTitle("Be FIT");
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
        builder.create().show();*/

    //}

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_cont, new HomeFragment(), "HomeStack");
        transaction.commit();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.sidenav_home);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.sidenav_home:
                        fragmentManager.beginTransaction().replace(R.id.main_cont, new HomeFragment(), "HomeStack").commit();
                        drawerLayout.close();
                        break;
                    case R.id.sidenav_activity:
                        fragmentManager.beginTransaction().replace(R.id.main_cont, new ExerciseFragment()).commit();
                        drawerLayout.close();
                        break;
                    case R.id.sidenav_eye:
                        fragmentManager.beginTransaction().replace(R.id.main_cont, new EyeFragment()).commit();
                        drawerLayout.close();
                        break;
                    case R.id.sidenav_pedo:
                        fragmentManager.beginTransaction().replace(R.id.main_cont, new PedomoterFragment()).commit();
                        drawerLayout.close();
                        break;
                    case R.id.sidenav_signOut:
                        fragmentManager.beginTransaction().replace(R.id.main_cont, new HomeFragment(), "HomeStack").commit();
                        drawerLayout.close();
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                        builder.setIcon(R.drawable.icon)
                                .setTitle("Be FIT")
                                .setMessage("Are You Sure You Want To Log Out?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder.create().show();
                        break;
                    case R.id.sidenav_profile:
                        fragmentManager.beginTransaction().replace(R.id.main_cont, new ProfileFragment()).commit();
                        drawerLayout.close();
                        break;
                    case R.id.sidenav_about:
                        fragmentManager.beginTransaction().replace(R.id.main_cont, new AboutFragment()).commit();
                        drawerLayout.close();
                        break;
                    case R.id.sidenav_update:
                        fragmentManager.beginTransaction().replace(R.id.main_cont,new UpdateFragment()).commit();
                        drawerLayout.close();
                        break;
                }
                return true;
            }
        });
    }

    public void onBackPressed() {
        Fragment newFragment = getSupportFragmentManager().findFragmentByTag("HomeStack");
        if (newFragment != null && newFragment.isVisible()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.icon);
            builder.setMessage("Are You Sure You Want To Exit?");
            builder.setTitle("Be FIT");
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
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_cont, new HomeFragment(), "HomeStack").commit();
            navigationView.setCheckedItem(R.id.sidenav_home);
        }

    }

}