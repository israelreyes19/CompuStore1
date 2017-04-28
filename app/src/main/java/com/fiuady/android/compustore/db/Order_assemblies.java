package com.fiuady.android.compustore.db;


import android.os.Parcel;
import android.os.Parcelable;

public final class Order_assemblies  implements Parcelable {
    private int id; // for order Id
    private int assembly_id; // for assembly id
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
    private Order_assemblies(Parcel in) {
        id = in.readInt();
        assembly_id = in.readInt();
        qty = in.readInt();

    }
    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeInt(assembly_id);
        out.writeInt(qty);
    }

    public static final Parcelable.Creator<Order_assemblies> CREATOR = new Parcelable.Creator<Order_assemblies>() {
        public Order_assemblies createFromParcel(Parcel in) {
            return new Order_assemblies(in);
        }

        public Order_assemblies[] newArray(int size) {
            return new Order_assemblies[size];
        }
    };
}
