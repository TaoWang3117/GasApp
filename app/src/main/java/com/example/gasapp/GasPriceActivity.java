package com.example.gasapp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gasapp.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GasPriceActivity extends AppCompatActivity implements GasPriceSetDialog.GasPriceSetListener {

    public TextView textViewGasPrice; //unleaded
    public TextView textViewMidGrade;
    public TextView textViewPremium;
    public TextView textViewDiesel;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_price);

        textViewGasPrice = (TextView) findViewById(R.id.textview_gasprice); //gasprice here refers to unleaded
        textViewMidGrade = (TextView) findViewById(R.id.textview_midgrade);
        textViewPremium = (TextView) findViewById(R.id.textview_premium);
        textViewDiesel = (TextView) findViewById(R.id.textview_diesel);

        TextView unleadedTextView = (TextView)findViewById(R.id.UnleadedGasLabel); //The following is for darkening the labels to the left of gas prices
        TextView midgradeTextView = (TextView)findViewById(R.id.MidGradeGasLabel);
        TextView premiumTextView = (TextView)findViewById(R.id.PremiumGasLabel);
        TextView dieselTextView = (TextView)findViewById(R.id.DieselGasLabel);
        unleadedTextView.setTextColor(Color.BLACK);
        midgradeTextView.setTextColor(Color.BLACK);
        premiumTextView.setTextColor(Color.BLACK);
        dieselTextView.setTextColor(Color.BLACK);
        String[] price = new String[4];
        try{
            File file = new File(getExternalFilesDir("/app"), "gasprices.txt");
            if(!file.exists()) {
                  file.createNewFile();
            }
            Scanner input = new Scanner(file);

            int count = 0;
            while (input.hasNextLine()) {
                price[count] = input.nextLine();
                count++;
            }
            input.close();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "File not found!", Toast.LENGTH_SHORT).show();
        }

        applyTexts(price[0],price[1],price[2],price[3]);
        button = (Button) findViewById(R.id.button_gasprice);
        if(!LoggedInUser.isEmployee()) {
            button.setVisibility(View.GONE);
        }
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    public void openDialog(){
        GasPriceSetDialog gasPriceSetDialog = new GasPriceSetDialog();
        gasPriceSetDialog.show(getSupportFragmentManager(), "Gas Price Set");
    }

    @Override
    public void applyTexts(String GasPrice, String MidGradePrice, String PremiumPrice, String DieselPrice) { //GasPrice refers to unleaded
        textViewGasPrice.setText(GasPrice); //unleaded
        textViewMidGrade.setText(MidGradePrice);
        textViewPremium.setText(PremiumPrice);
        textViewDiesel.setText(DieselPrice);
    }

}
