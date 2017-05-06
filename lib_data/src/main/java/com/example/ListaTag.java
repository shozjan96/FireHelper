package com.example;

/**
 * Created by Simek on 27. 02. 2017.
 */
import java.util.ArrayList;
public class ListaTag {
    private ArrayList<Tag> lista;

    public ListaTag() {
        lista = new ArrayList<>();
    }

    public void dodajTag(String tag)
    {
        lista.add(new Tag(tag));
    }

    public ArrayList<Tag> getList() {
        return lista;
    }

    public void setList(ArrayList<Tag> list) {
        this.lista = list;
    }

    @Override
    public String toString() {
        return "ListaTag{" +
                "list=" + lista +
                '}';
    }

    public Tag getPrvi(int i)
    {
        return lista.get(i);

    }
}
