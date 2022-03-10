package com.example.gasapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class SubmitOrderActivity extends AppCompatActivity {
    EditText color;
    EditText Address;
    EditText fuelType;
    EditText Plate;
    EditText Gallons;
    Date date;
    Button submitButton;

    public SubmitOrderActivity() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);


        //Values from text boxes
        final EditText Address = (EditText) findViewById(R.id.Address);
        final EditText color = (EditText)findViewById(R.id.Color);
        final EditText fuelType = (EditText)findViewById(R.id.FuelType);
        final EditText Plate = (EditText)findViewById(R.id.PlateNumber);
        final EditText Gallons = (EditText)findViewById(R.id.GallonsRequired);
        final EditText DateO = (EditText) findViewById(R.id.date);
        final EditText TimeO = (EditText)findViewById(R.id.time);

        Button submitButton = (Button) findViewById(R.id.CreateOrder);


        //button clicker
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validateData();
                String carColor = color.getText().toString();
                String Fuel = fuelType.getText().toString();
                String address1 = Address.getText().toString();
                String platenum = Plate.getText().toString();
                String dateO = DateO.getText().toString();
                String timeO = TimeO.getText().toString();
                String gal = Gallons.getText().toString() + " Gallons";
                try {
                    File order = new File(getExternalFilesDir("/app"), "order.txt");
                    String ord1 = " " + platenum + "/ " + carColor + "/ "  + Fuel + "/ "
                            + address1 + "/ " + dateO + "/ " + timeO + "/ " + gal + "\n";
                    if (!order.exists()) {
                        try {
                            order.createNewFile();
                        } catch (IOException e) {
                            toast("Couldn't create a new file!");
                        }
                    }
                    try {
                        FileOutputStream data1 = new FileOutputStream(order, true);
                        data1.write(ord1.getBytes());
                        data1.close();
                    } catch (IOException e) {
                        toast("could not add information to file");
                    }
                } catch (Exception e) {
                    toast("System failed. could not deliver/create order");
                }

                toast("Order submitted!");
                //startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    //validates if platenumber is empty or not(may try to change)
    private boolean validateData() {
        if (Plate.getText().toString().isEmpty()) {
            Plate.setError("Model cannot be empty!");
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