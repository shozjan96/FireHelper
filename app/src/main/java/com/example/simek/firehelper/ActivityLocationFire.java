package com.example.simek.firehelper;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.Button;

import com.example.DataAll;
import com.example.ListaTag;
import com.example.LokacijaOgnja;
import com.example.Tag;
import com.example.Uporabnik;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

public class ActivityLocationFire extends AppCompatActivity {
    ApplicationMy app;
    boolean stateNew;
    TextView StatusOgnja;
    TextView XKoordinata;
    TextView YKooradinata;
    ListView listaTagov;
    LokacijaOgnja l;
    CheckBox NajblizjeGD;
    CheckBox DrugoGD;
    CheckBox TretjeGD;
    Button pogasen;
    MapView mMapView;
    double x,y;
    IMapController mapController;
    private ItemizedOverlayWithFocus<OverlayItem> mMyLocationOverlay;
    ArrayList<OverlayItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stateNew = false;
        setContentView(R.layout.activity_location_fire);
        app = (ApplicationMy) getApplication();
        StatusOgnja =(TextView) findViewById(R.id.Status);
        YKooradinata=(TextView) findViewById(R.id.YCoord);
        XKoordinata=(TextView) findViewById(R.id.XCoord);
        listaTagov=(ListView) findViewById(R.id.Tags);
        NajblizjeGD =(CheckBox)findViewById(R.id.Najblizja);
        DrugoGD =(CheckBox)findViewById(R.id.Tretja);
        TretjeGD =(CheckBox)findViewById(R.id.Druga);
        pogasen=(Button)findViewById(R.id.button3);
        //update(app.getUporabnik(),app.LO());
        mMapView=(MapView)findViewById(R.id.mapview);

        mMapView.setTileSource(TileSourceFactory.MAPNIK);
        mMapView.setBuiltInZoomControls(false);
        mMapView.setMultiTouchControls(false);
        mapController = mMapView.getController();

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            l= app.getAll().getLocationByID(extras.getString(DataAll.LOKACIJA_ID));
            x=l.getX();
            y=l.getY();


        } else {
            System.out.println("Nič ni v extras!");
        }

        GeoPoint startPoint = new GeoPoint(x,y);
        mapController.setCenter(startPoint);

        mapController.setZoom(19);


        mMapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent i = new Intent(getBaseContext(), ActivityMap.class);
                i.putExtra(DataAll.LOKACIJA_ID, l.getIdOgenj());
                startActivity(i);
                return true;
            }
        });

        items = new ArrayList<OverlayItem>();
        mMyLocationOverlay = new ItemizedOverlayWithFocus<OverlayItem>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {

                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        IMapController mapController = mMapView.getController();
                        mapController.setCenter(item.getPoint());
                        mapController.zoomTo(mMapView.getMaxZoomLevel());
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        Intent i = new Intent(getBaseContext(), ActivityMap.class);
                        i.putExtra(DataAll.LOKACIJA_ID, l.getIdOgenj());
                        startActivity(i);
                        return false;
                    }
                }, this);
        mMyLocationOverlay.setFocusItemsOnTap(true);

        mMapView.getOverlays().add(mMyLocationOverlay);

    }




    void setLokacija(String ID) {
        l = app.getAll().getLocationByID(ID);
        update(app.getUporabnik(),l);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            setLokacija(extras.getString(DataAll.LOKACIJA_ID));
        } else {
            System.out.println("Nič ni v extras!");
        }

        // l = app.getTestLocation();
        // update(l);
    }

    public void update(Uporabnik up, LokacijaOgnja lo) {

        if(lo.getStatusOgnja()==true)
        {
            StatusOgnja.setText("Ogenj je pogašen!");
            StatusOgnja.setTextColor(Color.GREEN);
            pogasen.setBackgroundColor(Color.GREEN);
            //pogasen.setBackgroundColor();
        }

        else
        {
            StatusOgnja.setText("Ogenj ni pogašen!");
            StatusOgnja.setTextColor(Color.RED);
            pogasen.setBackgroundColor(Color.RED);
        }

        Bundle extras = getIntent().getExtras();
        YKooradinata.setText(String.valueOf(lo.getY()));
        XKoordinata.setText(String.valueOf(lo.getX()));
        NajblizjeGD.setChecked(true);
        NajblizjeGD.setText(lo.getLokacijaGD(0).getImeGD());

        DrugoGD.setText(lo.getLokacijaGD(1).getImeGD());
        TretjeGD.setText(lo.getLokacijaGD(2).getImeGD());
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listaTagov.setAdapter(arrayAdapter);
        arrayAdapter.add(getsTags(app.getlistaTag(extras.getString(DataAll.LOKACIJA_ID)),0).getIme());
        arrayAdapter.add(getsTags(app.getlistaTag(extras.getString(DataAll.LOKACIJA_ID)),1).getIme());
        arrayAdapter.add(getsTags(app.getlistaTag(extras.getString(DataAll.LOKACIJA_ID)),2).getIme());

    }

    public Tag getsTags(ListaTag lt,int i)
    {
        return lt.getPrvi(i);
    }

    public void onClickSaveMe(View v) {
        if (stateNew) app.getAll().addLocation(l);
       // String x=XKoordinata.toString();
        //double koordX=Double.parseDouble(x);

        app.save();
        //app.setX(2555);
        finish();
    }


    public void PogasenPozar(View v)
    {
        super.onResume();

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            l= app.getAll().getLocationByID(extras.getString(DataAll.LOKACIJA_ID));
            l.setStatusOgnja(true);

            if(l.getStatusOgnja()==true)
            {
                StatusOgnja.setText("Ogenj je pogašen!");
                StatusOgnja.setTextColor(Color.GREEN);
                pogasen.setBackgroundColor(Color.GREEN);

            }

            else
            {
                StatusOgnja.setText("Ogenj ni pogašen!");
                StatusOgnja.setTextColor(Color.RED);
                pogasen.setBackgroundColor(Color.RED);
            }

        } else {
            System.out.println("Nič ni v extras!");
        }
    }
}
