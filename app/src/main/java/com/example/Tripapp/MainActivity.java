package com.example.Tripapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.Tripapp.services.FloatingWidgetService;
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

// commit 1 anything
public class MainActivity extends AppCompatActivity {

    public static Boolean drawOverAppsAllowed = false;
    private static final int DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE = 1222;
    private AppBarConfiguration mAppBarConfiguration;
    Toolbar toolbar;
    FloatingActionButton fabStartTripDataActivity;
    UpcomingViewModel upcomingViewModel;
//    private IntentFilter intentFilter;
//    RingReceiver receiver;


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
//        intentFilter = new IntentFilter("com.example.SendReceiver");
//        receiver = new RingReceiver();
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

        //Check if the application has draw over other apps permission or not?
        //This permission is by default available for API<23. But for API > 23
        //you have to ask for the permission in runtime.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE);
        } else {
            //If permission is granted start floating widget service
            drawOverAppsAllowed = true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DRAW_OVER_OTHER_APP_PERMISSION_REQUEST_CODE) {
            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK)
                //If permission granted start floating widget service
                drawOverAppsAllowed = true;
            else
                //Permission is not available then display toast
                Toast.makeText(this,
                        getResources().getString(R.string.draw_other_app_permission_denied),
                        Toast.LENGTH_SHORT).show();

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void addTrip(Trip trip) {
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
                trip.setMinute(intent.getIntExtra(TripAppDataActivity.MINUTE,0));
                trip.setHour(intent.getIntExtra(TripAppDataActivity.HOUR,0));
                trip.setYear(intent.getIntExtra(TripAppDataActivity.YEAR,0));
                trip.setMonth(intent.getIntExtra(TripAppDataActivity.MONTH,0));
                trip.setDay(intent.getIntExtra(TripAppDataActivity.DAY,0));
                trip.setLatitude(29.924526);
                trip.setLongitude(31.205753);
                trip.setTripId(intent.getIntExtra(TripAppDataActivity.TRIP_ID,0));
                addTrip(trip);
            }
        }
    }
}