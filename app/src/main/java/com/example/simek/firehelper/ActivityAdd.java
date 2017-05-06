package com.example.simek.firehelper;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.example.DataAll;

import android.widget.TextView;
/**
 * Created by Simek on 11. 03. 2017.
 */

public class ActivityAdd extends AppCompatActivity {
    ApplicationMy app;
    Spinner mySpin;
    Button pregled;
    TextView upor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (ApplicationMy) getApplication();
        setContentView(R.layout.activity_add);
        mySpin = (Spinner) findViewById(R.id.Spiner1);
        pregled=(Button) findViewById(R.id.Poglej);
        upor=(TextView) findViewById(R.id.Ime);
    }

    @Override
    protected void onResume() {
        super.onResume();
        upor.setText(app.getUporabnik().getIme()+" "+app.getUporabnik().getPriimek());
      // DodajPodatkeSpiner();
    }

    public void KlikPreglej(View view){
        Intent i = new Intent(getBaseContext(), ActivityLocationFire.class);
        i.putExtra(DataAll.LOKACIJA_ID,  mySpin.getSelectedItem().toString());
        startActivity(i);

    }

   /* public void DodajPodatkeSpiner() {

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, app.getListLocationID());
        mySpin.setAdapter(dataAdapter);

        mySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }*/


}
