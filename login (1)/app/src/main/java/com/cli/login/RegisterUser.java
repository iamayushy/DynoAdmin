package com.cli.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{

    private TextInputEditText username, useremail, userpasswd;
    private MaterialButton signup;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private TextView logintext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            goMain();
        }

        username = findViewById(R.id.username);
        useremail = findViewById(R.id.usermail);
        userpasswd = findViewById(R.id.userpasswd);
        signup = findViewById(R.id.signup);

        progressBar = findViewById(R.id.loader);
        logintext = findViewById(R.id.loginpagetext);
        logintext.setOnClickListener(this);


      signup.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              registerUser();
              progressBar.setVisibility(View.VISIBLE);
          }
      });





    }

    private void registerUser() {
        String userName = username.getText().toString().trim();
        String userMail = useremail.getText().toString().trim();
        String password = userpasswd.getText().toString().trim();
        closeme();

        if(userName.isEmpty()){
            username.setError("Everyone has a name");
            username.requestFocus();
            return;
        }
        if(userMail.isEmpty()){
            useremail.setError("Empty email does not exist");
            useremail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(userMail).matches()){
            useremail.setError("Get me a valid mail");
            useremail.requestFocus();
            return;
        }

        if(password.isEmpty() || password.length() < 8){
            userpasswd.setError("Everyone has a name");
            userpasswd.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(userMail, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            user user = new user(userName, userMail, password);

                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){

                                        Toast.makeText(RegisterUser.this, "Welcome To Dyno", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        goMain();

                                    }
                                    else{
                                        Snackbar.make(logintext, "User Exist", Snackbar.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            Snackbar.make(logintext, "User Exist", Snackbar.LENGTH_LONG).show();


                        }
                    }
                });

    }

    private void goMain() {
        startActivity(new Intent(RegisterUser.this, MainActivity.class));
        finish();
    }

    private void closeme() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager manager
                    = (InputMethodManager)
                    getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            manager
                    .hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
        }
    }



    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.loginpagetext){
                startActivity(new Intent(this, LoginUser.class));
                finish();
        }
    }
}