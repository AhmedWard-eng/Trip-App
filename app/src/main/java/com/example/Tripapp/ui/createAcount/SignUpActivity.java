package com.example.Tripapp.ui.createAcount;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Tripapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class SignUpActivity extends AppCompatActivity {
  EditText edit_name,edit_email,edit_pass;
  Button btn_signUp;
    DatabaseReference databaseReference;
    TextView text_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edit_name=findViewById(R.id.edit_name);
        edit_email=findViewById(R.id.edit_email);
        edit_pass=findViewById(R.id.edit_pass_Signup);
        btn_signUp=findViewById(R.id.btn_signUp);

        text_Login=findViewById(R.id.text_login_activity_signUp);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");

       FirebaseAuth auth=FirebaseAuth.getInstance();



        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edit_name.getText().toString();
                String email = edit_email.getText().toString();
                String pass = edit_pass.getText().toString();
                if (name.equals("")) {
                    Toast.makeText(SignUpActivity.this, "Please Enter your Name", Toast.LENGTH_SHORT).show();

                } else if (email.equals("")) {
                    Toast.makeText(SignUpActivity.this, "Please Enter your Email", Toast.LENGTH_SHORT).show();

                } else if (pass.equals("")) {
                    Toast.makeText(SignUpActivity.this, "Please Enter your Password", Toast.LENGTH_SHORT).show();

                } else {
                    auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                              String uid=auth.getCurrentUser().getUid();
                                HashMap<String,Object> map=new HashMap<>();
                                map.put("name",name);
                                map.put("email",email);
                                map.put("pass",pass);
                                map.put("uid",uid);
                                databaseReference.child(uid).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful())
                                            Toast.makeText(SignUpActivity.this, "Created Success", Toast.LENGTH_SHORT).show();
                                   Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                                   startActivity(intent);

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(
                                                SignUpActivity.this, ""+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }else {
                                Toast.makeText(
                                        SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }
            }





        });

text_Login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(
                SignUpActivity.this,LoginActivity.class);
        startActivity(intent);
    }
});
}

}