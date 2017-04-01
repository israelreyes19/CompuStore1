package com.fiuady.android.compustore.db;




public final class Assembly_products {
    private int id;
    private int product_id;
    private int qty;

    public Assembly_products(int id, int product_id, int qty) {
        this.id = id;
        this.product_id = product_id;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
