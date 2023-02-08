package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgotpassword extends AppCompatActivity {

    private EditText femail;
    private Button button;
    private ProgressBar bar;
    private ImageView back;

    FirebaseAuth dbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        dbAuth = FirebaseAuth.getInstance();
        back = (ImageView) findViewById(R.id.imageView2);
        femail = (EditText) findViewById(R.id.txt_femail);
        button = (Button) findViewById(R.id.btn_reset);
        bar = (ProgressBar) findViewById(R.id.progressBar3);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Forgotpassword.this, Login.class));
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                closekeyboard();
            }
        });
    }

    public void closekeyboard() {
        View view = this.getCurrentFocus();
        if  (view != null)  {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }
    }

    private void reset() {
        String email = femail.getText().toString().trim();
        if(email.isEmpty()) {
            femail.setError("Cannot be empty");
            femail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            femail.setError("Enter a valid email");
            femail.requestFocus();
            return;
        }

        bar.setVisibility(View.VISIBLE);
        dbAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Forgotpassword.this, "Reset Passowrd link has been sent to that your email.", Toast.LENGTH_LONG).show();
                    bar.setVisibility(View.GONE);
                    startActivity(new Intent(Forgotpassword.this, Login.class));
                }else{
                    Toast.makeText(Forgotpassword.this, "Try Again, Something went wrong", Toast.LENGTH_LONG).show();
                }
            }
        }

        );


    }
}