package com.fiuady.android.compustore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.fiuady.android.compustore.db.Customers;
import com.fiuady.android.compustore.db.Inventory;
import com.fiuady.android.compustore.db.Order;
import com.fiuady.android.compustore.db.Order_Sales;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ConfirmOrderActivity extends AppCompatActivity {


    private Inventory inventory;
    private Spinner spinner_simulator;
    private RecyclerView RV_Orders_sale;
    private List<Order> OrdersAux = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        //declarar los componentes
        spinner_simulator = (Spinner) findViewById(R.id.Spinner_Simulator);
        RV_Orders_sale = (RecyclerView) findViewById(R.id.RecyclerView_Simulator);
        List<String> spinnerArray = new ArrayList<String>();
        inventory = new Inventory(getApplicationContext());
        //Llenar los componentes
        spinnerArray.add("Clientes");
        spinnerArray.add("Fecha");
        spinnerArray.add("Monto");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_simulator.setAdapter(adapter);



        spinner_simulator.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        Object item = parent.getItemAtPosition(pos);
                        if (spinner_simulator.getSelectedItem().toString().equals("Clientes")) {


                        } else if (spinner_simulator.getSelectedItem().toString().equals("Fecha")) {
                            OrdersAux = inventory.GetAllPendingOrders();
                            for (Order o : OrdersAux) {
                                //aqui obtendras todos los order_sales
                                Toast.makeText(getApplicationContext(), String.valueOf(o.getId()), Toast.LENGTH_LONG).show();
                                //OrderSalesByDate( Lista de Orders_sales obtenida) // este metodo ordena order sales by date
                            }

                        } else {
                            //aqui obtendras todos los order_sales
                            //OrderSalesByAmount (Lista de Order_Sales obtenida) // este metodo ordena order sales by amount
                        }
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

    }

    public List<Order_Sales> OrderSalesByDate(List<Order_Sales> OS) {
        Order_Sales aux;
        for (int i = 0; i < OS.size(); i++) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date Date_1 = new Date();
            try {
                Date_1 = sdf.parse(String.valueOf(OS.get(i).getFecha()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            for (int j = i + 1; j < OS.size(); j++) {
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
                Date Date_2 = new Date();
                try {
                    Date_2 = sdf1.parse(String.valueOf(OS.get(j).getFecha()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (Date_1.compareTo(Date_2) <= 0) {
                    aux = OS.get(i);
                    OS.set(i, OS.get(j));
                    OS.set(j, aux);
                }
            }
        }
        return OS;
    }

    public List<Order_Sales> OrderSalesByAmount(List<Order_Sales> OS) {
        Order_Sales aux;
        for (int i = 0; i < OS.size(); i++) {

            for (int j = i + 1; j < OS.size(); j++) {
                if (OS.get(i).getCosto()<OS.get(j).getCosto()){
                    aux = OS.get(i);
                    OS.set(i, OS.get(j));
                    OS.set(j, aux);
                }
            }
        }
        return OS;
    }
}
