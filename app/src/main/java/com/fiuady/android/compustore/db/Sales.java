package com.fiuady.android.compustore.db;

/**
 * Created by owner on 4/20/2017.
 */

public class Sales {

    private String date;
    private int Total_cost;

    public Sales(String date, int Total_cost) {
        this.date = date;
        this.Total_cost = Total_cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotal_cost() {
        return Total_cost;
    }

    public void setTotal_cost(int total_cost) {
        Total_cost = total_cost;
    }
}
