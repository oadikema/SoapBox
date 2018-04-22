package com.example.tapiwa.soapbox;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText mPasswordEditTxt, mEmailEditText;
    private Button loginBtn;
    private String TAG = "LOGIN PAGE";
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mPasswordEditTxt = findViewById(R.id.pwd_txt);
        mEmailEditText = findViewById(R.id.em_txt);
        loginBtn = findViewById(R.id.login_btn);
        progress =  new ProgressDialog(this);


        loginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }


    private void login() {

        progress.setMessage("Creating account");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

        if(checkValidity()) {
            mAuth.createUserWithEmailAndPassword(mEmailEditText.getText().toString(), mPasswordEditTxt.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                progress.setMessage("Account creation successful");
                                Intent openFrontPage = new Intent(LoginActivity.this, WallActivity.class);
                                startActivity(openFrontPage);
                                LoginActivity.this.finish();
                                progress.dismiss();

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private boolean checkValidity() {

        if(mPasswordEditTxt.getText().toString().length() == 0) {
            mPasswordEditTxt.setError("Invalid password");
            return false;
        }

        if(mEmailEditText.getText().toString().length() == 0) {
            mEmailEditText.setError("Invalid email");
            return false;
        }


        return true;
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            Intent openFrontPage = new Intent(LoginActivity.this, WallActivity.class);
            startActivity(openFrontPage);
        }
    }



}

