package com.example.gasapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Order extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderlist);

        display();

    }

    public void display() {
        try {
            File order = new File(getExternalFilesDir("/app"), "order.txt");
            //String order1 = " 123ABC/ White/ 93/ 1234 Washinton Rd. Kokomo IN/ Nov 15th/ 12:00pm/ 12 Gallons\n";
            //String order2 = " 456DEF/ Black/ 95/ 2345 Washinton Rd. Kokomo IN/ Nov 15th/ 1:00pm/ 20 Gallons\n";
            if (!order.exists()) {
                order.createNewFile();
            }
            //FileOutputStream data = new FileOutputStream(order,true);
            //data.write(order1.getBytes());
            //data.write(order2.getBytes());
            Scanner input = new Scanner(order);

            String receiveString = "";
            TableLayout view = findViewById(R.id.Ordertaken);
            while (input.hasNextLine()) {
                receiveString = input.nextLine();
                String[] value = receiveString.split("/");
                TableRow row = new TableRow(this);
                LinearLayout display = new LinearLayout(this);
                display.setOrientation(LinearLayout.VERTICAL);
                TextView plate = new TextView(this);
                TextView color = new TextView(this);
                TextView fuel = new TextView(this);
                TextView address = new TextView(this);
                TextView date = new TextView(this);
                TextView time = new TextView(this);
                TextView amount = new TextView(this);
                Button taken = new Button(this);
                plate.setText("Plate #:" + value[0]);
                color.setText("Car Color:" + value[1]);
                fuel.setText("Fuel Type" + value[2]);
                address.setText("Address:" + value[3]);
                date.setText("Date:" + value[4]);
                time.setText("Time:" + value[5]);
                amount.setText("Amount:" + value[6]);
                taken.setText("Take this Order");
                plate.setTextColor(Color.parseColor("#030303"));
                color.setTextColor(Color.parseColor("#030303"));
                fuel.setTextColor(Color.parseColor("#030303"));
                address.setTextColor(Color.parseColor("#030303"));
                date.setTextColor(Color.parseColor("#030303"));
                time.setTextColor(Color.parseColor("#030303"));
                amount.setTextColor(Color.parseColor("#030303"));
                taken.setTextColor(Color.parseColor("#030303"));
                display.addView(plate);
                display.addView(color);
                display.addView(fuel);
                display.addView(address);
                display.addView(date);
                display.addView(time);
                display.addView(amount);
                display.addView(taken);
                row.addView(display);
                view.addView(row);
            }
            input.close();
            //data.close();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "File not found!", Toast.LENGTH_SHORT).show();
        }
    }
}
