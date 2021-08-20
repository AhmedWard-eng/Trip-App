package com.example.Tripapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.Tripapp.ui.createAcount.MainActivity2;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
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

    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    public static DatabaseReference databaseRefUsers, USER_ID, databaseRefUpcoming, databaseRefHistory;
    public static String userId;

    public static final String upcomingId = "UpComing_Trips", historyId = "History_Trips";




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


        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        if (firebaseUser == null) {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
            finish();
        } else {
            userId = firebaseUser.getUid();
            databaseRefUsers = FirebaseDatabase.getInstance().getReference("Clients");
            USER_ID = databaseRefUsers.child(userId);
            databaseRefUsers.keepSynced(true);
            databaseRefUpcoming = USER_ID.child(upcomingId);
            databaseRefHistory = USER_ID.child(historyId);
        }



//        Toast.makeText(this, userId, Toast.LENGTH_LONG).show();

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

    }
}