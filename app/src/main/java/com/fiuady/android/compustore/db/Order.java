package com.fiuady.android.compustore.db;


public final class Order {
    private int id;
    private int status_id;
    private int customer_id;
    private String date;
    private String change_log;

    public Order(int id, int status_id, int customer_id, String date, String change_log) {
        this.id = id;
        this.status_id = status_id;
        this.customer_id = customer_id;
        this.date = date;
        this.change_log = change_log;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getChange_log() {
        return change_log;
    }

    public void setChange_log(String change_log) {
        this.change_log = change_log;
    }
}
