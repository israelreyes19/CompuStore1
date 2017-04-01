package com.fiuady.android.compustore.db;


public final class Order_status {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public int getEditable() {
        return editable;
    }

    public void setEditable(int editable) {
        this.editable = editable;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    private int description;
    private int editable;
    private String previous;
    private String next;

    public Order_status(int id, int description, int editable, String previous, String next) {
        this.id = id;
        this.description = description;
        this.editable = editable;
        this.previous = previous;
        this.next = next;
    }
}
