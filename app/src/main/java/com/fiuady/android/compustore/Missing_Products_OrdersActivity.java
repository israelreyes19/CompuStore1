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

import com.fiuady.android.compustore.db.Inventory;
import com.fiuady.android.compustore.db.Products;

import java.util.List;

public class Missing_Products_OrdersActivity extends AppCompatActivity {

    public static final String ID_Value = "com.fiuady.android.compustore.ID";
    private Inventory inventory;

    private class ProductsHolder extends RecyclerView.ViewHolder
    {


        private TextView id;
        private TextView category;
        private  TextView description;
        private TextView price;
        private TextView quantity;


        public ProductsHolder(View itemView) {
            super(itemView);
            category = (TextView) itemView.findViewById(R.id.txt_category);
            id = (TextView) itemView.findViewById(R.id.txt_id);
            description = (TextView) itemView.findViewById(R.id.txt_description);
            price = (TextView) itemView.findViewById(R.id.txt_price);
            quantity = (TextView) itemView.findViewById(R.id.txt_qty);
        }

        public void bindProducts(Products products)
        {

            double aux1 = products.getPrice();
            double division = (aux1)/100;
            category.setText(String.valueOf(products.getCategory_id()));
            description.setText(products.getDescription());
            price.setText(String.format("%.2f",division));
            // price.setText(String.valueOf(division));
            quantity.setText(String.valueOf(products.getQty()));
            id.setText(String.valueOf(products.getId()));
        }
    }
    private class ProductsAdapter extends RecyclerView.Adapter<ProductsHolder>
    {

        private List<Products> products;
        public ProductsAdapter(List<Products> products){this.products = products;}

        @Override
        public ProductsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.missing_stock_item, parent, false);
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
        setContentView(R.layout.activity_missing__products__orders);
        backbuttonp = (ImageButton) findViewById(R.id.BackActivyMissingProds);
        inventory = new Inventory(getApplicationContext());

        Intent intent = getIntent();
        String ID = intent.getStringExtra(ID_Value);

        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView_missing_products_orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter3 = new ProductsAdapter(inventory.getallMissingProductsbyOrder(ID));
        recyclerView.setAdapter(adapter3);

        backbuttonp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
