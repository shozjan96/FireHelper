package com.example;

/**
 * Created by Simek on 27. 02. 2017.
 */

public class Tag {
    private String ime;

    public Tag(String ime) {
        this.ime = ime;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    @Override
    public String toString() {
        return ""+ime;
    }
}
