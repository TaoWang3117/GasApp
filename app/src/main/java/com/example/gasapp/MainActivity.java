package com.example.gasapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button profileButton;
    private Button orderButton;
    private Button gasPriceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileButton = (Button) findViewById(R.id.profileBut);
        profileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openProfileActivity();
            }
        });

        orderButton = (Button) findViewById(R.id.orderBut);
        orderButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openOrderActivity();
            }
        });

        gasPriceButton = (Button) findViewById(R.id.gasBut);
        gasPriceButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openGasPriceActivity();
            }
        });

    }

    public void openProfileActivity(){
        startActivity(new Intent(getApplicationContext(), Profile.class));
    }

    public void openOrderActivity(){
        if(LoggedInUser.isEmployee()) {
            startActivity(new Intent(getApplicationContext(), Order.class));
        } else {
            startActivity(new Intent(getApplicationContext(), SubmitOrderActivity.class));
        }
    }

    public void openGasPriceActivity(){
        startActivity(new Intent(getApplicationContext(), GasPriceActivity.class));
    }

}
