package com.example.Tripapp.ui.createAcount;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Tripapp.R;

public class MainActivity2 extends AppCompatActivity {
    Button btn_start;
    TextView textLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R
                .layout.activity_main2);


        btn_start=findViewById(R.id.btn_start);
        textLog=findViewById(R.id.text_log);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity2.this,SignUpActivity.class);
                startActivity(intent);

            }
        });
        textLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity2.this,LoginActivity.class);
                startActivity(intent);


            }
        });
    }
}