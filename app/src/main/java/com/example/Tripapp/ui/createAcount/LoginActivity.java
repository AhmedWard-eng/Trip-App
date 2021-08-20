package com.example.Tripapp.ui.createAcount;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.Tripapp.MainActivity;
import com.example.Tripapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import papaya.in.sendmail.SendMail;

public class LoginActivity extends AppCompatActivity {
    EditText edit_email, edit_pass;
    TextView text_signUp;
    Button btn_login;
    ImageView imageView;
    GoogleSignInClient mGoogleSignInAccount;
    public static int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            Intent intent = new Intent(LoginActivity.this,MainActivity2.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {

        Intent in1 = new Intent(LoginActivity.this, MainActivity2.class);
        startActivity(in1);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edit_email = findViewById(R.id.edt_login_email);
        edit_pass = findViewById(R.id.edt_login_password);
        btn_login = findViewById(R.id.btn_login_login);
        imageView = findViewById(R.id.img_login_google);
        text_signUp = findViewById(R.id.txt_login_signup);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();

        createRequest();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edit_email.getText().toString();
                String pass = edit_pass.getText().toString();
                if (email.equals("") && pass.equals("") || email.equals("") || pass.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please Complete Login Information", Toast.LENGTH_SHORT).show();
                }

                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (email.equals("") && pass.equals("")) {
                            Toast.makeText(LoginActivity.this, "Please Complete Login Information", Toast.LENGTH_SHORT).show();


                        } else if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            SendMail mail = new SendMail("salahamany076@gmail.com", "AS1572000",
                                    email,
                                    "Sign Up Successful in Trip App",
                                    "Yes, it's working well\nI will use it always.");
                            mail.execute();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }


        });

////                String email=edit_email.getText().toString();
////                String pass=edit_pass.getText().toString();
//                if (email.equals("") && pass.equals("") || email.equals("") ||pass.equals("") ) {
//                    Toast.makeText(LoginActivity.this, "Please Complete Login Information", Toast.LENGTH_SHORT).show();
//
//
//                }
//
//                auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (email.equals("") && pass.equals("")) {
//                            Toast.makeText(LoginActivity.this, "Please Complete Login Information", Toast.LENGTH_SHORT).show();
//
//
//                        }
//                        else if(task.isSuccessful()){
//                            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
//                            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
//                            startActivity(intent);
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                  }
//
//
//
//        });

        //////////////////////////


        String email = edit_email.getText().toString();
        String pass = edit_pass.getText().toString();
        if (email.equals("") && pass.equals("")) {
            Toast.makeText(LoginActivity.this, "Please Complete Login Information", Toast.LENGTH_SHORT).show();


        } else {
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(LoginActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }


        text_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createRequest(){

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInAccount = GoogleSignIn.getClient(this, gso);
    }

    private void signIn(){
        Intent signInIntent = mGoogleSignInAccount.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }}
    private void   handleSignInResult(Task < GoogleSignInAccount > completedTask){
        try {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
//            Toast.makeText(this,"firebaseAuthWithGoogle:" + account.getId(),Toast.LENGTH_SHORT).show();
            firebaseAuthWithGoogle(account);

        } catch (ApiException e) {
            // Google Sign In failed, update UI appropriately
            Toast.makeText(this,e.toString() ,Toast.LENGTH_SHORT).show();
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this,"signIn successful",Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this,"signInWithCredential:failure" ,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

