package com.example;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Simek on 27. 02. 2017.
 */

public class LokacijaOgnja {
    String idOgenj;
    double x, y;
    String idUpor;
    boolean statusOgnja;
    private ListaTag tagi;
    private ArrayList<LokacijaGD> najblizjaGD;



    public LokacijaOgnja(double x, double y, String idUpor, boolean statusOgnja,ListaTag tag,LokacijaGD lg1,LokacijaGD lg2,LokacijaGD lg3) {
        najblizjaGD=new ArrayList<LokacijaGD>();
        this.idOgenj = UUID.randomUUID().toString().replaceAll("-", "");
        this.x = x;
        this.y = y;
        this.idUpor = idUpor;
        tagi=tag;
        this.statusOgnja=statusOgnja;

        najblizjaGD.add(lg1);

        najblizjaGD.add(lg2);

        najblizjaGD.add(lg3);

    }

    public ListaTag getTagi() {
        return tagi;
    }

    public void setTagi(ListaTag tagi) {
        this.tagi = tagi;
    }

    public LokacijaGD getLokacijaGD(int i)
    {
        return najblizjaGD.get(i);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getIdUpor() {
        return idUpor;
    }

    public void setIdUpor(String idUpor) {
        this.idUpor = idUpor;
    }

    public String getIdOgenj() {
        return idOgenj;
    }

    public void setIdOgenj(String idOgenj) {
        this.idOgenj = idOgenj;
    }

    public boolean getStatusOgnja() {
        return statusOgnja;
    }

    public void setStatusOgnja(boolean status) {
        this.statusOgnja = status;
    }


    @Override
    public String toString() {
        return "LokacijaOgnja{" +
                "idOgenj='" + idOgenj + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", idUpor='" + idUpor + '\'' +

                '}';
    }
}
