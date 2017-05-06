package com.example.simek.firehelper;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.content.IntentFilter;

/**
 * Created by Simek on 18. 03. 2017.
 */

public class ActivityMain extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private AdapterMain mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ApplicationMy app;
    private LocationUpdateReceiver dataUpdateReceiver;

    Location mLocation;

    private class LocationUpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(GpsMy.GPSTrackerEvent)) {
                mLocation = intent.getParcelableExtra(GpsMy.GPSTrackerKeyLocation);
                app.setLastLocation(mLocation);
                System.out.println("Trenutna lokacija: "+ System.currentTimeMillis()+" "+mLocation.toString());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.myrecycleview);
        mRecyclerView.setHasFixedSize(true);


        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        app = (ApplicationMy) getApplication();

        mAdapter = new AdapterMain(app.getAll(), this,app);
        mRecyclerView.setAdapter(mAdapter);
        setDeleteOnSwipe(mRecyclerView);
        mLocation=app.getLastLocation();
        if (this.dataUpdateReceiver == null) this.dataUpdateReceiver = new LocationUpdateReceiver();
        IntentFilter intentFilter = new IntentFilter(GpsMy.GPSTrackerEvent);
        registerReceiver(this.dataUpdateReceiver, intentFilter);
        startService(new Intent(app, GpsMy.class));

        mAdapter.setLastLocation(mLocation);
        app.sortUpdate();
        mAdapter.notifyDataSetChanged();


    }
    @Override
    protected void onResume() {
        super.onResume();

        /*if (this.dataUpdateReceiver == null) this.dataUpdateReceiver = new LocationUpdateReceiver();
        IntentFilter intentFilter = new IntentFilter(GpsMy.GPSTrackerEvent);
        registerReceiver(this.dataUpdateReceiver, intentFilter);
        startService(new Intent(app, GpsMy.class));*/
        app.sortUpdate();
        mAdapter.notifyDataSetChanged();
        mAdapter.setLastLocation(mLocation);
    }



    public void setDeleteOnSwipe(final RecyclerView mRecyclerView) {

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                app.removeLocationByPosition(viewHolder.getAdapterPosition());
                                app.save();
                                mRecyclerView.getAdapter().notifyDataSetChanged();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                mRecyclerView.getAdapter().notifyDataSetChanged();
                                break;
                        }
                        // mRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMain.this);
                builder.setTitle("Izbris ognja");
                builder.setMessage("Ali res želite izbrisati?").setPositiveButton("Da", dialogClickListener)
                        .setNegativeButton("Ne", dialogClickListener)
                ;
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        mRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                });

                builder.show();

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    public Location getLocation() {
        return mLocation;
    }




}
