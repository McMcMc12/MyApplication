package com.example.myapplication;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth dbAuth;
    private ImageView banner;
    private EditText txtName, txtPassword, txtPassword2, txtEmail, txtStudent;
    private ProgressBar progressBar;
    private Button cregister;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dbAuth = FirebaseAuth.getInstance();

        banner = (ImageView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        cregister = (Button) findViewById(R.id.button);
        cregister.setOnClickListener(this);


        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtName = (EditText) findViewById(R.id.txtName);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtPassword2 = (EditText) findViewById(R.id.txtPassword2);
        txtStudent = (EditText) findViewById(R.id.txtStudent);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.banner:
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.button:
                registerUser();
                close();
                break;

        }

    }

    private void close() {
        View view = this.getCurrentFocus();
        if(view != null)    {
            InputMethodManager imm =    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void registerUser() {
        String email = txtEmail.getText().toString().trim();
        String name = txtName.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        String password2 = txtPassword2.getText().toString().trim();
        String student = txtStudent.getText().toString().trim();

        if(name.isEmpty()){
            txtName.setError("Cannot empty here");
            txtName.requestFocus();
            return;
        }
        if(student.isEmpty()){
            txtStudent.setError("Cannot empty here");
            txtStudent.requestFocus();
            return;
        }

        if(password.isEmpty() || password.length() < 6 ){
            txtPassword.setError("Password cannot be empty or less than 6 characters");
            txtPassword.requestFocus();
            return;
        }

        if(email.isEmpty()){
            txtEmail.setError("Cannot empty here");
            txtEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            txtEmail.setError("this is not an email");
            txtEmail.requestFocus();
            return;
        }
        if(!password.equals(password2)){
            txtPassword2.setError("Password do not match");
            txtPassword.requestFocus();
            txtPassword2.requestFocus();
            return;
        }



        progressBar.setVisibility(View.VISIBLE);
        dbAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            User user = new User(name, email, student, password);

                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                Toast.makeText(register.this,"User is succesfully registered!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                                startActivity(new Intent(register.this, Login.class));

                                            }else{
                                                Toast.makeText(register.this,"Fail", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(register.this, "Fail!!!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}