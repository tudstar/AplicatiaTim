package com.example.cityapp.Activities.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
public EditText userMail,userPassword;
private Button butonLogin;
private ProgressBar loginProgress;
private FirebaseAuth mAuth;
private Intent HomeActivity;
private ImageView loginPhoto;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userMail = findViewById(R.id.login_mail);
        userPassword =findViewById(R.id.login_password);
        butonLogin = findViewById(R.id.buttonLogin);
        loginProgress = findViewById(R.id.loginProgress);
        mAuth = FirebaseAuth.getInstance();
        HomeActivity = new Intent(this,com.example.cityapp.Activities.Activities.HomeActivity.class);
        loginPhoto = findViewById(R.id.loginPhoto);
        loginPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerActivity = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(registerActivity);
                finish();
            }
        });

        loginProgress.setVisibility(View.INVISIBLE);
        butonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginProgress.setVisibility(View.VISIBLE);
                butonLogin.setVisibility(View.INVISIBLE);

                final String mail = userMail.getText().toString();
                final  String password= userPassword.getText().toString();
                if (mail.isEmpty() || password.isEmpty()) {
                    showMessage("Te rugam sa completezi  toate campurile");
                    butonLogin.setVisibility(View.VISIBLE);
                    loginProgress.setVisibility(View.INVISIBLE);
                }
                else
                {
                    signIn(mail,password);
                }



            }
        });

    }

    private void signIn(String mail, String password) {

        mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {
                    loginProgress.setVisibility(View.INVISIBLE);
                    butonLogin.setVisibility(View.VISIBLE);
                    updateUI();

                }

                else{
                    butonLogin.setVisibility(View.VISIBLE);
                loginProgress.setVisibility(View.INVISIBLE);
                    showMessage(task.getException().getMessage());}

            }
        });


    }

    private void updateUI() {

        startActivity(HomeActivity);
        finish();


    }

    private void showMessage(String text) {

        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onStart() {
       super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        if ((user !=null)) {
            //user is connected already soredirect user to HomePage

            updateUI();
        }




    }
}
