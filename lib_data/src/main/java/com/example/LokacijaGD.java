package com.example;

import java.util.UUID;

/**
 * Created by Simek on 27. 02. 2017.
 */

public class LokacijaGD {
    String idGD;
    double x, y;
    String imeGD;
    boolean izbrano;

    public LokacijaGD(double x, double y, String imeGD) {
        this.idGD= UUID.randomUUID().toString().replaceAll("-", "");;
        this.x = x;
        this.y = y;
        this.imeGD = imeGD;
    }

    public boolean isIzbrano() {
        return izbrano;
    }

    public void setIzbrano(boolean izbrano) {
        this.izbrano = izbrano;
    }

    public double getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public String getImeGD() {
        return imeGD;
    }

    public String getIdGD() {
        return idGD;
    }

    public void setIdGD(String idGD) {
        this.idGD = idGD;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setImeGD(String imeGD) {
        this.imeGD = imeGD;
    }

    @Override
    public String toString() {
        return "LokacijaGD{" +
                "idGD='" + idGD + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", imeGD='" + imeGD + '\'' +
                '}';
    }
}
