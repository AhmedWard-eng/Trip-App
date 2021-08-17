package com.example.Tripapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.Tripapp.ui.createAcount.MainActivity2;
import com.example.Tripapp.ui.home.UpcomingViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;

// commit 1 anything
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    Toolbar toolbar;
    FloatingActionButton fabStartTripDataActivity;
    UpcomingViewModel upcomingViewModel;

    Trip trip;

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fabStartTripDataActivity = findViewById(R.id.fab);
        fabStartTripDataActivity.setOnClickListener(view -> {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            Intent intent = new Intent(MainActivity.this, TripAppDataActivity.class);
            startActivity(intent);
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_history)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        MenuItem logOutItem = navigationView.getMenu().findItem(R.id.log_out);
        logOutItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.log_out) {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.signOut();
                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                    startActivity(intent);
                    finish();
                }


                return true;
            }
        });

      /* navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
           @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    if (item.getItemId()==R.id.log_out){
                    FirebaseAuth auth=FirebaseAuth.getInstance();
                    auth.signOut();
                    Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        }*/
        // );


    }

    private void addTrip(Trip trip,int position) {
        upcomingViewModel = new ViewModelProvider(this).get(UpcomingViewModel.class);
        upcomingViewModel.addTrip(trip);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_settings) {
            Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.item_entertainment) {
            return true;
        } else {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent != null) {
            trip = new Trip();
            String s = intent.getStringExtra(TripAppDataActivity.TRIP_UNIQUE_ID);
            Log.i("receiving_from_intent", String.valueOf(intent.getStringExtra(TripAppDataActivity.TRIP_UNIQUE_ID)));
            if (s != null) {
                trip.setTitle(intent.getStringExtra(TripAppDataActivity.TRIP_TITLE));
                trip.setTimeText(intent.getStringExtra(TripAppDataActivity.TRIP_TIME));
                trip.setDateText(intent.getStringExtra(TripAppDataActivity.TRIP_DATE));
                trip.setStartPoint(intent.getStringExtra(TripAppDataActivity.TRIP_START_POINT));
                trip.setEndPoint(intent.getStringExtra(TripAppDataActivity.TRIP_END_POINT));
//                trip.setTheSetTime((Calendar) intent.getSerializableExtra(TripAppDataActivity.TRIP_SET_TIME));
                int position = intent.getIntExtra(TripAppDataActivity.TRIP_POSITION,-1);
                addTrip(trip,position);
            }
        }
    }
}