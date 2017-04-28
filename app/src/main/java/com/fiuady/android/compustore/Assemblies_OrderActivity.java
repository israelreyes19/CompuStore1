package com.fiuady.android.compustore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fiuady.android.compustore.db.Assemblies_Orders;
import com.fiuady.android.compustore.db.Inventory;

import java.util.List;

public class Assemblies_OrderActivity extends AppCompatActivity {

    public static final String Order_ID = "com.fiuady.android.compustore.Order_ID";
    private Inventory inventory;
    private class ProductsHolder extends RecyclerView.ViewHolder
    {

        private  TextView description;
        private TextView quantity;
        private TextView price;


        public ProductsHolder(View itemView) {
            super(itemView);

            description = (TextView) itemView.findViewById(R.id.ensamble_nombre);
            price = (TextView) itemView.findViewById(R.id.costo_ensamble);
            quantity = (TextView) itemView.findViewById(R.id.cantidad_ensamble);
        }

        public void bindProducts(Assemblies_Orders assemblies_orders)
        {

            double aux1 = assemblies_orders.getCosto();
            double division = (aux1)/100;

            description.setText(assemblies_orders.getNombre());
            price.setText(String.format("%.2f",division));
            quantity.setText(String.valueOf(assemblies_orders.getQty()));

        }
    }
    private class ProductsAdapter extends RecyclerView.Adapter<ProductsHolder>
    {

        private List<Assemblies_Orders> products;
        public ProductsAdapter(List<Assemblies_Orders> products){this.products = products;}

        @Override
        public ProductsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.assemblies_orders_item, parent, false);
            return new ProductsHolder(v);

        }

        @Override
        public void onBindViewHolder(ProductsHolder holder, int position) {
            holder.bindProducts(products.get(position));
        }

        @Override
        public int getItemCount() {
            return products.size();
        }
    }

    private RecyclerView recyclerView;
    private ProductsAdapter adapter3;
    private ImageButton backbuttonp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assemblies__order);

        Intent intent = getIntent();
        String ID = intent.getStringExtra(Order_ID);

        inventory = new Inventory(getApplicationContext());
        backbuttonp = (ImageButton) findViewById(R.id.imageButtonBackAssembliesOrders);
        recyclerView = (RecyclerView) findViewById(R.id.Assemblies_orders_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter3 = new ProductsAdapter(inventory.GetAssembliesbyorder(ID));
        recyclerView.setAdapter(adapter3);

        backbuttonp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
