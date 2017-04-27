package com.fiuady.android.compustore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fiuady.android.compustore.db.Inventory;
import com.fiuady.android.compustore.db.Order_Sales;

import java.util.List;

public class SalesOrdersActivity extends AppCompatActivity {

    public static final String Month_Value = "com.fiuady.android.compustore.Month";
    public static final String Year_Value = "com.fiuady.android.compustore.Year";
    private Inventory inventory;


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
            }

            public void bindProducts(Order_Sales order_sales)
            {
                id.setText(String.valueOf(order_sales.getID()));
                double aux1 = order_sales.getCosto();
                double division = (aux1)/100;
                costo.setText(String.format("%.2f",division));
                nombre.setText(order_sales.getNombre());
                apellido.setText(order_sales.getApellido());
                fecha.setText(order_sales.getFecha());
                status.setText(order_sales.getStatus());
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
            }

            @Override
            public int getItemCount() {
                return order_sales.size();
            }
        }


    private RecyclerView recyclerView;
    private ProductsAdapter adapter3;
    private ImageButton backbuttonp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_orders);

        Intent intent = getIntent();

        String Number_month = "";
        String Month = intent.getStringExtra(Month_Value);
        if(Month.equals("Enero"))
        {
            Number_month = "01";
        }
        else if (Month.equals("Febrero")) {
            Number_month = "02";
        }
        else if (Month.equals("Marzo")) {
            Number_month = "03";
        }
        else if (Month.equals("Abril")) {
            Number_month = "04";
        }
        else if (Month.equals("Mayo")) {
            Number_month = "05";
        }
        else if (Month.equals("Junio")) {
            Number_month = "06";
        }
        else if (Month.equals("Julio")) {
            Number_month = "07";
        }
        else if (Month.equals("Agosto")) {
            Number_month = "08";
        }
        else if (Month.equals("Septiembre")) {
            Number_month = "09";
        }
        else if (Month.equals("Octubre")) {
            Number_month = "10";
        }
        else if (Month.equals("Noviembre")) {
            Number_month = "11";
        }
        else if (Month.equals("Diciembre")) {
            Number_month = "12";
        }

        String Year = intent.getStringExtra(Year_Value);

        String Total_date = Number_month +"-"+Year;
        backbuttonp = (ImageButton) findViewById(R.id.imageButtonBackOrderSales);
        inventory = new Inventory(getApplicationContext());

        recyclerView = (RecyclerView) findViewById(R.id.order_sales_recyvlerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

       adapter3 = new ProductsAdapter(inventory.getOrderSales(Total_date));
        recyclerView.setAdapter(adapter3);

        backbuttonp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        //    Toast.makeText(SalesOrdersActivity.this, Total_date, Toast.LENGTH_SHORT).show();

    }
}
