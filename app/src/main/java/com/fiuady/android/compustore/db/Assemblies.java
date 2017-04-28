package com.fiuady.android.compustore.db;


import android.os.Parcel;
import android.os.Parcelable;

public final class Assemblies implements Parcelable{
    private int id;
    private String description;

    public Assemblies(int id, String description) {
        this.id = id;
        this.description = description;
    }
    private Assemblies(Parcel in) {
        id = in.readInt();
        description = in.readString();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(description);
    }

    public static final Parcelable.Creator<Assemblies> CREATOR = new Parcelable.Creator<Assemblies>() {
        public Assemblies createFromParcel(Parcel in) {
            return new Assemblies(in);
        }

        public Assemblies[] newArray(int size) {
            return new Assemblies[size];
        }
    };
}
