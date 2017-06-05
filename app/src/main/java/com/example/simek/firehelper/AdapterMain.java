package com.example.simek.firehelper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.DataAll;
import com.example.LokacijaOgnja;
import com.example.Uporabnik;

/**
 * Created by Simek on 18. 03. 2017.
 */

public class AdapterMain extends RecyclerView.Adapter<AdapterMain.ViewHolder> {
    DataAll all;

    ActivityMain ac;
    ApplicationMy app;


    Location last;
    public static int UPDATE_DISTANCE_IF_DIFF_IN_M=10;

    public void setLastLocation(Location l) {
        if (last==null) {
            last = l;
            notifyDataSetChanged();
        }
        else {
            if (Util.distance(last.getLatitude(),last.getLongitude(),l.getLatitude(),l.getLongitude())>UPDATE_DISTANCE_IF_DIFF_IN_M){
                last = l;
                notifyDataSetChanged();
            }
        }
    }

    public AdapterMain(DataAll all, ActivityMain ac,ApplicationMy app) {
        this.all = all;
        this.ac = ac;
        this.app=app;

    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;
        public ImageView slika;
        public View row;
        public TextView distance;


        public ViewHolder(View v) {
            super(v);

            txtHeader = (TextView) v.findViewById(R.id.locationGD);
            txtFooter = (TextView) v.findViewById(R.id.uporabnik);
            row = v.findViewById(R.id.locationRow);
            slika=(ImageView) v.findViewById(R.id.imageView2);
            distance=(TextView)v.findViewById(R.id.distance);

        }
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lokacija, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    private static void startDView(String lokacijaID, Activity ac) {
        //  System.out.println(name+":"+position);
        Intent i = new Intent(ac.getBaseContext(), ActivityLocationFire.class);
        i.putExtra(DataAll.LOKACIJA_ID,  lokacijaID);
        ac.startActivity(i);

    }

    int i=0;
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final LokacijaOgnja aktiven=all.getListaOgnja().get(position);
        final String IDUp=aktiven.getIdUpor();


        for (Uporabnik l: app.getListUporabnik())
        {

            if(l.getIdUpor().equals(IDUp))
            {
                final String ime=l.getIme();
                final String priimek=l.getPriimek();
                holder.txtFooter.setText("Požar je vnesel: "+ime+" "+priimek);
                break;
            }
            else
            {

            }
        }


        if(aktiven.getStatusOgnja()==false)
        holder.slika.setImageResource(R.drawable.gori);
        else
        holder.slika.setImageResource(R.drawable.pogasen);

        holder.txtHeader.setText(aktiven.getTagi().getPrvi(0)+","+aktiven.getTagi().getPrvi(1)+","+aktiven.getTagi().getPrvi(2));

holder.row.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        AdapterMain.startDView(aktiven.getIdOgenj(),ac);
    }
});

        if (last==null) holder.distance.setText("N/A");
        else  holder.distance.setText(Util.getDistanceInString(ac.getLocation().getLatitude(), ac.getLocation().getLongitude(),aktiven.getX(),aktiven.getY()));
    }


    @Override
    public int getItemCount() {
        return all.getLocationSize();
    }
}


