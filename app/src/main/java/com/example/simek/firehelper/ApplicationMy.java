package com.example.simek.firehelper;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.example.DataAll;
import com.example.LokacijaGD;
import com.example.Tag;
import com.example.Uporabnik;
import com.example.LokacijaOgnja;
import com.example.ListaTag;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



/**
 * Created by Simek on 5. 03. 2017.
 */

public class ApplicationMy extends Application{
    int x;
    DataAll a;
    private Location mLastLocation;

    private static final String DATA_MAP = "firedatamap";
    private static final String FILE_NAME = "fire.json";



    @Override
    public void onCreate() {
        super.onCreate();
        x= 5;
        mLastLocation=null;
        if (!load())
        {
            a = DataAll.scenarijA();
            a.dodajUporabnike();
            a.dodajDrustva();
        }

    }

    public Location getLastLocation() {
        return mLastLocation;
    }

    public void setLastLocation(Location mLastLocation) {
        this.mLastLocation = mLastLocation;
    }
    public boolean hasLocation() {
        if (mLastLocation==null) return false;
        return true;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Uporabnik getUporabnik() {
        return a.getUpor();
    }

    public ArrayList<Uporabnik>getListUporabnik()
    {
        return a.getUporabniki();
    }

    public void dodajOgenj(LokacijaOgnja lo)
    {
        a.dodajLokacijo(lo);
    }


    public Uporabnik PreveriUporabnika(String UpIme,String passwd)
    {
        Uporabnik up=a.getUporabnikByUpIme(UpIme,passwd);
        return up;
    }

    public void nastaviUporabnika(Uporabnik up)
    {
        a.setUpor(up);
    }

    /*public LokacijaOgnja GetLokacijabyID(String i){
        return a.getListaOgnja(i);
    }*/
    public DataAll getAll() {
        return  a;
    }
    public ListaTag getlistaTag(String id)
    {
        return a.getLocationByID(id).getTagi();
    }
    public LokacijaOgnja getLocationByID(String id) {
        return a.getLocationByID(id);
    }

    public boolean save() {
        File file = new File(this.getExternalFilesDir(DATA_MAP), ""
                + FILE_NAME);
        //a.getLocationByID().setX(x);
        return AplicationJson.save(a,file);
    }
    public boolean load(){
        File file = new File(this.getExternalFilesDir(DATA_MAP), ""
                + FILE_NAME);
        DataAll tmp = AplicationJson.load(file);
        if (tmp!=null) a = tmp;
        else return false;
        return true;
    }

    public void removeLocationByPosition(int adapterPosition) {
        a.getListaOgnja().remove(adapterPosition);
    }

    public ArrayList<LokacijaGD> LokacijaDrustev()
    {
        return a.getLokacijeDrustev();
    }

    public void sortUpdate() {
        //sortType= (sortType+1) / 2;


                if (mLastLocation==null) return;
                Collections.sort(a.getListaOgnja(), new Comparator<LokacijaOgnja>() {
                    @Override
                    public int compare(LokacijaOgnja l1, LokacijaOgnja l2) {
                        int d1 = Util.distance(mLastLocation.getLatitude(),mLastLocation.getLongitude(),l1.getX(),l1.getY());
                        int d2 = Util.distance(mLastLocation.getLatitude(),mLastLocation.getLongitude(),l2.getX(),l2.getY());
                        if (d1==d2) return 0;
                        if (d1>d2) return 1;
                        return -1;
                    }
                });



        }





}
