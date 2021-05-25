
package com.example.animelist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.Arrays;

public class SignUpActivity extends AppCompatActivity {

    public static final String TAG = "SignUpActivity";
    private EditText etSignUpUsername;
    private EditText etSignUpPassword;
    private EditText etPasswordConfirm;
    private Button btnSignUp;
    private Button btnReturnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etSignUpUsername = findViewById(R.id.etSignUpUsername);
        etSignUpPassword = findViewById(R.id.etSignUpPassword);
        etPasswordConfirm = findViewById(R.id.etPasswordConfirm);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnReturnLogin = findViewById(R.id.btnReturnLogin);

        ParseUser user = new ParseUser();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick login button");
                String username = etSignUpUsername.getText().toString();
                String password = etSignUpPassword.getText().toString();
                String confirmPassword = etPasswordConfirm.getText().toString();

                if (password.equals(confirmPassword))
                {
                    user.setUsername(username);
                    user.setPassword(password);
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                ParseObject newUser = new ParseObject("subscribed");
                                newUser.put("subscribedAnimes", Arrays.asList(""));
                                newUser.put("user", user);
                                newUser.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        Toast.makeText(SignUpActivity.this, "Successful Sign Up! ", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                goLoginActivity();
                            } else {
                                ParseUser.logOut();
                                Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
                else{
                    Toast.makeText(SignUpActivity.this, "Passwords Aren't the Same!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReturnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick create account button");
                goLoginActivity();
            }
        });
    }

    private void goLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

}
