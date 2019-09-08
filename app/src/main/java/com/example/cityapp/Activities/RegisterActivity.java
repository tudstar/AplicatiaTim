package com.example.cityapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cityapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    ImageView ImgUserPhoto;
    static int PReqCode = 1;
    static int REQUESTCODE = 1;
    Uri pickedImgURI ;


    private EditText userEmail,userPassword,userPassword2,userName;
    private ProgressBar loadingProgress;
    private Button regButton;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //inu views

        userEmail = findViewById(R.id.regMail);
        userPassword = findViewById(R.id.regPassword);
        userPassword2 = findViewById(R.id.regPassword2);
        userName= findViewById(R.id.regName);
        loadingProgress = findViewById(R.id.regProcessBar);
        loadingProgress = setProgressBarVisibility(View.INVISIBLE);
        mAuth = FirebaseAuth.getInstance();
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

            regButton.setVisibility(View.INVISIBLE);
            loadingProgress.setVisibility(View.INVISIBLE);
            final String email = userEmail.getText().toString();
            final String password = userPassword.getText().toString();
            final String password2 = userPassword2.getText().toString();
            final String name = userName.getText().toString();


            if ( email.isEmpty() || name.isEmpty() || password.isEmpty()  || !password.equals(password2)) {



                showMessage("Va rugam completati toate campurile corect") ;
                regButton.setVisibility(View.VISIBLE);
                loadingProgress.setVisibility(View.INVISIBLE);




            }
            else {
                // toate campurile sunt obligatorii si putem incepe inregistrarea
                //metoda CreateUserAccount va trimite request sa se creeze un utilizator nou

                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()})

                            {
                                showMessage("Cont Creat");
                            }
                            else
                                {


                                showMessage("Eroare Creare Cont" + task.getException().getMessage());
                                regButton.setVisibility(View.VISIBLE);
                                loadingProgress.setVisibility(View.INVISIBLE);

                            }
                        })

            }










            }
        });

        ImgUserPhoto = findViewById(R.id.regUserPhoto);
        ImgUserPhoto.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                if (Build.VERSION.SDK_INT >= 22) {
                                                    ceckAndRequestForPermission();

                                                } else {
                                                    openGallery();
                                                }
                                            }
                                        }
        );
    }

    private void CreateUserAccount(String email, String name, String password) {

        //aceasta metoda creaza conntul utilizatorului nou cu email specific user si parola

mAuth.createUserWithEmailAndPassword(email,password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
            }
        })



    }

    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();


    }

    private ProgressBar setProgressBarVisibility(int invisible) {
    }

    private void openGallery() {
        //TODO: open gallery intent and wait for user to pick an image

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, REQUESTCODE);


    }

    private void ceckAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(RegisterActivity.this, "Please accept for required permission", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(RegisterActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);

            }
        } else {
            openGallery();

        }

    }
//hello
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESTCODE && data !=null) {
            //the user has succesfully picked a image
            //we need to save its reference to a variable
            pickedImgURI = data.getData() ;
            ImgUserPhoto.setImageURI(pickedImgURI);
        }

    }
}



//hello world, this is the image selector code

