package com.fiuady.android.compustore.db;


import android.os.Parcel;
import android.os.Parcelable;

public final class Customers implements Parcelable{
    private int id;
    private String first_name;
    private String last_name;
    private String address;
    private String Phone1;
    private String Phone2;
    private String Phone3;
    private String e_mail;

    public Customers(int id, String first_name, String last_name, String address, String phone1, String phone2, String phone3, String e_mail) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        Phone1 = phone1;
        Phone2 = phone2;
        Phone3 = phone3;
        this.e_mail = e_mail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone1() {
        return Phone1;
    }

    public void setPhone1(String phone1) {
        Phone1 = phone1;
    }

    public String getPhone2() {
        return Phone2;
    }

    public void setPhone2(String phone2) {
        Phone2 = phone2;
    }

    public String getPhone3() {
        return Phone3;
    }

    public void setPhone3(String phone3) {
        Phone3 = phone3;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }
    private Customers(Parcel in) {
        id = in.readInt();
        first_name=in.readString();
        last_name=in.readString();
        address=in.readString();
        Phone1=in.readString();
        Phone2=in.readString();
        Phone3=in.readString();
        e_mail=in.readString();

    }
    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(first_name);
        out.writeString(last_name);
        out.writeString(address);
        out.writeString(Phone1);
        out.writeString(Phone2);
        out.writeString(Phone3);
        out.writeString(e_mail);

    }

    public static final Parcelable.Creator<Customers> CREATOR = new Parcelable.Creator<Customers>() {
        public Customers createFromParcel(Parcel in) {
            return new Customers(in);
        }

        public Customers[] newArray(int size) {
            return new Customers[size];
        }
    };
}
