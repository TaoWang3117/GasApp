package com.example.gasapp;

/*
 * Created by: Adam Basham
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

public class LoginActivity extends AppCompatActivity {
    EditText emailField;
    EditText passwordField;
    Button loginButton;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailField = findViewById(R.id.emailLogin);
        passwordField = findViewById(R.id.passwordLogin);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButtonLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean userFound = false;
                if(validateData()) {
                    File file = new File(getExternalFilesDir("/app"), "users.json");
                    if(!file.exists()) {
                        toast("User does not exist!");
                        emailField.setError("User does not exist");
                        return;
                    }

                    JSONObject usersJson = null;
                    try {
                        usersJson = new JSONObject(JSONUtilities.getStringFromFile(file));
                        JSONArray usersArray = usersJson.getJSONArray("users");

                        //Iterate through registered users array,
                        for(int i = 0; i < usersArray.length(); i++) {
                            JSONObject obj = (JSONObject) usersArray.get(i);

                            //If user exists
                            if(obj.getString("email").equals(emailField.getText().toString())) {
                                userFound = true;
                                if(!obj.getString("password").equals(passwordField.getText().toString())) {
                                    toast("Incorrect Password!");
                                    passwordField.setError("Incorrect password");
                                    return;
                                }

                                //"Log" user in and go to main activity
                                LoggedInUser.setFirstName(obj.getString("firstName"), getApplicationContext(), false);
                                LoggedInUser.setLastName(obj.getString("lastName"), getApplicationContext(), false);
                                LoggedInUser.setEmailAddress(emailField.getText().toString(), getApplicationContext(), false);
                                LoggedInUser.setPostalAddress(obj.getString("address"), getApplicationContext(), false);
                                LoggedInUser.setPhoneNumber(obj.getString("phone"), getApplicationContext(), false);
                                LoggedInUser.setIsEmployee(obj.getBoolean("isEmployee"), getApplicationContext(), false);
                                LoggedInUser.setPassword(passwordField.getText().toString(), getApplicationContext(), false);
                                toast("Welcome " + LoggedInUser.getFirstName());

                                //Start activity, clearing login activity from Activity stack
                                //This is so the back button doesn't go from main screen back to login screen after login
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }

                        if(!userFound) {
                            toast("User does not exist!");
                            emailField.setError("User does not exist");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    //Validate user login by checking local JSON file of users registered
    private boolean validateData() {
        if(emailField.getText().toString().isEmpty()) {
            toast("You must enter an email!");
            emailField.setError("Email required");
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailField.getText().toString()).matches()) {
            toast("You must enter a valid email!");
            emailField.setError("Valid email required");
            return false;
        }

        if(passwordField.getText().toString().isEmpty()) {
            toast("You must enter a password!");
            passwordField.setError("Password required");
            return false;
        }

        if(passwordField.getText().toString().length() < 4) {
            toast("Password must be at least 4 characters long!");
            passwordField.setError("Password too short");
            return false;
        }

        return true;
    }

    //Helper function - displays UI toast with message
    private void toast(String message) {
        Toast t = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
        t.show();
    }
}