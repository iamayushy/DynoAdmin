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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginUser extends AppCompatActivity implements View.OnClickListener{

    private TextView registertext;
    private TextInputEditText loginmail, loginpassword;
    private MaterialButton signin;
    private ProgressBar loginbar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        registertext = findViewById(R.id.registertext);
        registertext.setOnClickListener(this);

        //User Data
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            goToMain();
        }
        loginmail = findViewById(R.id.loginmail);
        loginpassword = findViewById(R.id.loginpassword);
        signin = findViewById(R.id.signin);
        loginbar = findViewById(R.id.loaderlogin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkConnected()){
                loginuser();
                loginbar.setVisibility(View.VISIBLE);
                }
                else {
                    Snackbar.make(loginmail, "I need internet", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void loginuser() {

        String mail = loginmail.getText().toString().trim();
        String lpass = loginpassword.getText().toString().trim();
        closeme();
        if(mail.isEmpty()){
            loginmail.setError("Empty email does not exist");
            loginmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            loginmail.setError("Get me a valid mail");
            loginmail.requestFocus();
            return;
        }

        if(lpass.isEmpty() || lpass.length() < 8){
            loginpassword.setError("Everyone has a name");
            loginpassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(mail, lpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    goToMain();
                    loginbar.setVisibility(View.GONE);
                }
                else{
                    Snackbar.make(registertext, "User does not exist", Snackbar.LENGTH_SHORT).show();

                    loginbar.setVisibility(View.GONE);
                    registerUser();
                }
            }
        });
    }

    private void registerUser() {
        startActivity(new Intent(this, RegisterUser.class));
    }

    private void goToMain() {
        startActivity(new Intent(LoginUser.this, MainActivity.class));
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
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.registertext){
            startActivity(new Intent(this, RegisterUser.class));

        }
    }
}