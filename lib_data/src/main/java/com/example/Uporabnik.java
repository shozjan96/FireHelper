package com.example;

/**
 * Created by Simek on 27. 02. 2017.
 */

import java.util.UUID;

public class Uporabnik {
    String idUpor;
    private String ime;
    private String priimek;
    private String UpIme;
    private String geslo;

    public Uporabnik(String ime, String priimek,String UpIme,String geslo) {
        this.idUpor= UUID.randomUUID().toString().replaceAll("-", "");;
        this.ime = ime;
        this.priimek = priimek;
        this.UpIme=UpIme;
        this.geslo=geslo;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public String getIdUpor() {
        return idUpor;
    }

    public void setIdUpor(String idUpor) {
        this.idUpor = idUpor;
    }

    public String getUpIme() {
        return UpIme;
    }

    public void setUpIme(String upIme) {
        UpIme = upIme;
    }

    public String getGeslo() {
        return geslo;
    }

    public void setGeslo(String geslo) {
        this.geslo = geslo;
    }

    @Override
    public String toString() {
        return "Uporabnik{" +
                "idUpor='" + idUpor + '\'' +
                ", ime='" + ime + '\'' +
                ", priimek='" + priimek + '\'' +
                '}';
    }
}
