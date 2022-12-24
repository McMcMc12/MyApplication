package com.example.myapplication;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.appsearch.StorageInfo;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivityItemFormBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.animation.Positioning;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class item_form extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    ActivityItemFormBinding binding;
    ActivityResultLauncher<String> selectpic;
    private EditText itmName, itmDescription, itmPrice;
    private Button submit;
    private ImageView img_btn;
    private Spinner spin_cat;
    private ProgressBar progressBar;
    private boolean mInitailized;
    private Uri newUri;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;

    FirebaseAuth dbAuth;
    FirebaseDatabase dbRoot;
    DatabaseReference dbref;
   // FirebaseStorage StorageReference;

    public item_form() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_form);

        binding = ActivityItemFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        dbref = FirebaseDatabase.getInstance().getReference("uploads");


        selectpic = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result)
                    {
                        binding.imgbtn.setImageURI(result);
                        if(!(result == null)) {
                            TextView textView = findViewById(R.id.textView);
                            textView.setVisibility(View.GONE);
                            newUri = result;
                        }
                    }
                }
        );


        itmName = findViewById(R.id.txt_item_name);
        itmDescription = findViewById(R.id.txtdes);
        itmPrice = findViewById(R.id.txtprice);
        submit = findViewById(R.id.btnsell);
        spin_cat = findViewById(R.id.spincat);
        progressBar = findViewById(R.id.progressBar4);
        img_btn = findViewById(R.id.imgbtn);
        dbAuth = FirebaseAuth.getInstance();

        submit.setOnClickListener(this);
        img_btn.setOnClickListener(this);

        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,
                R.array.catergories, android.R.layout.simple_dropdown_item_1line);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spin_cat.setAdapter(adapter);
        spin_cat.setOnItemSelectedListener(this);



    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnsell:
                sell();
                break;
            case R.id.imgbtn:
                selectpic.launch("image/*");
                break;

        }

        }

    private void sell() {
            String name = itmName.getText().toString().trim();
            String description = itmDescription.getText().toString().trim();
            String price = itmPrice.getText().toString().trim();
            String Category = spin_cat.getSelectedItem().toString();

            Uri pic = newUri;

            //error checks
            if(name.isEmpty()) {
                itmName.setError("Cannot be left empty");
                itmName.requestFocus();
                return;
            }
            if(description.isEmpty())   {
                itmDescription.setError("Cannot be empty");
                itmDescription.requestFocus();
                return;
            }
            if(price.isEmpty()) {
                itmPrice.setError("Cannot be left empty");
                itmPrice.requestFocus();
                return;
            }

            if(newUri != null  ) {

            }else{
                Toast.makeText(item_form.this,"No Image selected", Toast.LENGTH_SHORT).show();
                img_btn.requestFocus();
            }

            String user = dbAuth.getCurrentUser().toString().trim();
            progressBar.setVisibility(View.VISIBLE);



            DatabaseReference rootref = FirebaseDatabase.getInstance().getReference();
            DatabaseReference taskref = rootref.child("Inventory").push();
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                  + "." + getFileExtension(newUri));
            mUploadTask = fileReference.putFile(newUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                      @Override
                      public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                               handler.postDelayed(new Runnable() {
                                @Override
                                  public void run() {

                            }
                        }, 500);


                          inventory Inventory = new inventory(name, description, price, Category, user,
                                  taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                          DatabaseReference rootref = FirebaseDatabase.getInstance().getReference();
                          DatabaseReference taskref = rootref.child("Inventory").push();

                          taskref.setValue(Inventory).addOnCompleteListener(task -> {

                              if (task.isSuccessful()) {
                                  Toast.makeText(item_form.this,"Item is succesfully registered!", Toast.LENGTH_LONG).show();
                                  progressBar.setVisibility(View.GONE);
                                  startActivity(new Intent(item_form.this, Feed.class));

                              }else{
                                  Toast.makeText(item_form.this,"Fail to load", Toast.LENGTH_LONG).show();
                                  progressBar.setVisibility(View.GONE);
                              }
                          });


                    }
                });






        }





    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        String choice = parent.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}

