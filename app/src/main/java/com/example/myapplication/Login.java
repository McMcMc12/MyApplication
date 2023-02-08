package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private TextView register,forgot;
    private Button login;
    private EditText txtemail, txtpassword;
    private ProgressBar progressBar;
    private FirebaseAuth dbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();

        txtemail = (EditText)findViewById(R.id.idMatric);
        txtpassword = (EditText)findViewById(R.id.idPassword);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        forgot = (TextView)  findViewById(R.id.forgot);
        forgot.setOnClickListener(this);

        progressBar = (ProgressBar)findViewById(R.id.progressBar2);

       login = (Button) findViewById(R.id.btnLogin);
       login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                startActivity(new Intent(this, register.class));
                break;
            case R.id.btnLogin:
                closekeyboard();
                userLogin();
                break;

            case R.id.forgot:
                startActivity(new Intent(Login.this, Forgotpassword.class));
                break;

        }

    }

    private void userLogin(){
        String email = txtemail.getText().toString().trim();
        String password = txtpassword.getText().toString().trim();

        if(email.isEmpty()) {
            txtemail.setError("Cannot be left empty");
            txtemail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            txtemail.setError("This is not an email");
            txtemail.requestFocus();
            return;
        }
            if (password.isEmpty()) {
                txtpassword.setError("Password is not filled");
                txtpassword.requestFocus();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            dbAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(Login.this, Feed.class));
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Login.this, "FAILED TO LOGIN", Toast.LENGTH_LONG).show();
                    }
                }});


    }

    public void closekeyboard() {
        View view = this.getCurrentFocus();
        if  (view != null)  {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }
    }
}