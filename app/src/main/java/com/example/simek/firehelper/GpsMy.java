package com.example.simek.firehelper;

/**
 * Created by Simek on 10. 04. 2017.
 */

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class GpsMy extends Service{
    public static final String GPSTrackerEvent="MyGPSTrackerEvent";
    public static final String GPSTrackerKeyLocation="MyGPSTrackerEventLOCATION";
    private static final String TAG = GpsMy.class.getSimpleName();
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 1000; //1000ms = 1s
    private static final float LOCATION_DISTANCE = 0.5f; //5m


    private class LocationListener implements android.location.LocationListener {
        Location mLastLocation;
        boolean isOK;
        public void sendBroadcastMessage(){
            Intent in = new Intent(GpsMy.GPSTrackerEvent);
            in.putExtra(GPSTrackerKeyLocation, mLastLocation);
            sendBroadcast(in);
        }
        public void setOK(boolean OK) {
            isOK = OK;
        }

        public LocationListener(String provider) {
            Log.i(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
            isOK = true;
//            sendBroadcastMessage();
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.i(TAG, "onLocationChanged: " + location);
            mLastLocation.set(location);
            sendBroadcastMessage();

        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.i(TAG, "onProviderDisabled: " + provider);
            isOK = false;
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.i(TAG, "onProviderEnabled: " + provider);
            isOK = true;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.i(TAG, "onStatusChanged: " + provider);
            if (status == LocationProvider.AVAILABLE) isOK = true;
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    public boolean isAvalable() {
        if (mLocationListeners==null) return false;
        if (mLocationListeners[0].isOK) return true;
        if (mLocationListeners[1].isOK) return true;
        return false;
    }
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        if (mLocationManager==null) {
            create();
        } else
        {
            if (!isAvalable()) {
                create();
            }
        }


        return START_STICKY;
    }

    public void create() {
        initializeLocationManager();
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
            mLocationListeners[1].setOK(true);
            mLocationListeners[1].sendBroadcastMessage();
        } catch (java.lang.SecurityException ex) {
            mLocationListeners[1].setOK(false);
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            mLocationListeners[1].setOK(false);
            Log.i(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
            mLocationListeners[0].setOK(true);
            mLocationListeners[0].sendBroadcastMessage();
        } catch (java.lang.SecurityException ex) {
            mLocationListeners[0].setOK(false);
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            mLocationListeners[0].setOK(false);
            Log.i(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }
    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
        create();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        Log.i(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }
}
