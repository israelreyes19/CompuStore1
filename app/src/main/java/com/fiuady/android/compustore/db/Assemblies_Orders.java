package com.fiuady.android.compustore.db;

/**
 * Created by owner on 4/28/2017.
 */

public class Assemblies_Orders {

    private String nombre;
    private  int qty;
    private int costo;

    public Assemblies_Orders (String nombre, int qty, int costo)
    {
        this.nombre = nombre;
        this.qty = qty;
        this.costo = costo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }
}
