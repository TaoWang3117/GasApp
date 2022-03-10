package com.example.gasapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;



public class Profile extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee);

        final EditText nameF = (EditText) findViewById(R.id.price1);
        final EditText nameL = (EditText) findViewById(R.id.price2);
        final EditText phoneView = (EditText) findViewById(R.id.price3);
        Button  update = (Button) findViewById(R.id.buttonUpdate);

        nameF.setText(LoggedInUser.getFirstName());
        nameL.setText(LoggedInUser.getLastName());
        phoneView.setText(LoggedInUser.getPhoneNumber());
        update.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                LoggedInUser.setFirstName(nameF.getText().toString(), getApplicationContext(), true);
                LoggedInUser.setLastName(nameL.getText().toString(), getApplicationContext(), true);
                LoggedInUser.setPhoneNumber(phoneView.getText().toString(), getApplicationContext(), true);
            }
        });
    }
}
