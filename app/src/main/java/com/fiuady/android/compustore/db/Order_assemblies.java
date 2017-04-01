package com.fiuady.android.compustore.db;


public final class Order_assemblies {
    private int id;
    private int assembly_id;
    private int qty;

    public Order_assemblies(int id, int assembly_id, int qty) {
        this.id = id;
        this.assembly_id = assembly_id;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAssembly_id() {
        return assembly_id;
    }

    public void setAssembly_id(int assembly_id) {
        this.assembly_id = assembly_id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
