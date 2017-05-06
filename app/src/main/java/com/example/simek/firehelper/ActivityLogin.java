package com.example.simek.firehelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.DataAll;
import com.example.Uporabnik;

import java.util.ArrayList;
import java.util.jar.Attributes;

/**
 * Created by Simek on 30. 04. 2017.
 */

public class ActivityLogin extends AppCompatActivity {

    DataAll da;
    ApplicationMy app;
    EditText upIme;
    EditText geslo;

    String uporabnisko;
    String passw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        app=(ApplicationMy) getApplication();
        upIme=(EditText) findViewById(R.id.editText2);
        geslo=(EditText)findViewById(R.id.editText3);

    }

    public void ClickPrijava(View view){
        uporabnisko=upIme.getText().toString();
        passw=geslo.getText().toString();



        Uporabnik potrdi=app.PreveriUporabnika(uporabnisko,passw);

        if(potrdi!=null)
        {
            app.nastaviUporabnika(potrdi);
            NaMenu();

        }
        else
        {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Napacno uporabniško ime ali geslo!");
            dlgAlert.setTitle("Napaka");

            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //dismiss the dialog
                        }
                    });
        }
    }

    public void NaMenu(){
        Intent i = new Intent(getBaseContext(), ActivityMenu.class);
        startActivity(i);
    }


}
