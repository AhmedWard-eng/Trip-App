package com.example.Tripapp.ui.createAcount;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Tripapp.MainActivity;
import com.example.Tripapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity2 extends AppCompatActivity {
    Button btn_start;
    TextView textLog;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    ScrollView scroll;

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
        scroll= findViewById(R.id.scrollMain);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(intent);
//            scroll.setVisibility(View.INVISIBLE);
        }


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