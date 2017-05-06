package com.example;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
/**
 * Created by Simek on 27. 02. 2017.
 */
import java.util.ArrayList;

import sun.util.resources.cldr.fa.LocaleNames_fa;

public class DataAll {
    public static final String LOKACIJA_ID = "lokacija_idXX";

    private Uporabnik upor;
    private  ArrayList<Uporabnik>uporabniki;
    private ArrayList<LokacijaGD>lokacijeDrustev;
   private ArrayList<LokacijaOgnja> ListaOgnja;

    //Novo
    public DataAll() {

        this.upor = new Uporabnik("Simon", "Hozjan","shozjan","test");
        ListaOgnja = new ArrayList<>();

    }

    public DataAll(Uporabnik upor, ArrayList listaOgnja, Hashtable lokacijaGD) {

        this.upor = upor;
        ListaOgnja = listaOgnja;

    }

    public ArrayList<Uporabnik> getUporabniki() {
        return uporabniki;
    }

    public void setUporabniki(ArrayList<Uporabnik> uporabniki) {
        this.uporabniki = uporabniki;
    }

    public void dodajUporabnike()
    {
        uporabniki=new ArrayList<>();
        Uporabnik up=new Uporabnik("Simon", "Hozjan","shozjan","test");
        uporabniki.add(up);
        Uporabnik up1=new Uporabnik("Mirko", "Hozjan","mirko","test");
        uporabniki.add(up1);
    }

    public void dodajDrustva()
    {
        lokacijeDrustev=new ArrayList<>();
        LokacijaGD nov=new LokacijaGD(46.3348,16.2760,"Lendava");
        LokacijaGD nov1=new LokacijaGD(46.3418,16.2049,"Velika Polana");
        LokacijaGD nov2=new LokacijaGD(46.3344,15.3838,"Maribor");
        LokacijaGD nov3=new LokacijaGD(46.3936,16.9047,"Murska Sobota");
        lokacijeDrustev.add(nov);
        lokacijeDrustev.add(nov1);
        lokacijeDrustev.add(nov2);
        lokacijeDrustev.add(nov3);

    }

    @Override
    public String toString() {
        return "DataAll{" +

                ", upor=" + upor +
                ", ListaOgnja=" + ListaOgnja +

                '}' ;
    }



    public LokacijaOgnja dodajLO(double x, double y)
    {
        ListaTag lista=new ListaTag();
        lista.dodajTag("Tag1");
        lista.dodajTag("Tag2");
        lista.dodajTag("Tag3");
        LokacijaOgnja lo= new LokacijaOgnja(x,y,upor.idUpor,false,lista,new LokacijaGD(1,1,"s"),new LokacijaGD(2,2,"22"),new LokacijaGD(3,3,"sss"));
        ListaOgnja.add(lo);
        return  lo;
    }

    public ArrayList<LokacijaGD> getLokacijeDrustev() {
        return lokacijeDrustev;
    }

    public void setLokacijeDrustev(ArrayList<LokacijaGD> lokacijeDrustev) {
        this.lokacijeDrustev = lokacijeDrustev;
    }

    public void dodajLokacijo(LokacijaOgnja lo)
    {
        ListaOgnja.add(lo);
    }


    public static DataAll scenarijA() {
        DataAll da=new DataAll();
        da.upor = new Uporabnik("Simon", "Hozjan","shozjan","test");
        LokacijaOgnja tmp;
        tmp = da.dodajLO(46.30,15.30);
        tmp = da.dodajLO(46.55,14.16);
        tmp = da.dodajLO(45.22,13.12);
        for(int j=0;j<50;j++)
        {
            tmp = da.dodajLO(46.30+j,15.30+j);
        }
        return da;
    }

    public Uporabnik getUpor() {
        return upor;
    }

    public void setUpor(Uporabnik upor) {
        this.upor = upor;
    }


    public LokacijaOgnja getListaOgnja(int i) {
        return ListaOgnja.get(i);
    }

    public LokacijaOgnja getLocationByID(String ID) {
        for (LokacijaOgnja l: ListaOgnja) { //TODO this solution is relatively slow! If possible don't use it!
            // if (l.getId() == ID) return l; //NAPAKA primerja reference
            if (l.getIdOgenj().equals(ID)) return l;
        }
        return null;
    }

    public Uporabnik getUporabnikByUpIme(String UpIme,String passwd) {
         for (Uporabnik l: uporabniki)
         {
            if(l.getUpIme().equals(UpIme) && l.getGeslo().equals(passwd))
            {
                return l;
            }
         }
        return null;
    }

    public ArrayList<LokacijaOgnja> getListaOgnja() {
        return ListaOgnja;
    }

    public int getLocationSize(){return ListaOgnja.size();}

    public void addLocation(LokacijaOgnja l) {ListaOgnja.add(l);}
}
