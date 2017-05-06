package com.example.simek.firehelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.drawable.GradientDrawable;
import android.location.Location;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.LokacijaGD;
import com.example.LokacijaOgnja;
import com.example.DataAll;
import com.example.ListaTag;
import android.content.IntentFilter;

import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Simek on 9. 04. 2017.
 */

public class ActivityAddFire extends AppCompatActivity {
    DataAll da;
    ApplicationMy app;
    TextView x;
    TextView y;
    ToggleButton VnetljivoJa;
    ToggleButton Vnetljivone;
    ToggleButton SirjenjePocasi;
    ToggleButton SirjenjeSrednje;
    ToggleButton SirjenjeHitro;
    ToggleButton VrstaGozd;
    ToggleButton VrstaTravnik;
    ToggleButton VrstaHisa;
    LokacijaOgnja l;
    String XKord="";
    String yKord="";

    public LocationUpdateReceiver dataUpdateReceiver;
    Location mLocation;

    private class LocationUpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(GpsMy.GPSTrackerEvent)) {
                mLocation = intent.getParcelableExtra(GpsMy.GPSTrackerKeyLocation);
                app.setLastLocation(mLocation);
                System.out.println("Lokacija:"+ System.currentTimeMillis()+" "+mLocation.toString());
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (ApplicationMy) getApplication();
        setContentView(R.layout.activity_addfire);

        if (this.dataUpdateReceiver == null) this.dataUpdateReceiver = new LocationUpdateReceiver();
        IntentFilter intentFilter = new IntentFilter(GpsMy.GPSTrackerEvent);
        registerReceiver(this.dataUpdateReceiver, intentFilter);
        startService(new Intent(app, GpsMy.class));//start service

        if (mLocation!=null)
        {
            XKord=Double.toString(mLocation.getLatitude());
            yKord=Double.toString(mLocation.getLongitude());
        }
        else
        {
            XKord=Double.toString(app.getLastLocation().getLatitude());
            yKord=Double.toString(app.getLastLocation().getLongitude());
        }




        x=(TextView) findViewById(R.id.Xkordinat);

        y=(TextView) findViewById(R.id.YKoordinat);
        x.setText(XKord);
        y.setText(yKord);
        VnetljivoJa=(ToggleButton) findViewById(R.id.VnetljiveJa);
        Vnetljivone=(ToggleButton) findViewById(R.id.VnetljiveNE);
        SirjenjePocasi=(ToggleButton) findViewById(R.id.SirjenjePocasi);
        SirjenjeSrednje=(ToggleButton) findViewById(R.id.SirjenjeSrednje);
        SirjenjeHitro=(ToggleButton) findViewById(R.id.SirjenjeHitro);
        VrstaGozd=(ToggleButton)findViewById(R.id.VrstaGozd);
        VrstaTravnik=(ToggleButton)findViewById(R.id.VrstaTravnik);
        VrstaHisa=(ToggleButton)findViewById(R.id.VrstaHisa);

        VrstaHisa.setBackgroundColor(Color.RED);
        VrstaTravnik.setBackgroundColor(Color.RED);
        VrstaGozd.setBackgroundColor(Color.RED);

        SirjenjePocasi.setBackgroundColor(Color.RED);
        SirjenjeSrednje.setBackgroundColor(Color.RED);
        SirjenjeHitro.setBackgroundColor(Color.RED);

        VnetljivoJa.setBackgroundColor(Color.RED);
        Vnetljivone.setBackgroundColor(Color.RED);

    }

    protected void onResume() {
        super.onResume();

        // l = app.getTestLocation();
        // update(l);
    }



    @Override
    protected void onPause() {
        super.onPause();
        if (dataUpdateReceiver != null) unregisterReceiver(dataUpdateReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(ActivityAddFire.this, GpsMy.class));
    }

    public void DodajOgenj(View view){
        ListaTag list= new ListaTag();
        if(VnetljivoJa.isChecked())
        {
            list.dodajTag("Vnetljive snovi");

        }
        else
        {
            list.dodajTag("Ni vnetljivih snovi");

        }

        if(SirjenjePocasi.isChecked())
        {
            list.dodajTag("Pocasno sirjenje");

        }
        else if(SirjenjeSrednje.isChecked())
        {
            list.dodajTag("Srednje hitro sirjenje");

        }
        else
        {
            list.dodajTag("Hitro sirjenje");

        }

        if(VrstaGozd.isChecked())
        {
            list.dodajTag("Gozdni pozar");

        }
        else if(VrstaHisa.isChecked())
        {
            list.dodajTag("Hisni pozar");

        }
        else
        {
            list.dodajTag("Travniski pozar");

        }

        final Location lokacija=app.getLastLocation();

        Collections.sort(app.LokacijaDrustev(), new Comparator<LokacijaGD>() {
            @Override
            public int compare(LokacijaGD l1, LokacijaGD l2) {
                int d1 = Util.distance(lokacija.getLatitude(),lokacija.getLongitude(),l1.getX(),l1.getY());
                int d2 = Util.distance(lokacija.getLatitude(),lokacija.getLongitude(),l2.getX(),l2.getY());
                if (d1==d2) return 0;
                if (d1>d2) return 1;
                return -1;
            }
        });



        System.out.println("ss"+app.LokacijaDrustev().get(0).getImeGD());
        System.out.println("ss"+app.LokacijaDrustev().get(1).getImeGD());
        System.out.println("ss"+app.LokacijaDrustev().get(2).getImeGD());



        if (mLocation!=null)
        {
            LokacijaOgnja novaLokacija=new LokacijaOgnja(mLocation.getLatitude(),mLocation.getLongitude(),app.getUporabnik().getIdUpor(),false,list,app.LokacijaDrustev().get(0),app.LokacijaDrustev().get(1),app.LokacijaDrustev().get(2));
            app.dodajOgenj(novaLokacija);
        }
        else
        {
            LokacijaOgnja novaLokacija=new LokacijaOgnja(app.getLastLocation().getLatitude(),app.getLastLocation().getLongitude(),app.getUporabnik().getIdUpor(),false,list,app.LokacijaDrustev().get(0),app.LokacijaDrustev().get(1),app.LokacijaDrustev().get(2));
            app.dodajOgenj(novaLokacija);
        }

        Intent i = new Intent(getBaseContext(), ActivityMenu.class);
        startActivity(i);

    }

    public void barvaj(View view)
    {
        if(VnetljivoJa.isChecked())
        {

            VnetljivoJa.setBackgroundColor(Color.GRAY);
        }
        else
            VnetljivoJa.setBackgroundColor(Color.RED);

        if(Vnetljivone.isChecked())
        {

            Vnetljivone.setBackgroundColor(Color.GRAY);
        }
        else
            Vnetljivone.setBackgroundColor(Color.RED);

        if(SirjenjePocasi.isChecked())
        {

            SirjenjePocasi.setBackgroundColor(Color.GRAY);
        }
        else
            SirjenjePocasi.setBackgroundColor(Color.RED);

        if(SirjenjeSrednje.isChecked())
        {

            SirjenjeSrednje.setBackgroundColor(Color.GRAY);
        }
        else
            SirjenjeSrednje.setBackgroundColor(Color.RED);

        if(SirjenjeHitro.isChecked())
        {

            SirjenjeHitro.setBackgroundColor(Color.GRAY);
        }
        else
            SirjenjeHitro.setBackgroundColor(Color.RED);

        if(VrstaGozd.isChecked())
        {

            VrstaGozd.setBackgroundColor(Color.GRAY);
        }
        else
            VrstaGozd.setBackgroundColor(Color.RED);

        if(VrstaHisa.isChecked())
        {

            VrstaHisa.setBackgroundColor(Color.GRAY);
        }
        else
            VrstaHisa.setBackgroundColor(Color.RED);


        if(VrstaTravnik.isChecked())
        {

            VrstaTravnik.setBackgroundColor(Color.GRAY);
        }
        else
            VrstaTravnik.setBackgroundColor(Color.RED);



    }
}
