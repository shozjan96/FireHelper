package com.example.simek.firehelper;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.webkit.PermissionRequest;
import android.widget.Button;

import com.example.DataAll;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

/**
 * Created by Simek on 9. 04. 2017.
 */

public class ActivityMenu extends AppCompatActivity {
    ApplicationMy app;
    DataAll da;
    Button novVnos;
    Button seznam;
    Button odjava;


    public LocationUpdateReceiver dataUpdateReceiver;
    Location mLocation;

    private class LocationUpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(GpsMy.GPSTrackerEvent)) {
                mLocation = intent.getParcelableExtra(GpsMy.GPSTrackerKeyLocation);
                app.setLastLocation(mLocation);
                System.out.println("Lokacija: "+ System.currentTimeMillis()+" "+mLocation.toString());
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);



        novVnos=(Button) findViewById(R.id.NovPozar);
        seznam=(Button) findViewById(R.id.SeznamPozar);
        odjava=(Button) findViewById(R.id.Odjava);
        app=(ApplicationMy) getApplication();

        app.dodajUporabnike();
        app.dodajDrustva();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (this.dataUpdateReceiver == null) this.dataUpdateReceiver = new LocationUpdateReceiver();
        IntentFilter intentFilter = new IntentFilter(GpsMy.GPSTrackerEvent);
        registerReceiver(this.dataUpdateReceiver, intentFilter);
        startService(new Intent(app, GpsMy.class));//start service

    }

   private void getPermissions() {
        MultiplePermissionsListener my  = new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (!report.areAllPermissionsGranted()) {
                    new android.app.AlertDialog.Builder(ActivityMenu.this)
                            .setTitle("Pravice")
                            .setMessage("Pravice morate nastaviti!")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    ActivityMenu.this.finish(); //end app!
                                }
                            })

                            .show();
                }}

            @Override public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        };


        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.CAMERA
                ).withListener(my).check();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getPermissions();

    }



    public void ClickSeznam(View view){
        Intent i = new Intent(getBaseContext(), ActivityMain.class);
        startActivity(i);
    }

    public void ClickNovVnos(View view){
        Intent i = new Intent(getBaseContext(), ActivityAddFire.class);
        startActivity(i);
    }

    public void ClickLogin(View view){
        Intent i = new Intent(getBaseContext(), ActivityLogin.class);
        startActivity(i);
    }
}
