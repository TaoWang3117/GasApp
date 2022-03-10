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
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText firstNameField;
    EditText lastNameField;
    EditText emailField;
    EditText addressField;
    EditText passwordField;
    EditText phoneNumberField;
    Switch employeeSwitch;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstNameField = findViewById(R.id.firstNameRegister);
        lastNameField = findViewById(R.id.lastNameRegister);
        emailField = findViewById(R.id.emailRegister);
        addressField = findViewById(R.id.addressRegister);
        passwordField = findViewById(R.id.passwordRegister);
        phoneNumberField = findViewById(R.id.phoneNumber);
        employeeSwitch = findViewById(R.id.employeeRegister);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateData()) {
                    try {
                        File file = new File(getExternalFilesDir("/app"), "users.json");
                        if(!file.exists()) {
                            file.createNewFile();

                            JSONObject obj = new JSONObject();
                            obj.put("firstName", firstNameField.getText().toString());
                            obj.put("lastName", lastNameField.getText().toString());
                            obj.put("email", emailField.getText().toString());
                            obj.put("address", addressField.getText().toString());
                            obj.put("password", passwordField.getText().toString());
                            obj.put("phone", phoneNumberField.getText().toString());
                            obj.put("isEmployee", employeeSwitch.isChecked());

                            JSONArray jsonArray = new JSONArray();
                            jsonArray.put(obj);

                            JSONObject obj2 = new JSONObject();
                            obj2.put("users", jsonArray);

                            JSONUtilities.writeJsonFile(file, obj2.toString());
                        } else {
                            JSONObject previousUsersJson = new JSONObject(JSONUtilities.getStringFromFile(file));
                            JSONArray usersArray = previousUsersJson.getJSONArray("users");

                            //Sketchy way to check if email address already registered
                            if(usersArray.toString().contains(emailField.getText().toString())) {
                                toast("Email address already registered!");
                                emailField.setError("Email already registered");
                                return;
                            }

                            //Create user JSON object
                            JSONObject obj = new JSONObject();
                            obj.put("firstName", firstNameField.getText().toString());
                            obj.put("lastName", lastNameField.getText().toString());
                            obj.put("email", emailField.getText().toString());
                            obj.put("address", addressField.getText().toString());
                            obj.put("password", passwordField.getText().toString());
                            obj.put("phone", phoneNumberField.getText().toString());
                            obj.put("isEmployee", employeeSwitch.isChecked());

                            //Append object to users JSON array
                            usersArray.put(obj);

                            //Replace old json file with new json file with added user
                            JSONObject newUsersJson = new JSONObject();
                            newUsersJson.put("users", usersArray);
                            JSONUtilities.writeJsonFile(file, newUsersJson.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //At this point, user is registered so move on
                    //This is a scuffed object for the logged in user.
                    LoggedInUser.setFirstName(firstNameField.getText().toString(), getApplicationContext(), false);
                    LoggedInUser.setLastName(lastNameField.getText().toString(), getApplicationContext(), false);
                    LoggedInUser.setEmailAddress(emailField.getText().toString(), getApplicationContext(), false);
                    LoggedInUser.setPostalAddress(addressField.getText().toString(), getApplicationContext(), false);
                    LoggedInUser.setPassword(passwordField.getText().toString(), getApplicationContext(), false);
                    LoggedInUser.setPhoneNumber(phoneNumberField.getText().toString(), getApplicationContext(), false);
                    LoggedInUser.setIsEmployee(employeeSwitch.isChecked(), getApplicationContext(), false);
                    toast("Welcome " + LoggedInUser.getFirstName());

                    //Start activity, clearing register activity from Activity stack
                    //This is so the back button doesn't go from main screen back to register screen after login
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    //Basic input validation for registration form
    private boolean validateData() {
        if(isEmpty(firstNameField)) {
            toast("You must enter a first name!");
            firstNameField.setError("First name required");
            return false;
        }

        if(isEmpty(lastNameField)) {
            toast("You must enter a last name!");
            lastNameField.setError("Last name required");
            return false;
        }

        if(isEmpty(addressField)) {
            toast("You must enter an address!");
            addressField.setError("Address required");
            return false;
        }

        if(isEmpty(emailField)) {
            toast("You must enter an email!");
            emailField.setError("Email required");
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailField.getText().toString()).matches()) {
            toast("You must enter a valid email!");
            emailField.setError("Valid email required");
            return false;
        }

        if(isEmpty(passwordField)) {
            toast("You must enter a password!");
            passwordField.setError("Password required");
            return false;
        }

        if(passwordField.getText().toString().length() < 4) {
            toast("Password must be at least 4 characters long!");
            passwordField.setError("Password too short");
            return false;
        }

        if(isEmpty(phoneNumberField)) {
            toast("You must enter a phone number!");
            phoneNumberField.setError("Phone number required");
            return false;
        }

        //10 digit phone number pattern check
        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher matcher = pattern.matcher(phoneNumberField.getText().toString());

        if(!matcher.matches()) {
            toast("You must enter a valid 10 digit phone number!");
            phoneNumberField.setError("Invalid phone number");
            return false;
        }

        return true;
    }

    //Helper function
    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().isEmpty();
    }

    //Helper function - displays UI toast with message
    private void toast(String message) {
        Toast t = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
        t.show();
    }
}