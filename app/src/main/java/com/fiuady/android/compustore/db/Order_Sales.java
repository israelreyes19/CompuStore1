package com.fiuady.android.compustore.db;

/**
 * Created by owner on 4/22/2017.
 */

public class Order_Sales {

        private int ID;
        private String Nombre;
        private String Apellido;
        private String Fecha;
        private String Status;
        private int Costo;

        public Order_Sales(int ID,String Nombre, String Apellido, String Fecha, String Status, int Costo) {
            this.ID = ID;
            this.Nombre = Nombre;
            this.Apellido = Apellido;
            this.Fecha = Fecha;
            this.Status = Status;
            this.Costo = Costo;
        }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
            return Nombre;
        }

        public void setNombre(String nombre) {
            Nombre = nombre;
        }

        public String getApellido() {
            return Apellido;
        }

        public void setApellido(String apellido) {
            Apellido = apellido;
        }

        public String getFecha() {
            return Fecha;
        }

        public void setFecha(String fecha) {
            Fecha = fecha;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public int getCosto() {
            return Costo;
        }

        public void setCosto(int costo) {
            Costo = costo;
        }

}
