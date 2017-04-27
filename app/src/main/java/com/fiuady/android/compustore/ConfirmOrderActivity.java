package com.fiuady.android.compustore;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fiuady.android.compustore.db.Customers;
import com.fiuady.android.compustore.db.Inventory;
import com.fiuady.android.compustore.db.Order;
import com.fiuady.android.compustore.db.Order_Sales;
import com.fiuady.android.compustore.db.Products;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ConfirmOrderActivity extends AppCompatActivity {

    private int bandera = 0;

    private class ProductsHolder extends RecyclerView.ViewHolder
    {

        private  TextView id;
        private TextView nombre;
        private TextView apellido;
        private  TextView fecha;
        private TextView status;
        private TextView costo;


        public ProductsHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.order_sales_id);
            nombre = (TextView) itemView.findViewById(R.id.nombre_cliente);
            apellido = (TextView) itemView.findViewById(R.id.apellido_cliente);
            fecha = (TextView) itemView.findViewById(R.id.fecha);
            status = (TextView) itemView.findViewById(R.id.Estado);
            costo = (TextView) itemView.findViewById(R.id.precio_orden);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int pos = getAdapterPosition();
                    final Order_Sales order_sales = adapter3.order_sales.get(pos);
                    final int order_id = order_sales.getID();
                    final String aux2 = String.valueOf(order_sales.getID());

                    if(inventory.getallMissingProductsbyOrder(aux2).isEmpty())
                    {

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ConfirmOrderActivity.this);
                        builder1.setTitle("¿Desea confirmar esta orden?");
                        builder1.setPositiveButton("Sí", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {

                                Inventory inventory = new Inventory(ConfirmOrderActivity.this);
                               // List<Products> products3 = inventory.getallnotMissingProductsbyOrder(aux2);

                                for (Products p : inventory.getallnotMissingProductsbyOrder(aux2))
                                {
                                    String aux5;
                                    String aux6;
                                    aux5 =String.valueOf(p.getId());
                                    aux6 = String.valueOf(p.getQty());
                                    //adapter.add(c.getDescription());
                                    inventory.Update_productquantity(aux5,aux6);
                                   // Toast.makeText(ConfirmOrderActivity.this, String.valueOf(p.getId()), Toast.LENGTH_SHORT).show();
                                }

                                inventory.UpdateOrder_status(order_id,2,"Orden confirmada");
                               // adapter3 = new ProductsAdapter(inventory.GetAllPendingOrdersbyclient());
                                //RV_Orders_sale.setAdapter(adapter3);
                                UpdateList();
                                Toast.makeText(ConfirmOrderActivity.this, "Orden confirmada", Toast.LENGTH_SHORT).show();
                                //recyclerView.setAdapter(adapter);
                            }
                        });
                        builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                        builder1.show();
                    }
                    else
                    {
                        Intent intent = new Intent(ConfirmOrderActivity.this, Missing_Products_OrdersActivity.class);
                        intent.putExtra(Missing_Products_OrdersActivity.ID_Value,aux2);
                        startActivity(intent);
                    }

                }
            });



        }

        public void bindProducts(Order_Sales order_sales)
        {
            String aux = String.valueOf(order_sales.getID());
            id.setText(String.valueOf(order_sales.getID()));
            double aux1 = order_sales.getCosto();
            double division = (aux1)/100;
            costo.setText(String.format("%.2f",division));
            nombre.setText(order_sales.getNombre());
            apellido.setText(order_sales.getApellido());
            fecha.setText(order_sales.getFecha());
            status.setText(order_sales.getStatus());

            List<Products> products = inventory.getallMissingProductsbyOrder(aux);

            List<Products> products2 = inventory.getallnotMissingProductsbyOrder(aux);

            if(products.isEmpty())
            {
                bandera = 1;
            }
            else
            {
                if(products2.isEmpty())
                {
                   bandera = 3;
                }

                else
                {
                    bandera = 2;
                }


            }

        }
    }
    private class ProductsAdapter extends RecyclerView.Adapter<ProductsHolder>
    {

        private List<Order_Sales> order_sales;
        public ProductsAdapter(List<Order_Sales> order_sales){this.order_sales = order_sales;}

        @Override
        public ProductsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_sales_item, parent, false);
            return new ProductsHolder(v);

        }

        @Override
        public void onBindViewHolder(ProductsHolder holder, int position) {

            holder.bindProducts(order_sales.get(position));

            if(bandera==1)
            {
                holder.itemView.setBackgroundColor(Color.GREEN);
            }

            else if(bandera==2)
            {
                holder.itemView.setBackgroundColor(Color.YELLOW);
            }
            else if(bandera==3)
            {
                holder.itemView.setBackgroundColor(Color.RED);
            }

        }

        @Override
        public int getItemCount() {
            return order_sales.size();
        }
    }

    private Inventory inventory;
    private Spinner spinner_simulator;
    private RecyclerView RV_Orders_sale;
    private List<Order> OrdersAux = new ArrayList<>();
    private ProductsAdapter adapter3;
    private ImageButton backbuttonp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        backbuttonp = (ImageButton) findViewById(R.id.BackActivysim);
        //declarar los componentes
        spinner_simulator = (Spinner) findViewById(R.id.Spinner_Simulator);
        RV_Orders_sale = (RecyclerView) findViewById(R.id.RecyclerView_Simulator);
        RV_Orders_sale.setLayoutManager(new LinearLayoutManager(this));
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
                            adapter3 = new ProductsAdapter(inventory.GetAllPendingOrdersbyclient());
                            RV_Orders_sale.setAdapter(adapter3);

                        } else if (spinner_simulator.getSelectedItem().toString().equals("Fecha")) {
                          //  OrdersAux = inventory.GetAllPendingOrders();
                          //  for (Order o : OrdersAux) {
                                //aqui obtendras todos los order_sales
                               // Toast.makeText(getApplicationContext(), String.valueOf(o.getId()), Toast.LENGTH_LONG).show();
                                //OrderSalesByDate( Lista de Orders_sales obtenida) // este metodo ordena order sales by date
                            adapter3 = new ProductsAdapter(OrderSalesByDate(inventory.GetAllPendingOrdersbyclient()));
                            RV_Orders_sale.setAdapter(adapter3);
                         //   }

                        } else {
                            adapter3 = new ProductsAdapter(inventory.GetAllPendingOrdersbycost());
                            RV_Orders_sale.setAdapter(adapter3);

                            //aqui obtendras todos los order_sales
                            //OrderSalesByAmount (Lista de Order_Sales obtenida) // este metodo ordena order sales by amount
                        }
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        backbuttonp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                if (Date_1.compareTo(Date_2) >= 0) {
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

    public void UpdateList()
    {
        if (spinner_simulator.getSelectedItem().toString().equals("Clientes")) {
            adapter3 = new ProductsAdapter(inventory.GetAllPendingOrdersbyclient());
            RV_Orders_sale.setAdapter(adapter3);

        } else if (spinner_simulator.getSelectedItem().toString().equals("Fecha")) {
            //  OrdersAux = inventory.GetAllPendingOrders();
            //  for (Order o : OrdersAux) {
            //aqui obtendras todos los order_sales
            // Toast.makeText(getApplicationContext(), String.valueOf(o.getId()), Toast.LENGTH_LONG).show();
            //OrderSalesByDate( Lista de Orders_sales obtenida) // este metodo ordena order sales by date
            adapter3 = new ProductsAdapter(OrderSalesByDate(inventory.GetAllPendingOrdersbyclient()));
            RV_Orders_sale.setAdapter(adapter3);
            //   }

        } else {
            adapter3 = new ProductsAdapter(inventory.GetAllPendingOrdersbycost());
            RV_Orders_sale.setAdapter(adapter3);

            //aqui obtendras todos los order_sales
            //OrderSalesByAmount (Lista de Order_Sales obtenida) // este metodo ordena order sales by amount
        }
    }


}
