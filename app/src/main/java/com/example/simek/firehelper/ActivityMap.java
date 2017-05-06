package com.example.simek.firehelper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;
import android.content.SharedPreferences;
import com.example.LokacijaOgnja;
import com.example.DataAll;
import com.example.Tag;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;



import java.util.ArrayList;

/**
 * Created by Simek on 16. 04. 2017.
 */

public class ActivityMap  extends AppCompatActivity{

    LokacijaOgnja l;
    double x;
    double y;
    ApplicationMy app;
    MapView mMapView;
    ScaleBarOverlay mScaleBarOverlay;
    DisplayMetrics dm;
    ArrayList<OverlayItem> items;
    private ItemizedOverlayWithFocus<OverlayItem> mMyLocationOverlay;
    String ID;
    IMapController mapController;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app=(ApplicationMy) getApplication();

        Context ctx = getApplicationContext();
        //important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_full_map);


        mMapView = (MapView) findViewById(R.id.mapviewfull);
        mMapView.setTileSource(TileSourceFactory.MAPNIK);
        mMapView.setBuiltInZoomControls(true);
        mMapView.setMultiTouchControls(true);

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
        app = (ApplicationMy) getApplication();

    }

    public void onResume(){
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use

        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        Bundle extras = getIntent().getExtras();
        if( (extras !=null))
        {
            ID = extras.getString(DataAll.LOKACIJA_ID);
            setLokacija(extras.getString(DataAll.LOKACIJA_ID));

        } else {
            System.out.println("Nič ni v extras!");
        }



        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Configuration.getInstance().save(this, prefs);
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
    }

    void setLokacija(String ID) {
        l = app.getLocationByID(ID);
        IMapController mapController = mMapView.getController();
        mapController.setZoom(18);
        GeoPoint startPoint = new GeoPoint(l.getX(), l.getY());

        mMyLocationOverlay.removeAllItems();
        mMyLocationOverlay.addItem(new OverlayItem(l.getTagi().getPrvi(0).toString()+";"+l.getTagi().getPrvi(1).toString()+";"+l.getTagi().getPrvi(2).toString(),l.getX()+";"+l.getY(),startPoint));
        mapController.setCenter(startPoint);

        //update(l);
    }
    }

