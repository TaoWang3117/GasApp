package com.example.gasapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.gasapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class GasPriceSetDialog extends AppCompatDialogFragment {
    private EditText editTextGasPrice; //Refers to unleaded
    private EditText editTextMidgradePrice;
    private EditText editTextPremiumPrice;
    private EditText editTextDieselPrice;
    private GasPriceSetListener listener;


    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_gaspriceset, null);

        builder.setView(view)
                .setTitle("Set Gas Price")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
        })
                .setPositiveButton("ok", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String GasPrice = editTextGasPrice.getText().toString(); //unleaded
                        String MidGradePrice = editTextMidgradePrice.getText().toString();
                        String PremiumPrice = editTextPremiumPrice.getText().toString();
                        String DieselPrice = editTextDieselPrice.getText().toString();
                        listener.applyTexts(GasPrice, MidGradePrice, PremiumPrice, DieselPrice);

                        File file = new File(getContext().getExternalFilesDir("/app"), "gasprices.txt");
                        if(!file.exists()) {
                            try {
                                file.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            FileWriter fw = new FileWriter(file);
                            fw.write(GasPrice);
                            fw.write("\n");
                            fw.write(MidGradePrice);
                            fw.write("\n");
                            fw.write(PremiumPrice);
                            fw.write("\n");
                            fw.write(DieselPrice);
                            fw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                });

        editTextGasPrice = view.findViewById(R.id.edit_gasprice); //unleaded
        editTextMidgradePrice = view.findViewById(R.id.edit_midgradeprice);
        editTextPremiumPrice = view.findViewById(R.id.edit_premiumprice);
        editTextDieselPrice = view.findViewById(R.id.edit_dieselprice);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (GasPriceSetListener) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString() +
            "must implement GasPriceSetListener");
        }
    }

    public interface GasPriceSetListener{
        void applyTexts(String GasPrice, String MidgradePrice, String PremiumPrice, String DieselPrice); //add more strings here
    }
}
