package com.example.simek.firehelper;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.Set;

import com.example.simek.firehelper.eventbus.MessageEventSettingsLocationUpdateInterval;

/**
 * Created by Simek on 23. 04. 2017.
 */

public class ActivityMySettings extends AppCompatActivity {
    private static final String TAG = ActivityMySettings.class.getSimpleName();
    public static final String LOCATION_INTERVAL_KEY = "lp_location_interval";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefsFragment()).commit();

    }


    public static class PrefsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
        private static final String TAG = PrefsFragment.class.getSimpleName();



        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            updateALLSummary(getPreferenceManager().getSharedPreferences());
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }
        private void updateALLSummary(SharedPreferences sharedPreferences) {
            Set<String> keys = sharedPreferences.getAll().keySet();
            Preference connectionPref;
            for (String key : keys) {
                connectionPref = findPreference(key);
                setSummary(sharedPreferences, connectionPref, key);
            }
        }


        private void setSummary(SharedPreferences sharedPreferences, Preference connectionPref, String key) {
            if (connectionPref == null) return;
            Log.i(TAG, "sharedPreferences key:" + " " + key);
            if (connectionPref instanceof EditTextPreference) {
                connectionPref.setSummary(sharedPreferences.getString(key, ""));
            } else {
                if (connectionPref instanceof CheckBoxPreference) {
                    if (sharedPreferences.getBoolean(key, true))
                        connectionPref.setSummary("True");
                    else
                        connectionPref.setSummary("False");
                }
            }
        }

        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Preference connectionPref = findPreference(key);
            if (connectionPref == null) {
                Log.e(TAG, "connectionPref is null");
                return;
            }
            setSummary(sharedPreferences, connectionPref, key);
            if (key.equals(LOCATION_INTERVAL_KEY)) {
                EventBus.getDefault().post(new MessageEventSettingsLocationUpdateInterval(Integer.parseInt(sharedPreferences.getString(key, "1001"))));


            }

        }
    }
}
