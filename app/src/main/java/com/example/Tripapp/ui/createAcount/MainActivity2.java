package com.example.Tripapp.ui.createAcount;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Tripapp.MainActivity;
import com.example.Tripapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity2 extends AppCompatActivity {
    Button btn_start;
    TextView textLog;
//    FirebaseAuth auth;
//    FirebaseUser firebaseUser;

//    public static DatabaseReference databaseRefUsers, USER_ID, databaseRefUpcoming, databaseRefHistory;
//    public static String userId;
//
//    public static final String upcomingId = "UpComing_Trips", historyId = "History_Trips";

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
        setContentView(R.layout.activity_main2);
//        scroll = findViewById(R.id.scrollMain);

//        auth = FirebaseAuth.getInstance();
//        firebaseUser = auth.getCurrentUser();
//
//        if (firebaseUser != null) {
//
//            scroll.setVisibility(View.INVISIBLE);
//            userId = firebaseUser.getUid();
//            databaseRefUsers = FirebaseDatabase.getInstance().getReference("Clients");
//            USER_ID = databaseRefUsers.child(userId);
//            databaseRefUsers.keepSynced(true);
//            databaseRefUpcoming = USER_ID.child(upcomingId);
//            databaseRefHistory = USER_ID.child(historyId);
//            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
//            startActivity(intent);
//
//        }


        btn_start = findViewById(R.id.btn_start);
        textLog = findViewById(R.id.text_log);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, SignUpActivity.class);
                startActivity(intent);

            }
        });
        textLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, LoginActivity.class);
                startActivity(intent);


            }
        });
    }
}